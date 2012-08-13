package com.directi.train.tweetapp.controllers.WebApp.Helpers;

import com.directi.train.tweetapp.services.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 13/8/12
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserListModelAndView extends ModelAndView{
    public UserListModelAndView() {
        super("userlist");
    }

    public UserListModelAndView(String string) {
        super(string);
    }
    public void addUserData(String userName,UserStore userStore) {
        this.addObject("userName", userName);
        this.addObject("noTweets", userStore.noOfTweets(userName));
        this.addObject("noFollow", userStore.noOfFollowers(userName));
        this.addObject("noFollowing", userStore.noOfFollowing(userName));
        this.addObject("userProfileItem", userStore.getUserPofileItem(userName));
    }

    public void addPageData(String url,String head,String title) {
        this.addObject("url",url);
        this.addObject("head",head);
        this.addObject("title",title);
    }
}
