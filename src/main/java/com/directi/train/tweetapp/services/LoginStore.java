package com.directi.train.tweetapp.services;

import com.directi.train.tweetapp.model.UserItem;
import com.directi.train.tweetapp.services.Auxillary.PasswordStore;
import com.directi.train.tweetapp.services.Auxillary.RandomStore;
import com.directi.train.tweetapp.services.Auxillary.ShardStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 14/8/12
 * Time: 2:38 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class LoginStore {
    @Autowired
    private RandomStore randomStore;

    @Autowired
    private ShardStore shardStore;

    private final int minUserLength = 1;
    private final int maxUserLength = 16;
    private final int minPasswordLength = 1;
    private final int maxPasswordLength = 16;

    public String registerUser(String email,String userName,String password) {
        if (!email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            return "3";
        }
        if (!userName.matches("[A-Za-z0-9_]*") || userName.length() < minUserLength || userName.length() > maxUserLength) {
            return "4";
        }
        if (password.length() < minPasswordLength || password.length() > maxPasswordLength) {
            return "5";
        }

        List<UserItem> userData = shardStore.getShardByUserName(userName).query("select * from users where username= ? or email=?", UserItem.rowMapper,userName, email );
        UserItem userItem;

        try {
            userItem = userData.get(0);
            if(userItem.getEmail().equals(email) ){
                return "1";
            }
            if(userItem.getUsername().equals(userName)){
                return "2";
            }
        }
        catch (IndexOutOfBoundsException e) {
            password = PasswordStore.SHA(password);
            shardStore.getNewUserShard().update("insert into users (email, username, password) values(?, ?, ?)", email, userName, password);
        }
        return "0";
    }

    public UserItem checkLogin(String userName,String password) throws Exception {
        UserItem userData;
        try {
            userData = shardStore.getShardByUserName(userName).query("select * from users where username = ?", UserItem.rowMapper, userName).get(0);
            if (userData.getPassword().equals(PasswordStore.SHA(password))) {
                userData.getId();
            } else {
                throw new Exception("Invalid Password");
            }
        }
        catch (EmptyResultDataAccessException e) {
            throw new Exception("User does not exist.Please Register");
        }
        return userData;
    }

    public void changePassword(String password, String userName) {
        shardStore.getShardByUserName(userName).update("update users set password = ? where username = ?", PasswordStore.SHA(password), userName);
        String eMail = shardStore.getShardByUserName(userName).query("select email from users where username = ?",  new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("email");
            }
        },userName).get(0);
        PasswordStore.sendPassword(eMail, password);
    }

    public void forgotPassword(String userName) {
        String eMail = null;
        try {
            eMail = shardStore.getShardByUserName(userName).query("select email from users where username = ?", new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("email");
                }
            },userName).get(0);
        }  catch (Exception E) {
            E.printStackTrace();
        }
        String pwd = randomStore.getPassword();
        shardStore.getShardByUserName(userName).update("update users set password = ? where email = ?", PasswordStore.SHA(pwd), eMail);
        PasswordStore.sendPassword(eMail, pwd);
    }

}
