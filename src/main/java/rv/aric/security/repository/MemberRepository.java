package rv.aric.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rv.aric.security.entity.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository <Member, UUID> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);
}
