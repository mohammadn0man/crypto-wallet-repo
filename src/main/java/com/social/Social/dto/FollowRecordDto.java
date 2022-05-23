package com.social.Social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowRecordDto {
    private Long id;
    private int followedByUserId;
    private int followedToUserId;
}
