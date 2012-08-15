package com.directi.train.tweetapp.services;

import com.directi.train.tweetapp.services.Auxillary.ShardStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AuthStore {
    @Autowired private ShardStore shardStore;

    public Long getUserId(String accessToken) {
        List<Long> userIds = shardStore.getAuthShard().query("select user_id from auth where access_token = ?" , new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("user_id");
            }
        },accessToken);
        return userIds.get(0);
    }

    public String getUserName(String accessToken) {
        List<String> userNames = shardStore.getAuthShard().query("select user_name from auth where access_token = ?", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("user_name");
            }
        },accessToken);
        return userNames.get(0);
    }

    public void insert(String userName, long userId, String accessToken) {
        shardStore.getAuthShard().update("insert into auth (user_name, user_id, access_token) values (?, ?, ?)", userName, userId, accessToken);
    }

    public void remove(String accessToken) {
        shardStore.getAuthShard().update("delete from auth where access_token = ?", accessToken);
    }

    public boolean isValid(String accessToken) {
        return shardStore.getAuthShard().queryForInt("select count (*) from auth where access_token = ?", accessToken) == 1;
    }
}
