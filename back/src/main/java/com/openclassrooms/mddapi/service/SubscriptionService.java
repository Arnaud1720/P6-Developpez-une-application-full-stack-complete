package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDto addSubscription(SubscriptionDto subscriptionDto);
    void removeSubscription(Integer subscriptionId);
    List<SubscriptionDto> findByUserId(Integer userId);

}
