package rv.aric.ipsum.service.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rv.aric.ipsum.rest.exception.IpsumException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequiredFieldsValidatorTest {

    @Test
    @DisplayName("Test RequiredFieldsValidator")
    void testRequiredFieldsValidator() {
        new RequiredFieldsValidator()
            .verify(1, "int")
            .verify("a", "string")
            .verify(List.of("a", "b"), "collection")
            .validate();
    }

    @Test
    @DisplayName("Test RequiredFieldsValidator throws exception")
    void testRequiredFieldsValidatorThrowsException() {

        IpsumException ipsumException = assertThrows(IpsumException.class, () -> {
            String empty = null;

            new RequiredFieldsValidator()
                    .verify(empty, "empty")
                    .validate();
        });

        String expectedMessage = "The following fields are required:\n empty";
        assertEquals(expectedMessage, ipsumException.getMessage());
    }

    @Test
    @DisplayName("Test RequiredFieldsValidator throws exception using collection")
    void testRequiredFieldsValidatorThrowsExceptionUsingCollection() {

        IpsumException ipsumException = assertThrows(IpsumException.class, () -> {
            new RequiredFieldsValidator()
                    .verify(new ArrayList<>(), "collection")
                    .validate();
        });

        String expectedMessage = "The following fields are required:\n collection";
        assertEquals(expectedMessage, ipsumException.getMessage());
    }

}