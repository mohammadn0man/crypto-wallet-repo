package com.social.Social.repository;

import com.social.Social.model.FollowRecord;
import org.springframework.data.repository.CrudRepository;

public interface FollowRecordRepository extends CrudRepository<FollowRecord, Integer> {
    Iterable<FollowRecord> findAllByFollowedByUserId(int id);

    boolean existsByFollowedByUserIdAndFollowedToUserId(Integer by, Integer to);
}
