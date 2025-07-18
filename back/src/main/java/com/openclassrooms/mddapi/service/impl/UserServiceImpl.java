package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.ProfilDto;
import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.Subscription;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.mapper.SubscriptionMapper;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.UserService;
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


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder, SubscriptionRepository subscriptionRepository, SubscriptionMapper subscriptionMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
    }


    @Override
    public UserDto save(UserDto userDto) {
        LocalDateTime now = LocalDateTime.now();
        userDto.setId(null);
        User entity = userMapper.toEntity(userDto);
        if (entity.getId() == null) {
            entity.setCreatedAt(now);
            entity.setUpdateAt(now);
            entity.setUsername(entity.getUsername());
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
                .subscriptionsCount(subscriptionsCount)
                .subscribedSubjectsCount(subscribedSubjectsCount)
                .subscriptions(subDtos)
                .build();
    }
}
