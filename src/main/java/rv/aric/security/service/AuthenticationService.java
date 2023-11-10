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
import rv.aric.security.common.utils.AuthenticationHeaderUtil;
import rv.aric.security.entity.Member;
import rv.aric.security.entity.Token;
import rv.aric.security.enums.EnumTokenType;
import rv.aric.security.repository.MemberRepository;
import rv.aric.security.repository.TokenRepository;

@Service
public class AuthenticationService implements LogoutHandler {

    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private TokenRepository tokenRepository;

    public AuthenticationService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository
    ) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    public String register(MemberDTO memberDTO) {
        var member = Member.builder()
            .name(memberDTO.name())
            .username(memberDTO.username())
            .email(memberDTO.email())
            .password(passwordEncoder.encode(memberDTO.password()))
            .biography(memberDTO.biography())
            .build();

        var savedMember = memberRepository.save(member);

        var jwtToken = jwtService.generateToken(member);

        saveToken(savedMember, jwtToken);

        return jwtToken;
    }

    public String authenticate(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        var member = memberRepository.findByUsername(username)
            .orElseThrow();

        var jwtToken = jwtService.generateToken(member);

        revokeAllMemberTokens(member);

        saveToken(member, jwtToken);

        return jwtToken;
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
