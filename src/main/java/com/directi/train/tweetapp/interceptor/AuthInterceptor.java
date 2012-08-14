package com.directi.train.tweetapp.interceptor;

import com.directi.train.tweetapp.services.AuthStore;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthInterceptor extends AuthAPIInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean status = checkCookieOrAttribute(request);
        if (status) {
            return status;
        }

        response.sendRedirect("/auth/login");
        return false;
    }
}