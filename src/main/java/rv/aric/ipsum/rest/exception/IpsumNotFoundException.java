package rv.aric.ipsum.rest.exception;

import org.springframework.http.HttpStatus;

public class IpsumNotFoundException extends IpsumException {

    public IpsumNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
