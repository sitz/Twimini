package com.directi.train.tweetapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 30/7/12
 * Time: 1:58 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class FollowController {
    @RequestMapping(value = "/user/following/{username}", method = RequestMethod.GET)
    public ModelAndView getFollowing(@PathVariable("username") String userName) {
        ModelAndView modelAndView = new ModelAndView("userlist");
        modelAndView.addObject("url","/user/following/" + userName + "/json");
        modelAndView.addObject("head","List of Following");
        return modelAndView;
    }

    @RequestMapping(value = "/user/followers/{username}", method = RequestMethod.GET)
    public ModelAndView getFollowers(@PathVariable("username") String userName) {
        ModelAndView modelAndView = new ModelAndView("userlist");
        modelAndView.addObject("url","/user/followers/" + userName + "/json");
        modelAndView.addObject("head","List of Followers");
        return modelAndView;
    }
    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
    public ModelAndView getSearchResults(@PathVariable("query") String query) {
        ModelAndView modelAndView = new ModelAndView("userlist");
        modelAndView.addObject("url","/search/" + query + "/json");
        modelAndView.addObject("head","Search Results");
        return modelAndView;
    }
}