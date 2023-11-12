package rv.aric.security.service.validator;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rv.aric.ipsum.rest.exception.IpsumException;
import rv.aric.security.entity.Member;
import rv.aric.security.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tester")
@SpringBootTest
@Transactional
class AuthenticationValidatorTest {

    @Autowired
    AuthenticationValidator authenticationValidator;

    @Autowired
    MemberRepository memberRepository;

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

    @Test
    @DisplayName("Throw exceptions when using an existent username")
    void testThrowExceptionValidateUniqueUsername() {
        var existentMember = Member.builder()
            .username("test")
            .email("test@email.t")
            .password("encoded")
            .build();

        memberRepository.save(existentMember);

        var member = Member.builder().username("test").build();

        IpsumException ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validateUniqueUsername(member.getUsername());
        });

        String expectedMessage = "The username 'test' is already in use.";
        assertEquals(expectedMessage, ipsumException.getMessage());

    }

    @Test
    @DisplayName("Do not throw exceptions when using an non-existent username")
    void testValidateUniqueUsername() {
        var existentMember = Member.builder()
            .name("Test")
            .username("other test")
            .email("test@email.t")
            .password("encoded")
            .build();

        memberRepository.save(existentMember);

        var member = Member.builder().username("test").build();
        authenticationValidator.validateUniqueUsername(member.getUsername());
    }

    @Test
    @DisplayName("Do not throw exceptions when using an non-existent email")
    void testValidateUniqueEmail() {
        var existentMember = Member.builder()
                .name("Test")
                .username("other test")
                .email("test@email.t")
                .password("encoded")
                .build();

        memberRepository.save(existentMember);

        var member = Member.builder().email("other_test@email.t").build();
        authenticationValidator.validateUniqueEmail(member.getEmail());
    }

    @Test
    @DisplayName("Throw exceptions when using an existent email")
    void testThrowExceptionValidateUniqueEmail() {
        var existentMember = Member.builder()
                .name("Test")
                .username("other test")
                .email("test@email.t")
                .password("encoded")
                .build();

        memberRepository.save(existentMember);

        var member = Member.builder().email("test@email.t").build();

        IpsumException ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validateUniqueEmail(member.getEmail());
        });

        String expectedMessage = "The email 'test@email.t' is already in use.";
        assertEquals(expectedMessage, ipsumException.getMessage());
    }

    @Test
    @DisplayName("Password must have at least 8 characters, one upper case, lower case, number, and special character")
    void testPasswordPattern() {
        authenticationValidator.validatePasswordPattern("Ab@01234");

        IpsumException ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validatePasswordPattern("Ab@0123");
        });
        String expectedMessage = "Password must have at least 8 characters.";
        assertEquals(expectedMessage, ipsumException.getMessage());


        ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validatePasswordPattern("A123456@");
        });
        expectedMessage = "Password must have at least 1 lowercase letter.";
        assertEquals(expectedMessage, ipsumException.getMessage());


        ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validatePasswordPattern("a123456@");
        });
        expectedMessage = "Password must have at least 1 uppercase letter.";
        assertEquals(expectedMessage, ipsumException.getMessage());


        ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validatePasswordPattern("abcdefG@");
        });
        expectedMessage = "Password must have at least 1 number.";
        assertEquals(expectedMessage, ipsumException.getMessage());


        ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validatePasswordPattern("abcdefG1");
        });
        expectedMessage = "Password must have at least 1 special character.";
        assertEquals(expectedMessage, ipsumException.getMessage());


        ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validatePasswordPattern("");
        });
        expectedMessage = "Password must have at least 8 characters, 1 lowercase letter, 1 uppercase letter, 1 number, 1 special character.";
        assertEquals(expectedMessage, ipsumException.getMessage());

        ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validatePasswordPattern(null);
        });
        expectedMessage = "Password must have at least 8 characters, 1 lowercase letter, 1 uppercase letter, 1 number, 1 special character.";
        assertEquals(expectedMessage, ipsumException.getMessage());
    }

    @Test
    @DisplayName("Email must have '@'")
    void testValidateEmailPattern() {
        authenticationValidator.validateEmailPatttern("test@email.t");

        IpsumException ipsumException = assertThrows(IpsumException.class, () -> {
            authenticationValidator.validateEmailPatttern("testemail.t");
        });
        String expectedMessage = "Email is not valid.";
        assertEquals(expectedMessage, ipsumException.getMessage());
    }
}