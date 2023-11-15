package rv.aric.security.common.response;

public record AuthenticationResponse (String userId, String token, String username, Long expiresIn) {
}
