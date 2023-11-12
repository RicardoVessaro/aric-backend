package rv.aric.security.entity.builder;

import rv.aric.security.entity.Member;
import rv.aric.security.entity.Token;
import rv.aric.security.entity.TokenType;

public class TokenBuilder {

    private Token token;

    public TokenBuilder(Token token) {
        this.token = token;
    }

    public TokenBuilder id(Integer id) {
        this.token.setId(id);
        return this;
    }

    public TokenBuilder token(String token) {
        this.token.setToken(token);
        return this;
    }

    public TokenBuilder expired(Boolean expired) {
        this.token.setExpired(expired);
        return this;
    }

    public TokenBuilder revoked(Boolean revoked) {
        this.token.setRevoked(revoked);
        return this;
    }

    public TokenBuilder member(Member member) {
        this.token.setMember(member);
        return this;
    }

    public TokenBuilder tokenType(TokenType tokenType) {
        this.token.setTokenType(tokenType);
        return this;
    }

    public Token build() {
        return  this.token;
    }
}
