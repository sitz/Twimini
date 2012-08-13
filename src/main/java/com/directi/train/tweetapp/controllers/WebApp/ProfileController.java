package com.directi.train.tweetapp.controllers.WebApp;

import com.directi.train.tweetapp.model.FeedItem;
import com.directi.train.tweetapp.model.UserProfileItem;
import com.directi.train.tweetapp.services.AuthStore;
import com.directi.train.tweetapp.services.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class ProfileController {
    private final UserStore userStore;
    private final AuthStore authStore;

    @Autowired
    public ProfileController(UserStore userStore, AuthStore authStore) {
        this.userStore = userStore;
        this.authStore = authStore;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView curProfile(HttpServletRequest request) {
        return profile(authStore.getUserName((String) request.getAttribute("accesstoken")), request);
    }

    @RequestMapping(value = "{userName}", method = RequestMethod.GET)
    public ModelAndView profile(@PathVariable("userName") String userName, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("userName", userName);
        modelAndView.addObject("noTweets", userStore.noOfTweets(userName));
        modelAndView.addObject("noFollow", userStore.noOfFollowers(userName));
        modelAndView.addObject("noFollowing", userStore.noOfFollowing(userName));
        modelAndView.addObject("followStatus", userStore.checkFollowingStatus(authStore.getUserName((String) request.getAttribute("accesstoken")), userName));
        modelAndView.addObject("userProfileItem", userStore.getUserPofileItem(userName));
        return modelAndView;
    }

    @RequestMapping(value = "change/{password}", method = RequestMethod.POST)
    @ResponseBody
    public void changePassword(@PathVariable("password") String password, HttpServletRequest request) {
        System.out.println(request.getAttribute("accesstoken"));
        try {
            userStore.changePassword(password, authStore.getUserName((String) request.getAttribute("accesstoken")));
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}
