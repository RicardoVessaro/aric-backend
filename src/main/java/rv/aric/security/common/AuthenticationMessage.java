package rv.aric.security.common;

public enum AuthenticationMessage {
    MEMBER_DUPLICATED_USERNAME("The username ''{0}'' is already in use.")
    ;

    private String message;

    AuthenticationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
