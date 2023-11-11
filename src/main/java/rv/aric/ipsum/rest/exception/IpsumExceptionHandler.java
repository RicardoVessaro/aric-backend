package rv.aric.ipsum.rest.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class IpsumExceptionHandler {

    @Value("${ipsum.exception.stacktrace.show:false}")
    private Boolean showStacktrace;

    @ExceptionHandler
    public ResponseEntity<IpsumExceptionResponse> handleException(IpsumException exception) {

        String stackTrace = getStackTrace(exception);

        IpsumExceptionResponse error = new IpsumExceptionResponse(
            exception.getHttpStatus().value(),
            exception.getMessage(),
            stackTrace,
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, exception.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<IpsumExceptionResponse> handleException(IpsumNotFoundException exception) {

        String stackTrace = getStackTrace(exception);

        IpsumExceptionResponse error = new IpsumExceptionResponse(
            exception.getHttpStatus().value(),
            exception.getMessage(),
            stackTrace,
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, exception.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<IpsumExceptionResponse> handleException(Exception exception) {

        String stackTrace = getStackTrace(exception);

        IpsumExceptionResponse error = new IpsumExceptionResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.getMessage(),
            stackTrace,
            System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getStackTrace(Exception exception) {
        return Boolean.TRUE.equals(showStacktrace) ? Arrays.toString(exception.getStackTrace()) : "";
    }

}
