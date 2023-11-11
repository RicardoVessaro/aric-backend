package rv.aric.security.service.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import rv.aric.ipsum.rest.exception.IpsumException;
import rv.aric.security.entity.Member;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationValidatorTest {

    AuthenticationValidator authenticationValidator;

    @BeforeEach
    void setUp() {
        authenticationValidator = new AuthenticationValidator();
    }

    @Test
    @DisplayName("Name, Username, Email, Password fields are given.")
    void testValidateRequiredFields() {
        var member = Member.builder()
            .name("Test")
            .username("test")
            .email("test@email.t")
            .password("encoded")
            .build();

        authenticationValidator.validateRequiredFields(member);
    }

    @Test
    @DisplayName("Name, Username, Email, Password fields are required.")
    void testThrowExceptionValidateRequiredFields() {
        var member = Member.builder().build();

        IpsumException ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validateRequiredFields(member);
        });

        String expectedMessage = "The following fields are required:\n Name, Username, Email, Password";
        assertEquals(expectedMessage, ipsumException.getMessage());
    }

}