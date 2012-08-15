package com.directi.train.tweetapp.services;

import com.directi.train.tweetapp.model.FeedItem;
import com.directi.train.tweetapp.model.UserProfileItem;
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
public class FeedStore  {
    @Autowired
    @Qualifier("simpleJdbcTemplate1")
    private SimpleJdbcTemplate db;

    @Autowired
    private UserStore localUserStore;

    private final int minTweetLength = 1;
    private final int maxTweetLength = 140;

    public FeedItem add(FeedItem feedItem,Long userId) {

        if (feedItem.getTweet().length() < minTweetLength) {
            return null;
        }
        if (feedItem.getTweet().length() > maxTweetLength) {
            feedItem.setTweet(feedItem.getTweet().substring(0, maxTweetLength));
        }

        String userName = (String)db.query(String.format("select username from users where id = %d", userId),new RowMapper<Object>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("username");
            }
        }).get(0);

        Long nextUniqueTweetId = feedItem.getTweetId();
        if (nextUniqueTweetId == null)
            nextUniqueTweetId = 1 + db.queryForLong(String.format("SELECT MAX(tweet_id) from (SELECT tweet_id from feeds where creator_id = %d) tweetidtable",
                                                    userId));

        Long creatorId = feedItem.getCreatorId();
        if (creatorId == null)
            creatorId = userId;

        db.update(String.format("insert into feeds (user_id, receiver_id, tweet, tweet_id, creator_id, timestamp) values(%d, %d, '%s', %d, %d, now())",
                userId, userId, feedItem.getTweet(), nextUniqueTweetId, creatorId));
        int id = db.queryForInt(String.format("select id from feeds where user_id =%d order by id desc limit 1", userId));

        List<UserProfileItem> followerIdList = localUserStore.followerList(userName);
        for (UserProfileItem userProfileItem : followerIdList) {
            Integer i = (Integer)userProfileItem.getId();
            db.update(String.format("insert into feeds (user_id, receiver_id, tweet, tweet_id, creator_id, timestamp) values(%d, %d, '%s', %d, %d, now())",
                    userId, i, feedItem.getTweet(), nextUniqueTweetId, creatorId));
        }

        return db.queryForObject(String.format(localUserStore.getPreSQL() + "feeds.id = %d" + localUserStore.getPostSQL() + "true" + localUserStore.getPreOrderSQL() + "desc",
                id), FeedItem.rowMapper);
    }

    public List<FeedItem> feed(Long userId) {
        String conditionalSQL = "feeds.receiver_id = %d";
        String orderingSQL = "desc limit %d";
        String otherCondition = "something.id > %d";
        return localUserStore.feedQueryAndFavoriteStatus(userId, userId, conditionalSQL, otherCondition, orderingSQL, localUserStore.getMinFeedId(), localUserStore.getFeedLimit());
    }

    public List<FeedItem> newFeedsList(Long feedId, Long userId) {
        String conditionalSQL = "feeds.receiver_id = %d";
        String orderingSQL = "asc limit %d";
        String otherCondition = "something.id > %d";
        return localUserStore.feedQueryAndFavoriteStatus(userId, userId, conditionalSQL, otherCondition, orderingSQL, feedId, localUserStore.getMaxFeedLimit());
    }

    public List<FeedItem> oldFeedsList(Long feedId, Long userId) {
        String conditionalSQL = "feeds.receiver_id = %d";
        String orderingSQL = "desc limit %d";
        String otherCondition = "something.id < %d";
        return localUserStore.feedQueryAndFavoriteStatus(userId, userId, conditionalSQL, otherCondition, orderingSQL, feedId, localUserStore.getFeedLimit());
    }

    public boolean favoriteTweet(Long creatorId, Long tweetId, Long userId) {
        if (localUserStore.isFavorited(creatorId, tweetId, userId))
            return false;
        return db.update(String.format("insert into favorites (tweet_id, user_id, creator_id) values (%d, %d, %d)", tweetId, userId, creatorId)) > 0;
    }

    public FeedItem reTweet(Long creatorId, Long tweetId, Long userId) {
        if (creatorId.equals(userId)) {
            return null;
        }
        if (localUserStore.isRetweeted(creatorId, tweetId, userId, this)) {
            return null;
        }
        db.update(String.format("insert into retweets (tweet_id, user_id, creator_id) values (%d, %d, %d)", tweetId, userId, creatorId));

        FeedItem feedItem = new FeedItem();
        feedItem.setTweetId(tweetId);
        String tweet = db.queryForObject(String.format("select tweet from feeds where tweet_id = %d and creator_id = %d and user_id = creator_id and user_id = receiver_id",
                tweetId, creatorId), new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("tweet");
                }
        });
        feedItem.setTweet(tweet);
        feedItem.setCreatorId(creatorId);
        return add(feedItem, userId);
    }

    public List<UserProfileItem> favoritedUsers(Long creatorId, Long tweetId) {
        return db.query(String.format("select id, username, email from (users inner join favorites on users.id = favorites.user_id) where creator_id = %d and  tweet_id = %d", creatorId, tweetId), UserProfileItem.rowMapper);
    }

    public List<UserProfileItem> reTweetedUsers(Long creatorId, Long tweetId) {
        return db.query(String.format("select id, username, email from (users inner join retweets on users.id = retweets.user_id) where creator_id = %d and  tweet_id = %d", creatorId, tweetId), UserProfileItem.rowMapper);
    }

}
