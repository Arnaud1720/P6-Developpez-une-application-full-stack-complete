package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.RefreshToken;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    void deleteAllByUser(User user);

}
