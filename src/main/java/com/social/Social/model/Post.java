package com.social.Social.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Posts")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "postContent", length = 6380)
    private String postContent;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",  timezone = "IST")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
}
