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

    public List<Integer> following_list(String userName) {
        int userId = db.queryForInt(String.format("select id from users where username='%s';", userName));
        return db.query(String.format("select following_id from following where user_id =%d", userId), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("following_id");
            }
        });
    }

    public List<Integer> follower_list(String userName) {
        int userId = db.queryForInt(String.format("select id from users where username='%s';", userName));
        return db.query(String.format("select follower_id from followers where user_id =%d", userId), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("follower_id");
            }
        });
    }

    public int getUserId(String userName) {
        return db.queryForInt(String.format("select id from users where username='%s';", userName));
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

    public int followUser(String userName, Long userID) {
        try {
            long thisUserID = userID;
            System.out.println(thisUserID);
            long thatUserID = getUserId(userName);
            System.out.println(thatUserID);

            db.update("insert into following (user_id, following_id) values (? ,?)", thisUserID, thatUserID);
            db.update("insert into followers (user_id, follower_id) values  (?, ?)", thatUserID, thisUserID);
            return 0;
        }
        catch (IndexOutOfBoundsException E) {
            System.out.println("User " + userName + "doesn't exist !");
            return 1;
        }
        catch (Exception E) {
            System.out.println("Follow operation unsuccessful !");
            E.printStackTrace();
            return 1;
        }
    }

    public int unFollowUser(String userName,Long userID) {
        try {
            long thisUserID = userID;
            System.out.println(thisUserID);
            long thatUserID = getUserId(userName);
            System.out.println(thatUserID);

            db.update(String.format("delete from following where following_id = %d and user_id = %d",  thatUserID,thisUserID));
            db.update(String.format("delete from followers where follower_id = %d and user_id = %d", thisUserID,thatUserID ));
            return 0;
        }
        catch (IndexOutOfBoundsException E) {
            System.out.println("User " + userName + "doesn't exist !");
            return 1;
        }
        catch (Exception E) {
            System.out.println("unFollow operation unsuccessful !");
            E.printStackTrace();
            return 1;
        }
    }

    public List<TweetItem> tweetList(String userName) {
        int userID = getUserId(userName);
        return db.query(String.format("select * from feeds where receiver_id = %d and user_id = %d order by id desc",userID,userID), TweetItem.rowMapper);
    }

    public Integer checkFollowingStatus(String curUser,String otherUser) {
        return db.queryForInt(String.format("select count(*) from followers where user_id = %d and follower_id = %d",
                getUserId(otherUser),getUserId(curUser)));
    }

    public Integer noOfFollowers(String userName) {
        return db.queryForInt(String.format("select count(*) from followers where user_id=%d",getUserId(userName)));
    }

    public Integer noOfFollowing(String userName) {
        return db.queryForInt(String.format("select count(*) from following where user_id=%d",getUserId(userName)));
    }

}