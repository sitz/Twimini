package com.directi.train.tweetapp.controllers.API;

import com.directi.train.tweetapp.model.FeedItem;
import com.directi.train.tweetapp.model.UserProfileItem;
import com.directi.train.tweetapp.services.AuthStore;
import com.directi.train.tweetapp.services.FeedStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 13/8/12
 * Time: 8:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/api/tweet")
public class TweetController {
    @Autowired private FeedStore feedStore;
    @Autowired private AuthStore authStore;

    @RequestMapping(value = "new", method = RequestMethod.POST)
    @ResponseBody
    public FeedItem create(FeedItem feedItem,HttpServletRequest request) {
        return feedStore.add(feedItem,authStore.getUserId((String) request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "retweets/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserProfileItem> getUsersWhoReTweeted(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId) {
        return feedStore.reTweetedUsers(creatorId, tweetId);
    }

    @RequestMapping(value = "favorites/{creatorId}/{tweetId}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserProfileItem> getUsersWhoLiked(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId) {
        return feedStore.favoritedUsers(creatorId, tweetId);
    }

    @RequestMapping(value = "favorite/{creatorId}/{tweetId}", method = RequestMethod.POST)
    @ResponseBody
    public boolean favoriteTweet(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId, HttpServletRequest request) {
        return feedStore.favoriteTweet(creatorId, tweetId, authStore.getUserId((String) request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "retweet/{creatorId}/{tweetId}", method = RequestMethod.POST)
    @ResponseBody
    public FeedItem reTweet(@PathVariable("creatorId") Long creatorId, @PathVariable("tweetId") Long tweetId, HttpServletRequest request) {
        return feedStore.reTweet(creatorId, tweetId, authStore.getUserId((String) request.getAttribute("accesstoken")));
    }

}
