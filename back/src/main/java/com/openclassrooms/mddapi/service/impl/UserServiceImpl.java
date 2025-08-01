package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.ProfilDto;
import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.Subscription;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.mapper.SubscriptionMapper;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final PostRepository postRepository;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder, SubscriptionRepository subscriptionRepository, SubscriptionMapper subscriptionMapper, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.postRepository = postRepository;
    }


    @Override
    public UserDto save(UserDto userDto) {
        LocalDateTime now = LocalDateTime.now();
        userDto.setId(null);
        User entity = userMapper.toEntity(userDto);
        if (entity.getId() == null) {
            entity.setCreatedAt(now);
            entity.setUpdateAt(now);
            entity.setUsername(userDto.getUsername());
        }
        entity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        User saved = userRepository.save(entity);
        return userMapper.toDto(saved);
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByFirstname(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).collect(toList());

    }

    @Override
    public ProfilDto getMyProfile(Integer userid) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(userid)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // 2️⃣ Nombre total d'abonnements
        long subscriptionsCount = subscriptionRepository.countByUser(user);
        long topicsCreatedCount      = postRepository.countByUserid(user);
        // nom de post crée
        // 3️⃣ Détail des abonnements
        List<Subscription> subs = subscriptionRepository.findAllByUser(user);
        long subscribedSubjectsCount = subs.size();
        List<SubscriptionDto> subDtos = subs.stream()
                .map(subscriptionMapper::toDto)
                .collect(toList());

        return ProfilDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .topicsCreatedCount(topicsCreatedCount)
                .subscriptionsCount(subscriptionsCount)
                .subscribedSubjectsCount(subscribedSubjectsCount)
                .subscriptions(subDtos)
                .build();
    }

    @Override
    public UserDto updateProfile(Integer userId, UserDto userDto) {
        // 1️⃣ Récupère l’entité existante
        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));

        // 2️⃣ Met à jour les champs autorisés
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            existing.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            // encoder le nouveau mot de passe
            existing.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
        // (tu peux ajouter d’autres champs de profil ici)

        // 3️⃣ Sauvegarde et mappe vers le DTO de sortie
        User saved = userRepository.save(existing);
        return userMapper.toDto(saved);
    }
}
