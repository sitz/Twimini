package com.directi.train.tweetapp.controllers.API;

import com.directi.train.tweetapp.controllers.WebApp.AuthController;
import com.directi.train.tweetapp.model.FeedItem;
import com.directi.train.tweetapp.services.AuthStore;
import com.directi.train.tweetapp.services.FeedStore;
import com.directi.train.tweetapp.services.UserStore;
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
 * Time: 10:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/api/feed")
public class UserFeedController {
    @Autowired private FeedStore feedStore;
    @Autowired private UserStore userStore;
    @Autowired private AuthStore authStore;

    @RequestMapping(value = "feed/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedItem> feedList(@PathVariable("userId") String userName) {
        return feedStore.feed(userStore.getUserId(userName));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedItem> feedList(HttpServletRequest request) {
        return feedList(authStore.getUserName((String) request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "new/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedItem> newFeedsList(@PathVariable("id") Long feedId, HttpServletRequest request) {
        return feedStore.newFeedsList(feedId, authStore.getUserId((String) request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "old/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedItem> oldFeedsList(@PathVariable("id") Long feedId, HttpServletRequest request) {
        return feedStore.oldFeedsList(feedId, authStore.getUserId((String) request.getAttribute("accesstoken")));
    }

    @RequestMapping(value = "{username}/old/{id}/", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedItem> oldFeedsList(@PathVariable("username") String userName,@PathVariable("id") Long feedId) {
        return feedStore.oldFeedsList(feedId,userStore.getUserId(userName));
    }

    @RequestMapping(value = "{username}/new/{id}/", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedItem> newFeedsList(@PathVariable("username") String userName,@PathVariable("id") Long feedId) {
        return feedStore.newFeedsList(feedId,userStore.getUserId(userName));
    }

}
