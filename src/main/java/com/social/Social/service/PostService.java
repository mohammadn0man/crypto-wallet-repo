package com.social.Social.service;

import com.social.Social.dto.PostDto;
import com.social.Social.model.Post;
import com.social.Social.repository.PostRepository;
import com.social.Social.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FollowRecordService followRecordService;

    /**
     * service to tweet post to feeds
     *
     * @param postDto post data
     * @return boolean response
     */
    public boolean addPost(PostDto postDto) {
        Post postModel = MapperUtil.getModelMapper()
                .map(postDto, Post.class);
        postModel.setTimestamp(new Date());
        try {
            postRepository.save(postModel);
        } catch (Exception e) {
            log.error("Post not saved");
            return false;
        }
        return true;
    }


    /**
     * get all the post for following users
     *
     * @param userName name of user for whom response is required
     * @return list of desired posts
     */
    public List<PostDto> getPost(String userName) {
        List<Integer> user = getUsersList(userName);
        List<Post> posts = new ArrayList<Post>();
        for (int i : user) {
            posts.addAll(postRepository.findAllByUser(userService.getUserById(i).orElseGet(null)));
        }
        return getPostDto(posts);
    }

    private List<Integer> getUsersList(String userName) {
        return followRecordService.getFollowers(userService
                .loadUserByUsername(userName)
                .getUserId());
    }

    @NotNull
    private List<PostDto> getPostDto(List<Post> res) {
        List<PostDto> ret = new ArrayList<>();
        for (Post x : res) {
            ret.add(MapperUtil.getModelMapper().map(x, PostDto.class));
        }
        return ret;
    }
}
