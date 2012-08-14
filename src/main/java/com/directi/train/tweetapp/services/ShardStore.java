package com.directi.train.tweetapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.SpanShapeRenderer;

@Service
public class ShardStore {
    @Autowired
    @Qualifier("simpleJdbcTemplate2")
    private SimpleJdbcTemplate db2;

    @Autowired
    @Qualifier("simpleJdbcTemplate1")
    private SimpleJdbcTemplate db1;

    @Autowired
    @Qualifier("ShardTemplate")
    private SimpleJdbcTemplate shardDB;

    public SimpleJdbcTemplate getShardById(Long userId) {
        return db1;
    }
    public SimpleJdbcTemplate getShardByUserName(String userName) {
        return db1;
    }
}
