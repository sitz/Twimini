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

    @RequestMapping(value = "new", method = RequestMethod.POST)
    @ResponseBody
    public FeedItem create(FeedItem feedItem,HttpSession session) {
        return feedStore.add(feedItem,(Long)session.getAttribute("userID"));
    }

    @RequestMapping(value = "feed", method = RequestMethod.POST)
    @ResponseBody
    public List<FeedItem> feed_list() {
        return feedStore.feed();
    }

    @RequestMapping(value = "favorite/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean favoriteTweet(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId, HttpSession httpSession) {
        return feedStore.favoriteTweet(creatorId, tweetId, (Long) httpSession.getAttribute("userID"));
    }

    @RequestMapping(value = "unfavorite/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean unFavoriteTweet(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId, HttpSession httpSession) {
        return feedStore.unFavoriteTweet(creatorId, tweetId, (Long) httpSession.getAttribute("userID"));
    }

    @RequestMapping(value = "retweet/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public FeedItem reTweet(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId, HttpSession httpSession) {
        return feedStore.reTweet(creatorId, tweetId, (Long) httpSession.getAttribute("userID"));
    }

    @RequestMapping(value = "unretweet/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public void unReTweet(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId, HttpSession httpSession) {
        feedStore.unReTweet(creatorId, tweetId, (Long) httpSession.getAttribute("userID"));
    }

    @RequestMapping(value = "favorites/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getUsersWhoFavorited(@PathVariable("tweetId") Long tweetId) {
        return feedStore.favoritedUsers(tweetId);
    }

    @RequestMapping(value = "retweets/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getUsersWhoReTweeted(@PathVariable("tweetId") Long tweetId) {
        return feedStore.reTweetedUsers(tweetId);
    }
}