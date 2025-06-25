package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private Integer id;
    private int userId;
    private int subjectid;
    private Instant subscribedAt;
    private Instant unsubscribedAt;
}
