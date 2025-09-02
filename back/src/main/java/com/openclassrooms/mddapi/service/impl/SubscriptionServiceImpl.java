package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.mapper.SubscriptionMapper;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.entity.Subscription;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.entity.Theme;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final ThemeRepository themeRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   UserRepository userRepository,
                                   SubscriptionMapper subscriptionMapper,
                                   ThemeRepository themeRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.themeRepository = themeRepository;
    }


    @Override
    public SubscriptionDto addSubscription(SubscriptionDto subscriptionDto) {
        // 1. Charger User et Theme ou lever 404
        User user = userRepository.findById(subscriptionDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Utilisateur introuvable : " + subscriptionDto.getUserId()));

        Theme theme = themeRepository.findById(subscriptionDto.getThemeId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Thème introuvable : " + subscriptionDto.getThemeId()));

        // 2. Construire et sauvegarder
        Subscription sub = Subscription.builder()
                .user(user)
                .theme(theme)
                .subscribedAt(LocalDateTime.now())
                .build();

        Subscription saved = subscriptionRepository.save(sub);

        // 3. Retourner le DTO de sortie (via MapStruct ou équivalent)
        return subscriptionMapper.toDto(saved);
    }
    @Override
    public void removeSubscription(Integer subscriptionId) {
        if (!subscriptionRepository.existsById(subscriptionId)) {
            throw new EntityNotFoundException("Souscription introuvable");
        }
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    public List<SubscriptionDto> findByUserId(Integer userId) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByUserId(userId);

        // 2️⃣ Mappe chaque Subscription (entité) en SubscriptionDto (DTO)
        return subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .toList();
    }

}
