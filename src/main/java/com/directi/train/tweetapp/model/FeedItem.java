package com.directi.train.tweetapp.model;

import de.bripkens.gravatar.Gravatar;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: sitesh
 * Date: 23/7/12
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedItem {
    private Long id;
    private Long userId;
    private String userName;
    private Long tweetId;
    private String tweet;
    private Long creatorId;
    private String creatorName;
    private boolean isRetweet;
    private boolean isFavorite;
    private Long favoriteCount;
    private Long retweetCount;
    private String profilePicURL;

    public static final RowMapper<FeedItem> rowMapper = new RowMapper<FeedItem>() {
        @Override public FeedItem mapRow(ResultSet resultSet, int i) throws SQLException {
            return new FeedItem(resultSet);
        }
    };

    public FeedItem() {}

    public FeedItem(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getLong("id");
        this.userId = resultSet.getLong("user_id");
        this.userName = resultSet.getString("username");
        this.tweetId = resultSet.getLong("tweet_id");
        this.tweet = resultSet.getString("tweet");
        this.creatorId = resultSet.getLong("creator_id");
        this.creatorName = resultSet.getString("creatorname");
        this.isRetweet = !creatorId.equals(userId);
        this.isFavorite = false;
        this.favoriteCount = 0L;
        this.retweetCount = 0L;
        this.profilePicURL = new Gravatar().getUrl( resultSet.getString("creatoremail") );
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public boolean isRetweet() {
        return isRetweet;
    }

    public void setRetweet(boolean retweet) {
        isRetweet = retweet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Long getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Long retweetCount) {
        this.retweetCount = retweetCount;
    }
}
