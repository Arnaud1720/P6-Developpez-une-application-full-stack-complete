package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Subscription;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Long countByUser(User user);
    List<Subscription> findAllByUser(User user);
    List<Subscription> findAllByUserId(Integer userId);
}
