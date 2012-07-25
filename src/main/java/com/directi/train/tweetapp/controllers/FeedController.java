package com.directi.train.tweetapp.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: sitesh
 * Date: 24/7/12
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */

import com.directi.train.tweetapp.model.FeedItem;
import com.directi.train.tweetapp.services.FeedStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/tweet")
public class FeedController {
    private final FeedStore feedStore;

    @Autowired
    public FeedController(FeedStore feedStore) {
        this.feedStore = feedStore;
    }

    @RequestMapping
    public ModelAndView feed() {
        return new ModelAndView();
    }

    @RequestMapping("new")
    @ResponseBody
    public FeedItem create(FeedItem feedItem) {
        return feedStore.add(feedItem);
    }

    @RequestMapping(value = "feed", method = RequestMethod.POST)
    @ResponseBody
    public List<FeedItem> feed_list() {
        return feedStore.feed();
    }

    @RequestMapping(value = "favorite/{tweetid}", method = RequestMethod.GET)
    @ResponseBody
    public void favoriteTweet(@PathVariable("tweetid") Long tweetId, HttpSession httpSession) {
        feedStore.favoriteTweet(tweetId, (Long) httpSession.getAttribute("userID"));
    }

    @RequestMapping(value = "unfavorite/{tweetid}", method = RequestMethod.GET)
    @ResponseBody
    public void unFavoriteTweet(@PathVariable("tweetid") Long tweetId, HttpSession httpSession) {
        feedStore.unFavoriteTweet(tweetId, (Long) httpSession.getAttribute("userID"));
    }

    @RequestMapping(value = "retweet/{tweetid}", method = RequestMethod.GET)
    @ResponseBody
    public void reTweet(@PathVariable("tweetid") Long tweetId, HttpSession httpSession) {
        feedStore.reTweet(tweetId, (Long) httpSession.getAttribute("userID"));
    }

    @RequestMapping(value = "unretweet/{tweetid}", method = RequestMethod.GET)
    @ResponseBody
    public void unReTweet(@PathVariable("tweetid") Long tweetId, HttpSession httpSession) {
        feedStore.unReTweet(tweetId, (Long) httpSession.getAttribute("userID"));
    }

    @RequestMapping(value = "favorites/{tweetid}", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getUsersWhoFavorited(@PathVariable("tweetid") Long tweetId) {
        return feedStore.favoritingUsers(tweetId);
    }

    @RequestMapping(value = "retweets/{tweetid}", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getUsersWhoReTweeted(@PathVariable("tweetid") Long tweetId) {
        return feedStore.retweetingUsers(tweetId);
    }

}