package rv.aric.security.entity;

import jakarta.persistence.*;
import rv.aric.ipsum.utils.ByteUtil;
import rv.aric.security.entity.builder.TokenBuilder;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @SequenceGenerator(name = "token_id", allocationSize = 1)
    @GeneratedValue(generator = "token_id")
    private Integer id;

    private String token;

    private byte expired;

    private byte revoked;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "token_type_id")
    private TokenType tokenType;

    public static TokenBuilder builder() {
        return new TokenBuilder(new Token());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean isExpired() {
        return ByteUtil.byteToBoolean(expired);
    }

    public void setExpired(Boolean expired) {
        this.expired = ByteUtil.booleanToByte(expired);
    }

    public Boolean isRevoked() {
        return ByteUtil.byteToBoolean(revoked);
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = ByteUtil.booleanToByte(revoked);
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
