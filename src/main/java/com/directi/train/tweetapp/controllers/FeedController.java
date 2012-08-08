package com.directi.train.tweetapp.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: sitesh
 * Date: 24/7/12
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */

import com.directi.train.tweetapp.model.FeedItem;
import com.directi.train.tweetapp.services.AuthStore;
import com.directi.train.tweetapp.services.FeedStore;
import com.directi.train.tweetapp.services.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/tweet")
public class FeedController {
    private final FeedStore feedStore;
    private final UserStore userStore;

    @Autowired
    public FeedController(FeedStore feedStore,UserStore userStore) {
        this.feedStore = feedStore;
        this.userStore = userStore;
    }

    @RequestMapping
    public ModelAndView feed(HttpServletRequest request) {
        String userName = (AuthStore.getUserName(request.getAttribute("accesstoken")));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userName", userName);
        modelAndView.addObject("noTweets",userStore.noOfTweets(userName));
        modelAndView.addObject("noFollow",userStore.noOfFollowers(userName));
        modelAndView.addObject("noFollowing",userStore.noOfFollowing(userName));
        modelAndView.addObject("userProfileItem", userStore.getUserPofileItem(userName));
        return modelAndView;
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    @ResponseBody
    public FeedItem create(FeedItem feedItem,HttpServletRequest request) {
        return feedStore.add(feedItem,AuthStore.getUserId(request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "feed", method = RequestMethod.POST)
    @ResponseBody
    public List<FeedItem> feedList(HttpServletRequest request) {
        return feedStore.feed(AuthStore.getUserId(request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "feed/new/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedItem> newFeedsList(@PathVariable("id") Long feedId, HttpServletRequest request) {
        return feedStore.newFeedsList(feedId, AuthStore.getUserId(request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "feed/old/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedItem> oldFeedsList(@PathVariable("id") Long feedId, HttpServletRequest request) {
        return feedStore.oldFeedsList(feedId, AuthStore.getUserId(request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "favorite/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean favoriteTweet(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId, HttpServletRequest request) {
        return feedStore.favoriteTweet(creatorId, tweetId, AuthStore.getUserId(request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "unfavorite/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean unFavoriteTweet(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId, HttpServletRequest request) {
        return feedStore.unFavoriteTweet(creatorId, tweetId, AuthStore.getUserId(request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "retweet/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public FeedItem reTweet(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId, HttpServletRequest request) {
        return feedStore.reTweet(creatorId, tweetId, AuthStore.getUserId(request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "unretweet/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public void unReTweet(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId, HttpServletRequest request) {
        feedStore.unReTweet(creatorId, tweetId, AuthStore.getUserId(request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "favorites/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getUsersWhoFavorited(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId) {
        return feedStore.favoritedUsers(creatorId, tweetId);
    }

    @RequestMapping(value = "retweets/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getUsersWhoReTweeted(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId) {
        return feedStore.reTweetedUsers(creatorId, tweetId);
    }
}