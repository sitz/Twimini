package com.directi.train.tweetapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sitesh
 * Date: 8/8/12
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AuthStore {
    @Autowired
    private SimpleJdbcTemplate db;

    public Long getUserId(String accessToken) {
        List<Long> userIds = db.query(String.format("select user_id from auth where access_token = '%s'", accessToken), new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("user_id");
            }
        });
        return userIds.get(0);
    }

    public String getUserName(String accessToken) {
        List<String> userNames = db.query(String.format("select user_name from auth where access_token = '%s'", accessToken), new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("user_name");
            }
        });
        return userNames.get(0);
    }

    public void insert(String userName, long userId, String accessToken) {
        db.update(String.format("insert into auth (user_name, user_id, access_token) values ('%s', %d, '%s')", userName, userId, accessToken));
    }

    public void remove(String accessToken) {
        db.update(String.format("delete from auth where access_token = '%s'", accessToken));
    }

    public boolean isValid(String accessToken) {
        return db.queryForInt(String.format("select count (*) from auth where access_token = '%s'", accessToken)) == 1;
    }
}
