package rv.aric.ipsum.service.validator;

import rv.aric.ipsum.rest.exception.IpsumException;

import java.util.Collection;

public class RequiredFieldsValidator {

    String message = "";

    public RequiredFieldsValidator verify(Object value, String field) {
        if(value == null) {
            addEmptyField(field);
        }
        return this;
    }

    public RequiredFieldsValidator verify(Collection<?> value, String field) {
        if(value == null || value.isEmpty()) {
            addEmptyField(field);
        }
        return this;
    }

    public RequiredFieldsValidator verify(String value, String field) {
        if(value == null || value.isEmpty()) {
            addEmptyField(field);
        }
        return this;
    }

    private void addEmptyField(String field) {
        if(message.isEmpty()) {
            message +=  field;

        } else {
            message += ", " + field;
        }
    }

    public void validate() {
        if(!message.isEmpty()) {
            throw new IpsumException("The following fields are required:\n " + message);
        }
    }
}
