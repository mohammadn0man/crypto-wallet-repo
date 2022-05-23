package com.social.Social.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "follower_record")
public class FollowRecord {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "followed_by_user_id")
    private int followedByUserId;

    @Column(name = "followed_to_user_id")
    private int followedToUserId;
}
