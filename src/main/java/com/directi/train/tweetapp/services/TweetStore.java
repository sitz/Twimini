package com.directi.train.tweetapp.services;

import com.directi.train.tweetapp.model.TweetItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetStore {
    private final ThreadLocal<Long> userID;
    public SimpleJdbcTemplate db;

    @Autowired
    public TweetStore(@Qualifier("userID") ThreadLocal<Long> userID, SimpleJdbcTemplate template) {
        this.userID = userID;
        db = template;
    }

    public List<TweetItem> list() {
        return db.query("select * from feeds where user_id=? order by id asc", TweetItem.rowMapper, userID.get());
    }

    public TweetItem add(TweetItem tweetItem) {
        System.out.println(userID.get());
        System.out.println(tweetItem.getTweet());
        db.update("insert into feeds (user_id, receiver_id, description) values(?,?,?)",userID.get(), userID.get(), tweetItem.getTweet());
        int id = db.queryForInt("CALL IDENTITY()");
        return db.queryForObject("select * from feeds where id=?", TweetItem.rowMapper, id);
    }

    public List<TweetItem> profile() {
        return list();
    }

}