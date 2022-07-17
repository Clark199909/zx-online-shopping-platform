package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        Object obj = request.getSession().getAttribute("uid");

        if(obj == null) { // not logged in, so redirect to the login page and end processing request
            response.sendRedirect("/web/login.html");
            // end requesting
            return false;
        }

        // let request proceed
        return true;
    }
}
