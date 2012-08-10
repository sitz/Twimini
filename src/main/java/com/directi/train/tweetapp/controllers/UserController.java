package com.directi.train.tweetapp.controllers;

import com.directi.train.tweetapp.services.AuthStore;
import com.directi.train.tweetapp.services.RandomStore;
import com.directi.train.tweetapp.services.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class UserController {
    private final UserStore userStore;
    private final AuthStore authStore;
    private final String cookieName = "accesstoken";

    @Autowired
    public UserController(UserStore userStore, AuthStore authStore) {
        this.userStore = userStore;
        this.authStore = authStore;
    }

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
                              @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {

        if (request.getAttribute(cookieName) != null) {
            return (String) request.getAttribute(cookieName);
        }

        long userID;
        String accessToken =  RandomStore.getAccessToken();
        try {
            userID = userStore.checkLogin(userName,password).getId();
            authStore.insert(userName, userID, accessToken);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error1";
        }

        Cookie cookie = new Cookie(cookieName, accessToken);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
        return accessToken;
    }

    @RequestMapping(value = "forgot/{userName}", method = RequestMethod.POST)
    @ResponseBody
    public void forgotPassword(@PathVariable("userName") String userName) {
        userStore.forgotPassword(userName);
    }

    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request) {
         Cookie cookies[] = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            authStore.remove(cookie.getValue());
        }

        return "redirect:/";
    }
}