package com.directi.train.tweetapp.controllers;

import com.directi.train.tweetapp.model.TweetItem;
import com.directi.train.tweetapp.services.TweetStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TweetController {
    private final TweetStore tweetStore;

    @Autowired
    public TweetController(TweetStore tweetStore) {
        this.tweetStore = tweetStore;
    }

    @RequestMapping
    public ModelAndView feed() {
        return new ModelAndView();
    }

    @RequestMapping("new")
    @ResponseBody
    public TweetItem create(TweetItem tweetItem, HttpSession httpSession) {
        return tweetStore.add(tweetItem, (Long) httpSession.getAttribute("userID"));
    }

    @RequestMapping(value = "feed", method = RequestMethod.POST)
    @ResponseBody
    public List<TweetItem> feed_list(HttpSession httpSession) {
        return tweetStore.feed((Long) httpSession.getAttribute("userID"));
    }

    @RequestMapping(value = "favorite/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getUsersWhoFavorited(@PathVariable("tweetId") Integer tweetId) {
        return tweetStore.favoritingUsers(tweetId);
    }

    @RequestMapping(value = "retweet/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getUsersWhoReTweeted(@PathVariable("tweetId") Integer tweetId) {
        return tweetStore.retweetingUsers(tweetId);
    }

}