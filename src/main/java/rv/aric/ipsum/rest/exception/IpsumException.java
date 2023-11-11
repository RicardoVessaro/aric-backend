package rv.aric.ipsum.rest.exception;

import org.springframework.http.HttpStatus;

public class IpsumException extends RuntimeException {

    public IpsumException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
