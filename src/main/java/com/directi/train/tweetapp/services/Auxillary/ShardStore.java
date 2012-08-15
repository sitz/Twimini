package com.directi.train.tweetapp.services.Auxillary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShardStore {
    @Autowired
    @Qualifier("simpleJdbcTemplate2")
    private SimpleJdbcTemplate db2;

    @Autowired
    @Qualifier("simpleJdbcTemplate1")
    private SimpleJdbcTemplate db1;

    @Autowired
    @Qualifier("shardTemplate")
    private SimpleJdbcTemplate shardDB;

    public SimpleJdbcTemplate getShardByUserId(Long userId) {
        return db1;
    }

    public SimpleJdbcTemplate getShardByUserName(String userName) {
        return db1;
    }

    public SimpleJdbcTemplate getShardByUserEmail(String eMail) {
        return db1;
    }

    public SimpleJdbcTemplate getNewUserShard() {
        return db1;
    }
    public SimpleJdbcTemplate getAuthShard() {
        return db1;
    }
}
