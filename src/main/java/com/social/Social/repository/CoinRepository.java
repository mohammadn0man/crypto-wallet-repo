package com.social.Social.repository;

import com.social.Social.model.Coin;
import com.social.Social.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoinRepository extends CrudRepository<Coin, Integer> {
    List<Coin> findAllByUser(User user);
}
