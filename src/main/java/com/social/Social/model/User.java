package com.social.Social.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @TableGenerator(name = "User_Gen",
            table = "ID_GEN",
            pkColumnName = "GEN_NAME",
            valueColumnName = "GEN_VAL",
            pkColumnValue = "User_Gen",
            initialValue = 10000,
            allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "User_Gen")
    private int userId;

    @Column(unique = true)
    private String userName;

    private String fullName;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = FollowRecord.class)
    @JoinColumn(name = "followed_by_user_id")
    private List<FollowRecord> following;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = FollowRecord.class)
    @JoinColumn(name = "followed_to_user_id")
    private List<FollowRecord> followers;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}
