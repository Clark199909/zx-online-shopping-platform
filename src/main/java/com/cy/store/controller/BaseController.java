package com.cy.store.controller;

import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

// base class for controllers
public class BaseController {
    public static final int OK = 200;

    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException) {
            result.setState(4000);
        } else if(e instanceof InsertException){
            result.setState(5000);
        } else if(e instanceof UserNotFoundException) {
            result.setState(5001);
        } else if(e instanceof PasswordNotMatchException){
            result.setState(5002);
        }
        result.setMessage(e.getMessage());
        return result;
    }

    protected final Integer getUidFromSession(HttpSession httpSession) {
        return Integer.valueOf(httpSession.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession httpSession) {
        return httpSession.getAttribute("username").toString();
    }
}
