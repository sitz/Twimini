package com.directi.train.tweetapp.interceptor;

import com.directi.train.tweetapp.services.AuthStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: sitesh
 * Date: 14/8/12
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthAPIInterceptor extends HandlerInterceptorAdapter {
    @Autowired private AuthStore authStore;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {
        if (checkCookieOrAttribute(request)) {
            return true;
        }
        else {
            request.setAttribute("accesstoken", "-1");
        }
        return true;
    }

    public Boolean checkCookieOrAttribute(HttpServletRequest request) {
        String cookieName = "accesstoken";

        if (request.getParameter(cookieName) != null) {
            String accessToken = request.getParameter(cookieName);
            request.setAttribute(cookieName, accessToken);
            Boolean flag = authStore.isValid(accessToken);
            if (flag) {
                request.setAttribute("curUserName",authStore.getUserName(accessToken));
                return flag;
            }
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    String accessToken = cookie.getValue();
                    request.setAttribute(cookieName, accessToken);
                    Boolean flag = authStore.isValid(accessToken);
                    if (flag) {
                        request.setAttribute("curUserName",authStore.getUserName(accessToken));
                        return flag;
                    }
                }
            }
        }

        return false;
    }
}
