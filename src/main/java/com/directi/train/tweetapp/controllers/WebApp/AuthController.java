package com.directi.train.tweetapp.controllers.WebApp;

import com.directi.train.tweetapp.controllers.API.ApiAuthController;
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
public class AuthController {
    @Autowired ApiAuthController auth;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginForm() {
        return "index";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView registerForm() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        auth.logout(request);
        return "redirect:/";
    }
}