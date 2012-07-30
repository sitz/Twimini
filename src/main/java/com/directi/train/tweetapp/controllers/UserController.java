package com.directi.train.tweetapp.controllers;

import com.directi.train.tweetapp.services.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class UserController {
    public final SimpleJdbcTemplate db;
    private final UserStore userStore;

    @Autowired
    public UserController(SimpleJdbcTemplate db,UserStore userStore) {this.db = db;this.userStore = userStore;}

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginForm() {
        return "index";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView registerForm() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestParam("username") String userName,
                                 @RequestParam("password") String password,
                                 @RequestParam("email") String email) {
        return userStore.registerUser(email,userName,password);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("username") String userName,
                              @RequestParam("password") String password, HttpSession session) {
        long userID;
        System.out.println(userName+password);
        try {
            userID = userStore.checkLogin(userName,password).getId();
            session.setAttribute("userName", userName);
            session.setAttribute("userID", userID);
        } catch (Exception e) {
            return "1";
        }
        return "0";
    }

    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}