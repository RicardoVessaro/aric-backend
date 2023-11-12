package rv.aric.security.enums;

import rv.aric.security.entity.TokenType;

public enum EnumTokenType {
    BEARER(new TokenType(1, "Bearer"));

    private final TokenType tokenType;

    EnumTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public TokenType get() {
        return tokenType;
    }
}
