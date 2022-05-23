package com.social.Social.controller;


import com.social.Social.dto.FollowRecordDto;
import com.social.Social.exception.RequestParameterException;
import com.social.Social.service.FollowRecordService;
import com.social.Social.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follower")
public class FollowRecordController {
    private final FollowRecordService followRecordService;

    /**
     * follow any other user
     * @param followRecordDto id contains record data
     * @return response 202 on successful else error
     * @throws RequestParameterException for invalid params
     */
    @PostMapping("/add")
    public ResponseEntity<String> followUser(@RequestBody FollowRecordDto followRecordDto) throws RequestParameterException {
        return ResponseUtil.filterResponse(followRecordService.follow(followRecordDto));
    }

//    @PostMapping("/remove")
//    public ResponseEntity<String> unfollowUser(@RequestBody FollowRecordDto followRecordDto) throws RequestParameterException {
//        return ResponseUtil.filterResponse(followRecordService.unfollow(followRecordDto));
//    }
}
