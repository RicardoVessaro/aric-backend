package rv.aric.ipsum.rest.exception;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class IpsumNotFoundException extends IpsumException {

    public IpsumNotFoundException(String message) {
        super(message);
    }

    public IpsumNotFoundException(String message, Object... messageParams) {
        super(MessageFormat.format(message, messageParams));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
