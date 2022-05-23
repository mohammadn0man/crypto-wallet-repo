package com.social.Social.service;

import com.social.Social.dto.FollowRecordDto;
import com.social.Social.model.FollowRecord;
import com.social.Social.repository.FollowRecordRepository;
import com.social.Social.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FollowRecordService {
    @Autowired
    private FollowRecordRepository followRecordRepository;

    /**
     * get the list of followers for given user id
     *
     * @param id user_id
     * @return list of followers
     */
    public List<Integer> getFollowers(int id) {
        Iterable<FollowRecord> followers = followRecordRepository
                .findAllByFollowedByUserId(id);
        List<Integer> result = new ArrayList<>();
        for (FollowRecord x : followers) {
            result.add(x.getFollowedToUserId());
        }
        return result;
    }

    /**
     * follow the user service check if record already exist else add
     *
     * @param followRecordDto record contains data for followed by and followed to users
     * @return boolean response
     */
    public boolean follow(FollowRecordDto followRecordDto) {
        if (followRecordRepository.existsByFollowedByUserIdAndFollowedToUserId(followRecordDto.getFollowedByUserId(), followRecordDto.getFollowedToUserId())) {
            return true;
        }
        FollowRecord followModel = MapperUtil
                .getModelMapper()
                .map(followRecordDto, FollowRecord.class);
        try {
            followRecordRepository.save(followModel);
        } catch (Exception e) {
            log.error("follower not saved");
            return false;
        }
        return true;
    }

//    public boolean unfollow(FollowRecordDto followRecordDto) {
//        if (!followRecordRepository.existsByFollowedByUserIdAndFollowedToUserId(followRecordDto.getFollowedByUserId(), followRecordDto.getFollowedToUserId())) {
//            return true;
//        }
//        Follow followModel = MapperUtil
//                .getModelMapper()
//                .map(followRecordDto, FollowRecord.class);
//        try {
//            followRecordRepository.delete(followModel);
//        } catch (Exception e) {
//            log.error("changes to unfollow not saved");
//            return false;
//        }
//        return true;
//    }
}
