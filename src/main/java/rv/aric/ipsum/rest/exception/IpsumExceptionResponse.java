package rv.aric.ipsum.rest.exception;

public record IpsumExceptionResponse(int status, String message, String stackTrace, long timeStamp) {
}
