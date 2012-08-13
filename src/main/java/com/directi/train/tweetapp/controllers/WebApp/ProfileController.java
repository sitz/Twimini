package com.directi.train.tweetapp.controllers.WebApp;

import com.directi.train.tweetapp.controllers.WebApp.Helpers.UserListModelAndView;
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
    @Autowired private UserStore userStore;
    @Autowired private AuthStore authStore;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView curProfile(HttpServletRequest request) {
        return profile(authStore.getUserName((String) request.getAttribute("accesstoken")), request);
    }

    @RequestMapping(value = "{userName}", method = RequestMethod.GET)
    public ModelAndView profile(@PathVariable("userName") String userName, HttpServletRequest request) {
        UserListModelAndView modelAndView = new UserListModelAndView("user");
        modelAndView.addObject("userProfileItem", userStore.getUserPofileItem(userName));
        Integer i =userStore.checkFollowingStatus(authStore.getUserName((String) request.getAttribute("accesstoken")),userName);
        Boolean followStatus;
        if(i==0) followStatus = Boolean.FALSE;
        else followStatus = Boolean.TRUE;
        modelAndView.addObject("followStatus", followStatus);
        modelAndView.addUserData(userName,userStore);
        return modelAndView;
    }
}
