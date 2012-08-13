package com.directi.train.tweetapp.controllers.WebApp.Helper;

import com.directi.train.tweetapp.services.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 13/8/12
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class UserListPageHelper {
    @Autowired private UserStore userStore;

    public void addUserData(ModelAndView modelAndView,String userName) {
        modelAndView.addObject("userName", userName);
        modelAndView.addObject("noTweets",userStore.noOfTweets(userName));
        modelAndView.addObject("noFollow",userStore.noOfFollowers(userName));
        modelAndView.addObject("noFollowing",userStore.noOfFollowing(userName));
        modelAndView.addObject("userProfileItem", userStore.getUserPofileItem(userName));
    }

    public void addPageData(ModelAndView modelAndView,String url,String head,String title) {
        modelAndView.addObject("url",url);
        modelAndView.addObject("head",head);
        modelAndView.addObject("title",title);
    }
}
