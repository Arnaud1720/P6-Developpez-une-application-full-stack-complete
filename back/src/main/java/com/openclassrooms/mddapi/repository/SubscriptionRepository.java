package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Subjects;
import com.openclassrooms.mddapi.entity.Subscription;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Long countByUser(User user);
    List<Subscription> findAllByUser(User user);

    boolean existsByUserAndSubjects(User user, Subjects subject);

    Optional<Subscription> findByUserIdAndSubjectsId(Integer userId, Integer subjectId);
}
