package one.terenin.repository;

import one.terenin.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository // rudiment
public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {
    Optional<TokenEntity> findTokenEntityByToken(String token);
}
