package com.social.Social.util;

import com.social.Social.model.FollowRecord;
import com.social.Social.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private String userName;
    private String password;
    private String fullName;
    private List<FollowRecord> following;
    private List<FollowRecord> followers;
    private int userId;

    public CustomUserDetails() {
    }

    public CustomUserDetails(User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
        this.userId = user.getUserId();
        this.followers = user.getFollowers();
        this.following = user.getFollowing();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(new String[]{"ROLE_USER"})
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public List<FollowRecord> getFollowing() {
        return following;
    }

    public void setFollowing(List<FollowRecord> following) {
        this.following = following;
    }

    public List<FollowRecord> getFollowers() {
        return followers;
    }

    public void setFollowers(List<FollowRecord> followers) {
        this.followers = followers;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", following=" + following +
                ", followers=" + followers +
                ", userId=" + userId +
                '}';
    }
}
