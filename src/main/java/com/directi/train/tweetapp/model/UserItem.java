package com.directi.train.tweetapp.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 20/7/12
 * Time: 12:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserItem {
    private int user_id;
    private String username;
    private String email;
    private String password;

    public static final RowMapper<UserItem> rowMapper = new RowMapper<UserItem>() {
        @Override
        public UserItem mapRow(ResultSet resultSet, int i) throws SQLException {
            return new UserItem(resultSet);
        }
    };

    public UserItem(ResultSet resultSet) throws SQLException {
        user_id = resultSet.getInt("id");
        username = resultSet.getString("username");
        email = resultSet.getString("email");
        password = resultSet.getString("password");
    }
}
