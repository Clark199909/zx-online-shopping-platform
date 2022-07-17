package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

// base class for controllers
public class BaseController {
    public static final int OK = 200;

    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException) {
            result.setState(4000);
        } else if(e instanceof UserNotFoundException) {
            result.setState(4001);
        } else if(e instanceof PasswordNotMatchException){
            result.setState(4002);
        } else if(e instanceof AddressCountLimitException){
            result.setState(4003);
        } else if(e instanceof InsertException){
            result.setState(5000);
        } else if(e instanceof UpdateException){
            result.setState(5001);
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
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
