package rv.aric.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rv.aric.security.entity.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository <Token, Integer> {

    @Query("""
        SELECT t FROM Token t 
            INNER JOIN Member m ON m.id = t.member.id
        WHERE m.id = :memberId AND (t.expired = 0 OR t.revoked = 0)   
    """)
    List<Token> findAllValidTokensByMember(UUID memberId);

    Optional<Token> findByToken(String token);
}
