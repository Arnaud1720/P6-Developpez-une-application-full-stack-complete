package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.entity.RefreshToken;
import com.openclassrooms.mddapi.entity.User;

import java.util.Optional;

public interface RefreshTokenService {
   RefreshToken createRefreshToken(String username);
   void deleteByUser(User user);
    Optional<RefreshToken> verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);

}
