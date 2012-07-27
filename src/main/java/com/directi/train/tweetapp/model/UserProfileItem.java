package com.directi.train.tweetapp.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 27/7/12
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserProfileItem {
    private int user_id;
    private String username;
    private String email;

    public static final RowMapper<UserProfileItem> rowMapper = new RowMapper<UserProfileItem>() {
        @Override
        public UserProfileItem mapRow(ResultSet resultSet, int i) throws SQLException {
            return new UserProfileItem(resultSet);
        }
    };

    public UserProfileItem(ResultSet resultSet) throws SQLException {
        user_id = resultSet.getInt("id");
        username = resultSet.getString("username");
        email = resultSet.getString("email");
    }
    public int getId() {
        return user_id;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
}
