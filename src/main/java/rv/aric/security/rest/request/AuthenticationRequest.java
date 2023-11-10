package rv.aric.security.rest.request;

public record AuthenticationRequest(
    String username,
    String password
) {
}
