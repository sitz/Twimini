package com.directi.train.tweetapp.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TweetItem {
    private String tweet;
    private int id;
    private int receiverId;

    public static final RowMapper<TweetItem> rowMapper = new RowMapper<TweetItem>() {
        @Override public TweetItem mapRow(ResultSet resultSet, int i) throws SQLException {
            return new TweetItem(resultSet);
        }
    };
    public TweetItem(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        tweet = rs.getString("description");
        receiverId = rs.getInt("receiver_id");
    }

    public TweetItem() { }

    public String getTweet() {
        return this.tweet;
    }

    public void setTweet(String name) {
        this.tweet = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
}