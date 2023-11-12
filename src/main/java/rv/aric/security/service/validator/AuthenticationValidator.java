package rv.aric.security.service.validator;

import org.springframework.stereotype.Component;
import rv.aric.ipsum.rest.exception.IpsumException;
import rv.aric.ipsum.service.validator.RegexValidator;
import rv.aric.ipsum.service.validator.RequiredFieldsValidator;
import rv.aric.security.common.AuthenticationMessage;
import rv.aric.security.entity.Member;
import rv.aric.security.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class AuthenticationValidator {

    private MemberRepository memberRepository;

    public AuthenticationValidator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void validateRequiredFields(Member member) {
        new RequiredFieldsValidator()
            .verify(member.getName(), "Name")
            .verify(member.getUsername(), "Username")
            .verify(member.getEmail(), "Email")
            .verify(member.getPassword(), "Password")
        .validate();
    }

    public void validateUniqueUsername(String username) {
        Optional<Member> existentMemberOptional = memberRepository.findByUsername(username);

        if(existentMemberOptional.isEmpty()) {
            return;
        }

        Member existentMember = existentMemberOptional.get();
        throw new IpsumException(
                AuthenticationMessage.MEMBER_DUPLICATED_USERNAME.getMessage(), existentMember.getUsername());
    }

    public void validateUniqueEmail(String email) {
        Optional<Member> existentMemberOptional = memberRepository.findByEmail(email);

        if(existentMemberOptional.isEmpty()) {
            return;
        }

        Member existentMember = existentMemberOptional.get();
        throw new IpsumException(
                AuthenticationMessage.MEMBER_DUPLICATED_EMAIL.getMessage(), existentMember.getEmail()
        );
    }

    public void validatePasswordPattern(String password) {
        new RegexValidator()
            .verifyMinSize(8)
            .verify("([a-z])", "1 lowercase letter")
            .verify("([A-Z])", "1 uppercase letter")
            .verify("([\\d])", "1 number")
            .verify("([@$!%*?&])","1 special character")
            .validate(password, "Password");
        ;
    }

    public void validateEmailPatttern(String email) {
        new RegexValidator()
            .setMessage("{0} is not valid.")
            .verify("([@])", "email")
            .validate(email, "Email");
    }
}
