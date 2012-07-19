package com.directi.train.tweetapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Map;

@Controller
public class UserController {
    public final SimpleJdbcTemplate db;

    @Autowired
    public UserController(SimpleJdbcTemplate db) {this.db = db;}

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
                              @RequestParam("email") String email,
                              HttpSession session) {

        ModelAndView mv = new ModelAndView("/index");
        long userID;
        try {
            Map<String, Object> userData = db.queryForMap("select email,username from users where email='"+ email + "' or username='"+ userName +"'");
            if(userData.get("username").equals(userName)) {
                mv.addObject("message", "UserName Already exists.");
                return mv;
            }
            if(userData.get("email").equals(email)) {
                mv.addObject("message", "Email Already exists.");
                return mv;
            }
        } catch (EmptyResultDataAccessException e) {
            db.update("insert into users (email, username, password) values(?, ?, ?)",email, userName, password);
            mv.addObject("message", "Email registered.");
        }
        return mv;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("username") String userName, @RequestParam("password") String password, HttpSession session) {
        ModelAndView mv = new ModelAndView("/index");
        long userID;
        try {
            Map<String, Object> userData = db.queryForMap("select id, username, password  from users where username='"+ userName +"'");
            if (!userData.get("password").equals(password)) {
                mv.addObject("message", "Invalid password.");
                return mv;
            }
            userID = (Integer) userData.get("id");
        } catch (EmptyResultDataAccessException e) {
            mv.addObject("message", "User Does not register. Please register");
            return mv;
        }
        session.setAttribute("userName", userName);
        session.setAttribute("userID", userID);
        System.out.println(userID);
        mv.setViewName("redirect:/tweet");
        return mv;
    }

    @RequestMapping(value = "/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/user/following", method = RequestMethod.GET)
    public ModelAndView getFollowing(@RequestParam("username") String userName) {
        System.out.println(userName);
        ModelAndView mv = new ModelAndView("following");
        int userId = db.queryForInt("select id from users where username='"+ userName+ "'");
        List<Map<String, Object>> followingList = db.queryForList(String.format("select following_id from following where user_id='%d'", userId));
        mv.addObject(followingList);

        return mv;
    }

    @RequestMapping(value = "/user/followers", method = RequestMethod.GET)
    public ModelAndView getFollowers(@RequestParam("username") String userName) {
        System.out.println(userName);
        ModelAndView mv = new ModelAndView("followers");
        int userId = db.queryForInt("select id from users where username='"+userName+"'", userName);
        List<Map<String, Object>> followersList = db.queryForList(String.format("select follower_id from followers where user_id='%d'", userId));
        mv.addObject(followersList);

        return  mv;
    }
}