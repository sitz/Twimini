package com.directi.train.tweetapp.controllers.WebApp;

import com.directi.train.tweetapp.controllers.WebApp.Helpers.UserListModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class FollowController {

    @RequestMapping(value = "following/{username}", method = RequestMethod.GET)
    public UserListModelAndView getFollowing(@PathVariable("username") String userName) {
        UserListModelAndView modelAndView = new UserListModelAndView();
        modelAndView.addUserData(userName);
        modelAndView.addPageData("/api/user/following/" + userName + "/json", "List of Following", "Following");
        return modelAndView;
    }

    @RequestMapping(value = "followers/{username}", method = RequestMethod.GET)
    public UserListModelAndView getFollowers(@PathVariable("username") String userName) {
        UserListModelAndView modelAndView = new UserListModelAndView();
        modelAndView.addUserData(userName);
        modelAndView.addPageData("/api/user/followers/" + userName, "List of Followers", "Followers");
        return modelAndView;
    }

}