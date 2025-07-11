package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubscriptionDto;

public interface SubscriptionService {
    SubscriptionDto addSubscription(SubscriptionDto subscriptionDto);
     void deleteByUserAndSubject(Integer userId, Integer subjectId);
}
