package com.directi.train.tweetapp.interceptor;

import com.directi.train.tweetapp.services.AuthStore;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    public AuthInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String cookieName = "accesstoken";
        if (request.getAttribute(cookieName) != null) {
            String accessToken = (String) request.getParameter(cookieName);
            request.setAttribute(cookieName, accessToken);

            Boolean flag = AuthStore.isValid(accessToken);
            if (flag) return flag;
        }

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                String accessToken = cookie.getValue();
                request.setAttribute(cookieName, accessToken);

                Boolean flag = AuthStore.isValid(accessToken);
                if (flag) return flag;
            }
        }

        response.sendRedirect("/");
        return false;
    }
}