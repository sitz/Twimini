package com.directi.train.tweetapp.services;

import com.directi.train.tweetapp.model.TweetItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TweetStore {
    private final ThreadLocal<Long> userID;
    public SimpleJdbcTemplate db;
    private UserStore userStore;

    @Autowired
    public TweetStore(@Qualifier("userID") ThreadLocal<Long> userID, SimpleJdbcTemplate template,UserStore userStore) {
        this.userID = userID;
        this.db = template;
        this.userStore= userStore;
    }

    public List<TweetItem> list() {
        return db.query("select * from feeds where receiver_id = ? order by id desc", TweetItem.rowMapper, userID.get());
    }

    public TweetItem add(TweetItem tweetItem) {
        System.out.println(userID.get());
        System.out.println(tweetItem.getTweet());
        String userName = (String)db.query(String.format("select username from users where id=%d", userID.get()),new RowMapper<Object>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("username");
            }
        }).get(0);
        List<Integer> followerIdList = userStore.follower_list(userName);
        for (Integer i : followerIdList) {
            System.out.println(i);
            db.update("insert into feeds (user_id, receiver_id, tweet, timestamp) values(?,?,?,now())",userID.get(), i, tweetItem.getTweet());
        }
        db.update("insert into feeds (user_id, receiver_id, tweet,timestamp) values(?,?,?,now())",userID.get(), userID.get(), tweetItem.getTweet());
        int id = db.queryForInt(String.format("select id from feeds where user_id =%d order by id desc limit 1", userID.get()));
        return db.queryForObject("select * from feeds where id=?", TweetItem.rowMapper, id);
    }

    public List<TweetItem> profile() {
        return list();
    }

}
