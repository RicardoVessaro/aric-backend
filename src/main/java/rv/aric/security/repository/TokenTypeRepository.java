package rv.aric.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rv.aric.security.entity.TokenType;

public interface TokenTypeRepository extends JpaRepository<TokenType, Integer> {
}
