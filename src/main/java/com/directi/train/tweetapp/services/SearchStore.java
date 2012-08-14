package com.directi.train.tweetapp.services;

import com.directi.train.tweetapp.model.UserProfileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchStore {
    @Autowired
    @Qualifier("simpleJdbcTemplate1")
    private SimpleJdbcTemplate db;

    public List<UserProfileItem> getResults(String query, Long userId) {
        List<UserProfileItem> usersList = db.query(String.format("select * from users where username like '%%%s%%' or email like '%%%s%%@%%.%%'",
                query,query), UserProfileItem.rowMapper);
        for (UserProfileItem user : usersList) {
            user.setFollowing(db.queryForInt(String.format("select count(*) from following where user_id = %d and following_id = %d", userId, user.getId())) > 0);
        }

        return usersList;
    }
}