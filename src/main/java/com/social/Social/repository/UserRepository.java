package com.social.Social.repository;

import com.social.Social.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String name);
}
