package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Subscription;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Long countByUser(User user);
    List<Subscription> findAllByUser(User user);

    // Méthode avec fetch EAGER pour charger les thèmes
    @Query("SELECT s FROM Subscription s " +
            "LEFT JOIN FETCH s.theme t " +
            "WHERE s.user.id = :userId " +
            "AND s.unsubscribedAt IS NULL")
    List<Subscription> findActiveSubscriptionsByUserId(@Param("userId") Integer userId);

    // Version alternative si vous voulez toutes les subscriptions (actives et inactives)
    @Query("SELECT s FROM Subscription s " +
            "LEFT JOIN FETCH s.theme t " +
            "WHERE s.user.id = :userId")
    List<Subscription> findAllByUserIdWithTheme(@Param("userId") Integer userId);
}
