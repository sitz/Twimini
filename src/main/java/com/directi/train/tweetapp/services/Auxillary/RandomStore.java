package com.directi.train.tweetapp.services.Auxillary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: sitesh
 * Date: 8/8/12
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RandomStore {
    @Autowired private ShardStore shardStore;

    public String getAccessToken() {
        return Integer.toString(shardStore.getAuthShard().queryForInt("select max(access_token) from auth")) + 1;
    }

    public String getPassword() {
        String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int randomInt = getRandomInt(1, 16);

        String pwd = "";
        while (randomInt-- > 0) {
            pwd += alpha.charAt(getRandomInt(1, alpha.length()) - 1);
        }
        return pwd;
    }

    private int getRandomInt(int lo, int hi) {
        return (int) (lo + Math.ceil(Math.random() * (hi - lo)));
    }
}
