package com.directi.train.tweetapp.services;

import org.springframework.beans.factory.annotation.Autowired;
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
    private static SimpleJdbcTemplate db;

    @Autowired
    public RandomStore(SimpleJdbcTemplate template) {
        this.db = template;
    }

    public static String getAccessToken() {
        return  new Integer(db.queryForInt("select count(*) from auth") + 1).toString();
    }

    public static String getPassword() {
        String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int randomInt = getRandomInt(1, alpha.length());

        String pwd = "";
        while (--randomInt > 0) {
            pwd += alpha.charAt(getRandomInt(1, alpha.length()) - 1);
        }
        return pwd;
    }

    private static int getRandomInt(int lo, int hi) {
        return (int) ( lo + Math.ceil(Math.random() * (hi - lo + 1)));
    }
}
