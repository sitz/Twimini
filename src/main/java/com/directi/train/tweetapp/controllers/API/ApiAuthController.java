package com.directi.train.tweetapp.controllers.API;

import com.directi.train.tweetapp.services.AuthStore;
import com.directi.train.tweetapp.services.Auxillary.RandomStore;
import com.directi.train.tweetapp.services.LoginStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 13/8/12
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/api/auth")
public class ApiAuthController {
    @Autowired private AuthStore authStore;
    @Autowired private LoginStore loginStore;
    @Autowired private RandomStore randomStore;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestParam("username") String userName, @RequestParam("password") String password, @RequestParam("email") String email) {
        return loginStore.registerUser(email,userName,password);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("username") String userName, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
        String cookieName = "accesstoken";
        if (request.getAttribute(cookieName) != null) {
            return (String) request.getAttribute(cookieName);
        }

        long userID;
        String accessToken =  randomStore.getAccessToken();
        try {
            userID = loginStore.checkLogin(userName,password).getId();
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

    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @ResponseBody
    public void logout(HttpServletRequest request) {
        Cookie cookies[] = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            authStore.remove(cookie.getValue());
        }
    }

    @RequestMapping(value = "forgot/{userName}", method = RequestMethod.POST)
    @ResponseBody
    public void forgotPassword(@PathVariable("userName") String userName) {
        loginStore.forgotPassword(userName);
    }
}
