package rv.aric.security.entity.builder;

import rv.aric.security.entity.Member;

import java.util.UUID;

public class MemberBuilder {

    private final Member member;

    public MemberBuilder(Member member) {
        this.member = member;
    }
    public MemberBuilder id(UUID id) {
        this.member.setId(id);
        return this;
    }

    public MemberBuilder name(String name) {
        this.member.setName(name);
        return this;
    }

    public MemberBuilder username(String username) {
        this.member.setUsername(username);
        return this;
    }

    public MemberBuilder email(String email) {
        this.member.setEmail(email);
        return this;
    }
    public MemberBuilder password(String password) {
        this.member.setPassword(password);
        return this;
    }

    public MemberBuilder biography(String biography) {
        this.member.setBiography(biography);
        return this;
    }

    public Member build() {
        return this.member;
    }
}
