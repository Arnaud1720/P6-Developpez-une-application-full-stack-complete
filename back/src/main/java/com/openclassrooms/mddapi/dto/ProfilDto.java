package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private long subscriptionsCount;
    private long subscribedSubjectsCount;
    private List<SubscriptionDto> subscriptions;
    private long topicsCreatedCount;

}
