package rv.aric.security.service.validator;

import org.springframework.stereotype.Component;
import rv.aric.ipsum.rest.exception.IpsumException;
import rv.aric.ipsum.service.validator.RequiredFieldsValidator;
import rv.aric.security.entity.Member;

import java.util.ArrayList;

@Component
public class AuthenticationValidator {

    public void validateRequiredFields(Member member) {
        new RequiredFieldsValidator()
            .verify(member.getName(), "Name")
            .verify(member.getUsername(), "Username")
            .verify(member.getEmail(), "Email")
            .verify(member.getPassword(), "Password")
        .validate();
    }

}
