package rv.aric.security.service.validator;

import org.springframework.stereotype.Component;
import rv.aric.ipsum.rest.exception.IpsumException;
import rv.aric.ipsum.service.validator.RequiredFieldsValidator;
import rv.aric.security.common.AuthenticationMessage;
import rv.aric.security.entity.Member;
import rv.aric.security.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Optional;

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
}
