package rv.aric.security.service.validator;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import rv.aric.ipsum.rest.exception.IpsumException;
import rv.aric.security.entity.Member;
import rv.aric.security.repository.MemberRepository;
import rv.aric.security.repository.TokenTypeRepository;

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
    @DisplayName("Do not throw exceptions when using an different username")
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
}