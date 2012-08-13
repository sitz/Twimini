package com.directi.train.tweetapp.controllers.WebApp;

/**
 * Created with IntelliJ IDEA.
 * User: sitesh
 * Date: 24/7/12
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */

import com.directi.train.tweetapp.controllers.WebApp.Helper.UserListModelAndView;
import com.directi.train.tweetapp.controllers.WebApp.Helper.UserListPageHelper;
import com.directi.train.tweetapp.services.AuthStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tweet")
public class FeedController {
    @Autowired private AuthStore authStore;

    @RequestMapping
    public ModelAndView feed(HttpServletRequest request) {
        String userName = (authStore.getUserName((String) request.getAttribute("accesstoken")));
        UserListModelAndView modelAndView = new UserListModelAndView();
        modelAndView.addUserData(userName);
        return modelAndView;
    }

    @RequestMapping(value = "favorites/{creatorId}/{tweetId}", method = RequestMethod.GET)
    public UserListModelAndView getUsersWhoFavorited(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId,HttpServletRequest request) {
        String userName = (String) request.getAttribute("curUserName");
        UserListModelAndView modelAndView = new UserListModelAndView();
        modelAndView.addUserData( userName);
        modelAndView.addPageData( "/api/status/favorites/" + creatorId + "/" + tweetId, "Users Who have Liked the tweet", "Likes");
        return  modelAndView;
    }

    @RequestMapping(value = "retweets/{creatorId}/{tweetId}", method = RequestMethod.GET)
    public UserListModelAndView getUsersWhoReTweeted(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId,HttpServletRequest request) {
        String userName = (String) request.getAttribute("curUserName");
        UserListModelAndView modelAndView = new UserListModelAndView();
        modelAndView.addUserData( userName);
        modelAndView.addPageData( "/api/status/retweets/" + creatorId + "/" + tweetId, "Users Who have Retweeted the tweet", "RTs");
        return  modelAndView;
    }
}