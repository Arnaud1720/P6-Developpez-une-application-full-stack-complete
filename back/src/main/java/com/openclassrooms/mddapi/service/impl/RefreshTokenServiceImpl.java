package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.entity.RefreshToken;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.RefreshTokenRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.jwt.refreshExpirationMs}") private Long refreshExpirationMs;
    private final RefreshTokenRepository repo;
    private final UserRepository userRepo;

    public RefreshTokenServiceImpl(RefreshTokenRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Override
    public RefreshToken createRefreshToken(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
        rt.setToken(UUID.randomUUID().toString());
        return repo.save(rt);
    }

    @Override
    public void deleteByUser(User user) {
        repo.deleteAllByUser(user);

    }

    @Override
    public Optional<RefreshToken> verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            repo.delete(token);
            return Optional.empty();
        }
        return Optional.of(token);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repo.findByToken(token);

    }

}
