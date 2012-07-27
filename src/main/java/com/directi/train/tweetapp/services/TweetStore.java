package com.directi.train.tweetapp.services;

import com.directi.train.tweetapp.model.TweetItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TweetStore {
    public SimpleJdbcTemplate db;
    private UserStore userStore;

    @Autowired
    public TweetStore(SimpleJdbcTemplate template,UserStore userStore) {
        this.db = template;
        this.userStore= userStore;
    }

    public List<TweetItem> feed(Long userId) {
        return db.query("select * from feeds where receiver_id = ? order by id desc", TweetItem.rowMapper, userId);
    }

    public TweetItem add(TweetItem tweetItem, Long userId) {
        System.out.println(userId);
        System.out.println(tweetItem.getTweet());

        String userName = (String)db.query(String.format("select username from users where id=%d", userId),new RowMapper<Object>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("username");
            }
        }).get(0);
        int nextUniqueTweetId = 1 + (int) db.queryForInt("select max(tweet_id) from feeds");
        List<Long> followerIdList = userStore.followerList(userName);
        for (Long i : followerIdList) {
            System.out.println(i);
            db.update("insert into feeds (user_id, receiver_id, tweet, tweet_id, timestamp) values(?, ?, ?, ?, now())",
                    userId, i, tweetItem.getTweet(), nextUniqueTweetId);
        }
        db.update("insert into feeds (user_id, receiver_id, tweet, tweet_id, timestamp) values(?, ?, ?, ?, now())",
                userId, userId, tweetItem.getTweet(), nextUniqueTweetId);
        int id = db.queryForInt(String.format("select id from feeds where user_id =%d order by id desc limit 1", userId));
        return db.queryForObject("select * from feeds where id=?", TweetItem.rowMapper, id);
    }

    public List<Integer> favoritingUsers(Integer tweetId) {
        return db.query(String.format("select user_id from favorites where tweet_id = %d", tweetId), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("user_id");
            }
        });
    }

    public List<Integer> retweetingUsers(Integer tweetId) {
        return db.query(String.format("select user_id from retweets where tweet_id = %d", tweetId), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("user_id");
            }
        });
    }
}
