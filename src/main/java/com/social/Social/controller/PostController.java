package com.social.Social.controller;


import com.social.Social.dto.PostDto;
import com.social.Social.exception.RequestParameterException;
import com.social.Social.service.PostService;
import com.social.Social.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    /**
     * post tweet to the feed
     * @param postDto post's content and other details
     * @return 202 for success else error
     * @throws RequestParameterException for invalid params
     */
    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto) throws RequestParameterException {
        return ResponseUtil.filterResponse(postService.addPost(postDto));
    }

    /**
     * get all post from user which is followed
     * @param userName name of user for whom we want to get feed
     * @return list of posts to show on feeds
     */
    @GetMapping("/get/{userName}")
    public List<PostDto> getPost(@PathVariable String userName){
        return postService.getPost(userName);
    }

}
