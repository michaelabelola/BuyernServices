package com.buyern.authentication.repositories;

import com.buyern.authentication.enums.TokenType;
import com.buyern.authentication.models.CustomToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomTokenRepository extends JpaRepository<CustomToken, Long> {
    Optional<CustomToken> findByUserIdAndTokenAndType(String userId, String token, TokenType type);

    Optional<CustomToken> findByUserIdAndType(String userId, TokenType type);

    long deleteByEmailAndType(String email, TokenType type);

    boolean existsByUserIdAndTokenAndType(String userId, String token, TokenType type);


}