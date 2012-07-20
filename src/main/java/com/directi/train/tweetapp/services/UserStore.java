package com.directi.train.tweetapp.services;

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
    private final ThreadLocal<Long> userID;
    public SimpleJdbcTemplate db;


    @Autowired
    public UserStore(@Qualifier("userID") ThreadLocal<Long> userID, SimpleJdbcTemplate template) {
        this.userID = userID;
        db = template;
    }

    public List<Object> following_list(String userName) {
        int userId = db.queryForInt(String.format("select id from users where username='%s';", userName));
        return db.query(String.format("select following_id from following where user_id =%d", userId), new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("following_id");
            }
        });
    }


    public List<Object> follower_list(String userName) {
        int userId = db.queryForInt(String.format("select id from users where username='%s';", userName));
        return db.query(String.format("select follower_id from followers where user_id =%d", userId), new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
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

    public void follow_user(String userName) {
        try {
            long thisUserID = this.userID.get();
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

    public void unfollow_user(String userName) {
        try {
            long thisUserID = this.userID.get();
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
            System.out.println("Unfollow operation unsuccessful !");
            E.printStackTrace();
        }
    }
}