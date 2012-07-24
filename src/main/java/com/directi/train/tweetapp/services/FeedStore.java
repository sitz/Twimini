package com.directi.train.tweetapp.services;

import com.directi.train.tweetapp.model.FeedItem;
import com.directi.train.tweetapp.model.TweetItem;
import org.hsqldb.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sitesh
 * Date: 24/7/12
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class FeedStore {
    private final ThreadLocal<Long> userID;
    public SimpleJdbcTemplate db;
    private UserStore userStore;

    @Autowired
    public FeedStore(@Qualifier("userID") ThreadLocal<Long> userID, SimpleJdbcTemplate simpleJdbcTemplate, UserStore userStore) {
        this.userID = userID;
        this.db = simpleJdbcTemplate;
        this.userStore = userStore;
    }

    public FeedItem add(FeedItem feedItem) {
        System.out.println(userID.get());
        System.out.println(feedItem.getTweet());

        String userName = (String)db.query(String.format("select username from users where id=%d", userID.get()),new RowMapper<Object>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("username");
            }
        }).get(0);
        int nextUniqueTweetId = 1 + (int) db.queryForInt("select max(tweet_id) from feeds");
        List<Integer> followerIdList = userStore.followerList(userName);
        for (Integer i : followerIdList) {
            System.out.println(i);
            db.update("insert into feeds (user_id, receiver_id, tweet, tweet_id, creator_id, timestamp) values(?, ?, ?, ?, ?, now())",
                    userID.get(), i, feedItem.getTweet(), nextUniqueTweetId, userID.get());
        }
        db.update("insert into feeds (user_id, receiver_id, tweet, tweet_id, creator_id, timestamp) values(?, ?, ?, ?, ?, now())",
                userID.get(), userID.get(), feedItem.getTweet(), nextUniqueTweetId, userID.get());
        int id = db.queryForInt(String.format("select id from feeds where user_id =%d order by id desc limit 1", userID.get()));
        return db.queryForObject(String.format("select something.id, user_id, something.username, tweet_id, tweet, creator_id, users.username as creatorname " +
                "from (select feeds.id, feeds.user_id , users.username, feeds.tweet_id, feeds.tweet, feeds.creator_id " +
                "from feeds inner join users " +
                "on users.id = feeds.user_id " +
                "where feeds.id = %d order by feeds.id desc) something inner join users " +
                "on something.creator_id = users.id", id), FeedItem.rowMapper);
    }

    public List<FeedItem> feed() {
        return db.query(String.format("select something.id, user_id, something.username, tweet_id, tweet, something.creator_id, users.username as creatorname " +
                "from (select feeds.id, feeds.user_id , users.username, feeds.tweet_id, feeds.tweet, feeds.creator_id " +
                "from feeds inner join users " +
                "on users.id = feeds.user_id " +
                "where feeds.receiver_id = %d " +
                "order by feeds.id desc) something inner join users " +
                "on something.creator_id = users.id", userID.get()), FeedItem.rowMapper);
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
