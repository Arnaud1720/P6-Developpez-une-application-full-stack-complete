package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.entity.Subjects;
import com.openclassrooms.mddapi.mapper.SubscriptionMapper;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.entity.Subscription;
import com.openclassrooms.mddapi.entity.User;

import java.time.LocalDateTime;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final SubscriptionMapper subscriptionMapper;


    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UserRepository userRepository, SubjectRepository subjectRepository, SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.subscriptionMapper = subscriptionMapper;
    }


    @Override
    public SubscriptionDto addSubscription(SubscriptionDto subscriptionDto) {
        // 1. Charger User et Subject ou lever 404
        User user = userRepository.findById(subscriptionDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable : " + subscriptionDto.getUserId()));
        Subjects subject = subjectRepository.findById(subscriptionDto.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Sujet introuvable : " + subscriptionDto.getSubjectId()));

        // 2. Optionnel : vérifier qu’on n’est pas déjà abonné
        boolean alreadySub = subscriptionRepository
                .existsByUserAndSubjects(user, subject);
        if (alreadySub) {
            throw new IllegalStateException("Vous êtes déjà abonné à ce sujet");
        }

        // 3. Construire et sauvegarder
        Subscription sub = Subscription.builder()
                .user(user)
                .subjects(subject)
                .subscribedAt(LocalDateTime.now())
                .build();
        Subscription saved = subscriptionRepository.save(sub);

        // 4. Retourner le DTO de sortie
        return subscriptionMapper.toDto(saved);
    }

    @Override
    public void deleteByUserAndSubject(Integer userId, Integer subjectId) {
        Subscription sub = subscriptionRepository
                .findByUserIdAndSubjectsId(userId, subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Abonnement introuvable"));
        subscriptionRepository.delete(sub);
    }
}
