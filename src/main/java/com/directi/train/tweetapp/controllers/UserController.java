package com.directi.train.tweetapp.controllers;

import com.directi.train.tweetapp.services.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    public final SimpleJdbcTemplate db;
    private final UserStore userStore;

    @Autowired
    public UserController(SimpleJdbcTemplate db,UserStore userStore) {this.db = db;this.userStore = userStore;}

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String loginForm() {
        return "index";
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public ModelAndView registerForm() {
        return new ModelAndView("register");
    }
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam("username") String userName,
                                 @RequestParam("password") String password,
                                 @RequestParam("email") String email, HttpSession session) {
        ModelAndView mv = new ModelAndView("/index");
        mv.addObject("message",userStore.registerUser(email,userName,password));
        return mv;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("username") String userName,
                              @RequestParam("password") String password, HttpSession session) {
        ModelAndView mv = new ModelAndView("/index");
        long userID;
        System.out.println(userName+password);
        try {
            userID = userStore.checkLogin(userName,password).getId();
            System.out.println(userID);
            session.setAttribute("userName", userName);
            session.setAttribute("userID", userID);
        } catch (Exception e) {
            System.out.println(e);
            mv.addObject("message",e.getMessage());
        }
        mv.setViewName("redirect:/tweet");
        return mv;
    }

    @RequestMapping(value = "/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/user/following/{username}", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getFollowing(@PathVariable("username") String userName) {
        return userStore.following_list(userName);
    }

    @RequestMapping(value = "/user/followers/{username}", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getFollowers(@PathVariable("username") String userName) {
        return userStore.follower_list(userName);
    }

    @RequestMapping(value = "/user/follow/{username}", method = RequestMethod.GET)
    @ResponseBody
    public void followUser(@PathVariable ("username") String userName) {
        userStore.follow_user(userName);
    }

    @RequestMapping(value = "/user/unfollow/{username}", method = RequestMethod.GET)
    @ResponseBody
    public void unfollowUser(@PathVariable ("username") String userName) {
        userStore.unfollow_user(userName);
    }
}