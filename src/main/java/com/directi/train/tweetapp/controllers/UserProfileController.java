package com.directi.train.tweetapp.controllers;

import com.directi.train.tweetapp.model.FeedItem;
import com.directi.train.tweetapp.model.UserProfileItem;
import com.directi.train.tweetapp.services.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserProfileController {
    public final SimpleJdbcTemplate db;
    private final UserStore userStore;

    @Autowired
    public UserProfileController(SimpleJdbcTemplate db,UserStore userStore) {this.db = db;this.userStore = userStore;}

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView curProfile(HttpSession session ) {
        return profile((String)session.getAttribute("userName"),session);
    }

    @RequestMapping(value = "{userName}", method = RequestMethod.GET)
    public ModelAndView profile(@PathVariable("userName") String userName, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("userName", userName);
        modelAndView.addObject("noTweets", userStore.noOfTweets(userName));
        modelAndView.addObject("noFollow", userStore.noOfFollowers(userName));
        modelAndView.addObject("noFollowing", userStore.noOfFollowing(userName));
        modelAndView.addObject("followStatus", userStore.checkFollowingStatus((String) session.getAttribute("userName"), userName));
        return modelAndView;
    }

    @RequestMapping(value = "{userName}/json", method = RequestMethod.POST)
    @ResponseBody
    public List<FeedItem> jsonProfile(@PathVariable("userName") String userName) {
        return userStore.tweetList(userName);
    }

    @RequestMapping(value = "following/{username}/json", method = RequestMethod.GET)
    @ResponseBody
    public List<UserProfileItem> getFollowing(@PathVariable("username") String userName) {
        return userStore.followingList(userName);
    }

    @RequestMapping(value = "followers/{username}/json", method = RequestMethod.GET)
    @ResponseBody
    public List<UserProfileItem> getFollowers(@PathVariable("username") String userName) {
        return userStore.followerList(userName);
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

    @RequestMapping(value = "follow/{username}", method = RequestMethod.GET)
    @ResponseBody
    public int followUser(@PathVariable ("username") String userName,HttpSession session) {
        return userStore.followUser(userName, (Long) session.getAttribute("userID"));
    }

    @RequestMapping(value = "unfollow/{username}", method = RequestMethod.GET)
    @ResponseBody
    public int unFollowUser(@PathVariable("username") String userName,HttpSession session) {
        return userStore.unFollowUser(userName, (Long) session.getAttribute("userID"));
    }

}
