package com.social.Social.repository;

import com.social.Social.model.Post;
import com.social.Social.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @NotNull
    @Override
    List<Post> findAllById(@NotNull Iterable<Integer> iterable);

    List<Post> findAllByUser(User user);
}
