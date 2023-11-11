package rv.aric.ipsum.rest.exception;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class IpsumException extends RuntimeException {

    public IpsumException(String message) {
        super(message);
    }

    public IpsumException(String message, Object... messageParams) {
        super(MessageFormat.format(message, messageParams));
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
