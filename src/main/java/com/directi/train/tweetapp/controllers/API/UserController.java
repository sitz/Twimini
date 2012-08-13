package com.directi.train.tweetapp.controllers.API;

import com.directi.train.tweetapp.model.FeedItem;
import com.directi.train.tweetapp.model.UserProfileItem;
import com.directi.train.tweetapp.services.AuthStore;
import com.directi.train.tweetapp.services.FeedStore;
import com.directi.train.tweetapp.services.LoginStore;
import com.directi.train.tweetapp.services.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 13/8/12
 * Time: 8:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired private UserStore userStore;
    @Autowired private AuthStore authStore;
    @Autowired private LoginStore loginStore;

    @RequestMapping(value = "following/{username}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserProfileItem> getFollowing(@PathVariable("username") String userName) {
        return userStore.followingList(userName);
    }

    @RequestMapping(value = "followers/{username}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserProfileItem> getFollowers(@PathVariable("username") String userName) {
        return userStore.followerList(userName);
    }

    @RequestMapping(value = "change/{password}", method = RequestMethod.POST)
    @ResponseBody
    public void changePassword(@PathVariable("password") String password, HttpServletRequest request) {
        System.out.println(request.getAttribute("accesstoken"));
        try {
            loginStore.changePassword(password, authStore.getUserName((String) request.getAttribute("accesstoken")));
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    @RequestMapping(value = "{userName}", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedItem> jsonProfile(@PathVariable("userName") String userName, HttpServletRequest request) {
        return userStore.tweetList(userName, authStore.getUserId((String) request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "favorites/{username}", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getFavoriteTweetsOfAUser(@PathVariable("username") String userName) {
        return userStore.getFavoriteTweetsOfAUser(userName);
    }

    @RequestMapping(value = "retweets/{username}", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getReTweetsOfAUser(@PathVariable("username") String userName) {
        return userStore.getReTweetsOfAUser(userName);
    }

    @RequestMapping(value = "follow/{username}", method = RequestMethod.POST)
    @ResponseBody
    public void followUser(@PathVariable ("username") String userName,HttpServletRequest request) {
        userStore.followUser(userName, authStore.getUserId((String) request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "unfollow/{username}", method = RequestMethod.POST)
    @ResponseBody
    public void unFollowUser(@PathVariable("username") String userName,HttpServletRequest request) {
        userStore.unFollowUser(userName, authStore.getUserId((String) request.getAttribute("accesstoken")));
    }
}
