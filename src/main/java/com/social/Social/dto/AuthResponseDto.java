package com.social.Social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private final String userName;
    private final String fullName;
    private final int userId;
    private final String jwt;
}

