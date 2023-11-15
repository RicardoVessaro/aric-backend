package rv.aric.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import rv.aric.security.common.dto.MemberDTO;
import rv.aric.security.common.response.AuthenticationResponse;
import rv.aric.security.common.utils.AuthenticationHeaderUtil;
import rv.aric.security.entity.Member;
import rv.aric.security.entity.Token;
import rv.aric.security.enums.EnumTokenType;
import rv.aric.security.repository.MemberRepository;
import rv.aric.security.repository.TokenRepository;
import rv.aric.security.service.validator.AuthenticationValidator;

import java.util.Date;

@Service
public class AuthenticationService implements LogoutHandler {

    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private TokenRepository tokenRepository;

    private AuthenticationValidator authenticationValidator;

    public AuthenticationService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository, AuthenticationValidator authenticationValidator
    ) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.authenticationValidator = authenticationValidator;
    }

    public AuthenticationResponse register(MemberDTO memberDTO) {
        var member = Member.builder()
            .name(memberDTO.name())
            .username(memberDTO.username())
            .email(memberDTO.email())
            .password(encodePassword(memberDTO))
            .biography(memberDTO.biography())
            .build();

        authenticationValidator.validateRequiredFields(member);
        authenticationValidator.validateUniqueUsername(member.getUsername());
        authenticationValidator.validateUniqueEmail(member.getEmail());
        authenticationValidator.validateEmailPatttern(member.getEmail());
        authenticationValidator.validatePasswordPattern(memberDTO.password());

        var savedMember = memberRepository.save(member);

        var jwtToken = jwtService.generateToken(member);

        saveToken(savedMember, jwtToken);

        return getAuthenticationResponse(savedMember.getId().toString(), jwtToken);
    }

    private String encodePassword(MemberDTO memberDTO) {
        if(memberDTO.password().isEmpty()) {
            return null;
        }

        return passwordEncoder.encode(memberDTO.password());
    }

    public AuthenticationResponse authenticate(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        var member = memberRepository.findByUsername(username)
            .orElseThrow();

        var jwtToken = jwtService.generateToken(member);

        revokeAllMemberTokens(member);

        saveToken(member, jwtToken);

        return getAuthenticationResponse(member.getId().toString(), jwtToken);
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        AuthenticationHeaderUtil authHeaderUtil = new AuthenticationHeaderUtil(request);

        if(authHeaderUtil.isNull() || !authHeaderUtil.isBearer()) {
            return;
        }

        final String jwt = authHeaderUtil.getJwtToken();

        var storedToken = tokenRepository.findByToken(jwt)
            .orElse(null);

        if(storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
    }

    private AuthenticationResponse getAuthenticationResponse(String memberId, String jwtToken) {
        Date expirationDate = jwtService.extractExpiration(jwtToken);
        String username = jwtService.extractUsername(jwtToken);
        Long expiresIn = expirationDate.getTime() - new Date().getTime();

        return new AuthenticationResponse(memberId, jwtToken, username, expiresIn);
    }

    private void revokeAllMemberTokens(Member member) {
        var validMemberTokens = tokenRepository.findAllValidTokensByMember(member.getId());

        if(validMemberTokens.isEmpty()) {
            return;
        }

        validMemberTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });

        tokenRepository.saveAll(validMemberTokens);
    }

    private void saveToken(Member member, String jwtToken) {
        var token = Token.builder()
            .member(member)
            .token(jwtToken)
            .tokenType(EnumTokenType.BEARER.get())
            .revoked(false)
            .expired(false)
            .build();

        tokenRepository.save(token);
    }

}
