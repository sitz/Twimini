package com.directi.train.tweetapp.services;

import com.directi.train.tweetapp.model.TweetItem;
import com.directi.train.tweetapp.model.UserItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 20/7/12
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserStore {
    public SimpleJdbcTemplate db;


    @Autowired
    public UserStore(SimpleJdbcTemplate template) {
        db = template;
    }

    public int getUserId(String userName) {
        return db.queryForInt(String.format("select id from users where username='%s';", userName));
    }

    public List<Integer> following_list(String userName) {
        int userId = getUserId(userName);
        return db.query(String.format("select following_id from following where user_id =%d", userId), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("following_id");
            }
        });
    }


    public List<Integer> follower_list(String userName) {
        int userId = getUserId(userName);
        return db.query(String.format("select follower_id from followers where user_id =%d", userId), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("follower_id");
            }
        });
    }

    public String registerUser(String email,String userName,String password) {
        List<UserItem> userData = db.query(String.format("select * from users where username='%s' or email='%s'", userName,email), UserItem.rowMapper);
        UserItem userItem;
        long userID;
        try {
            userItem = userData.get(0);
            if(userItem.getEmail().equals(email) ){
                return "Email Already Registered to a different Username.";
            }
            if(userItem.getUsername().equals(userName)){
                return "Username Already Registered";
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            db.update("insert into users (email, username, password) values(?, ?, ?)",email, userName, password);
        }
        return "User Registered.";
    }

    public UserItem checkLogin(String userName,String password) throws Exception{
        ModelAndView mv = new ModelAndView("/index");
        UserItem userData;
        long userID;
        try {
            userData = db.query("select * from users where username='"+ userName +"'", UserItem.rowMapper).get(0);
            if (userData.getPassword().equals(password)) {
                userID = (Integer) userData.getId();
            } else {
                System.out.println(userData.getPassword());
                throw new Exception("Invalid Password");
            }
        }
        catch (EmptyResultDataAccessException e) {
            throw new Exception("User does not exist.Please Register");
        }
        return userData;
    }

    public void follow_user(String userName, Long userID) {
        try {
            long thisUserID = userID;
            System.out.println(thisUserID);
            long thatUserID = getUserId(userName);
            System.out.println(thatUserID);

            db.update("insert into following (user_id, following_id) values (? ,?)", thisUserID, thatUserID);
            db.update("insert into followers (user_id, follower_id) values  (?, ?)", thatUserID, thisUserID);
        }
        catch (IndexOutOfBoundsException E) {
            System.out.println("User " + userName + "doesn't exist !");
        }
        catch (Exception E) {
            System.out.println("Follow operation unsuccessful !");
            E.printStackTrace();
        }
    }

    public void unfollow_user(String userName, Long userID) {
        try {
            long thisUserID = userID;
            System.out.println(thisUserID);
            long thatUserID = getUserId(userName);
            System.out.println(thatUserID);

            db.update(String.format("delete from following where following_id = %d and user_id = %d", thisUserID, thatUserID));
            db.update(String.format("delete from followers where follower_id = %d and user_id = %d", thatUserID, thisUserID));
        }
        catch (IndexOutOfBoundsException E) {
            System.out.println("User " + userName + "doesn't exist !");
        }
        catch (Exception E) {
            System.out.println("unFollow operation unsuccessful !");
            E.printStackTrace();
        }
    }

    public List<Integer> favorite_tweets(String userName) {
        int userId = getUserId(userName);
        return db.query(String.format("select tweet_id from favorites where user_id = %d", userId), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("tweet_id");
            }
        } );
    }

    public List<Integer> re_tweets(String userName) {
        int userId = getUserId(userName);
        return db.query(String.format("select tweet_id from retweets where user_id = %d", userId), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("tweet_id");
            }
        } );
    }

    public void favorite_tweet(Integer tweetId, Long userID) {
        db.update("insert into favorites (tweet_id, user_id) values (?, ?)", tweetId, userID);
    }

    public void unfavorite_tweet(Integer tweetId, Long userID) {
        db.update(String.format("delete from favorites where tweet_id = %d and user_id = %d", tweetId, userID));
    }

    public void re_tweet(Integer tweetId, Long userID) {
        db.update("insert into retweets (tweet_id, user_id) values (?, ?)", tweetId, userID);
    }

    public void un_retweet(Integer tweetId, Long userID) {
        db.update(String.format("delete from retweets where tweet_id = %d and user_id = %d", tweetId, userID));
    }
}