package com.social.Social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private int postId;
    private int userId;
    private String postContent;
    private Date timestamp;
}
