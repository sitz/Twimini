package com.directi.train.tweetapp.controllers;

import com.directi.train.tweetapp.model.TweetItem;
import com.directi.train.tweetapp.services.TweetStore;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tweet")
public class TweeetController {
    private final TweetStore tweetStore;

    @Autowired
    public TweeetController(TweetStore tweetStore) {
        this.tweetStore = tweetStore;
    }

    @RequestMapping
    public ModelAndView tweet () {
        return new ModelAndView() {{
            addObject("tweets", tweetStore.list());
        }};
    }

    @RequestMapping("profile")
    @ResponseBody
    public List<TweetItem> profile() {
        return tweetStore.profile();
    }

    @RequestMapping("new")
    @ResponseBody
    public TweetItem create(TweetItem tweetItem) {
        return tweetStore.add(tweetItem);
    }

}