package com.social.Social.dto;

import com.social.Social.model.FollowRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int userId;
    private String userName;
    private String password;
    private String fullName;
    private List<FollowRecord> following;
    private List<FollowRecord> followers;

}
