package com.directi.train.tweetapp.controllers;

import com.directi.train.tweetapp.model.UserProfileItem;
import com.directi.train.tweetapp.services.AuthStore;
import com.directi.train.tweetapp.services.SearchStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 27/7/12
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SearchController {
    private final SearchStore searchStore;
    private final AuthStore authStore;

    @Autowired
    public SearchController(SearchStore searchStore, AuthStore authStore) {
        this.searchStore = searchStore;
        this.authStore = authStore;
    }

    @RequestMapping(value = "search/{query}/json", method = RequestMethod.GET)
    @ResponseBody
    public List<UserProfileItem> favoriteTweet(@PathVariable("query") String query, HttpServletRequest request) {
        return searchStore.getResults(query, authStore.getUserId((String) request.getAttribute("accesstoken")));
    }



}