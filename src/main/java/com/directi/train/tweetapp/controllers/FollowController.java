package com.directi.train.tweetapp.controllers;

import com.directi.train.tweetapp.services.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 30/7/12
 * Time: 1:58 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class FollowController {
    @Autowired private UserStore userStore;
    public void addUserData(ModelAndView modelAndView,String userName) {
        modelAndView.addObject("userName", userName);
        modelAndView.addObject("noTweets",userStore.noOfTweets(userName));
        modelAndView.addObject("noFollow",userStore.noOfFollowers(userName));
        modelAndView.addObject("noFollowing",userStore.noOfFollowing(userName));
        modelAndView.addObject("userProfileItem", userStore.getUserPofileItem(userName));
    }

    @RequestMapping(value = "/user/following/{username}", method = RequestMethod.GET)
    public ModelAndView getFollowing(@PathVariable("username") String userName) {
        ModelAndView modelAndView = new ModelAndView("userlist");
        modelAndView.addObject("url","/user/following/" + userName + "/json");
        modelAndView.addObject("head","List of Following");
        modelAndView.addObject("title","Following");
        addUserData(modelAndView,userName);
        return modelAndView;
    }

    @RequestMapping(value = "/user/followers/{username}", method = RequestMethod.GET)
    public ModelAndView getFollowers(@PathVariable("username") String userName) {
        ModelAndView modelAndView = new ModelAndView("userlist");
        modelAndView.addObject("url","/user/followers/" + userName + "/json");
        modelAndView.addObject("head","List of Followers");
        modelAndView.addObject("title","Followers");
        addUserData(modelAndView,userName);
        return modelAndView;
    }

    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
    public ModelAndView getSearchResults(@PathVariable("query") String query,HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("userlist");
        modelAndView.addObject("url","/search/" + query + "/json");
        modelAndView.addObject("head","Search Results");
        addUserData(modelAndView,(String) request.getAttribute("curUserName"));
        modelAndView.addObject("title","Search");
        return modelAndView;
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView search(HttpServletRequest request) {
        return getSearchResults("12312312312",request);
    }
}