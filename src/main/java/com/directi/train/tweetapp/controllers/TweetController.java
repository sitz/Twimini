package com.directi.train.tweetapp.controllers;

import com.directi.train.tweetapp.model.TweetItem;
import com.directi.train.tweetapp.model.UserItem;
import com.directi.train.tweetapp.services.TweetStore;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tweet")
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
    public TweetItem create(TweetItem tweetItem) {
        return tweetStore.add(tweetItem);
    }

    @RequestMapping(value = "feed", method = RequestMethod.POST)
    @ResponseBody
    public List<TweetItem> feed_list() {
        return tweetStore.feed();
    }

    @RequestMapping(value = "favorite/{tweetid}", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getUsersWhoFavorited(@PathVariable("tweetid") Integer tweetId) {
        return tweetStore.favoriting_users(tweetId);
    }

    @RequestMapping(value = "retweet/{tweetid}", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getUsersWhoReTweeted(@PathVariable("tweetid") Integer tweetId) {
        return tweetStore.retweeting_users(tweetId);
    }

}