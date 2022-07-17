package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController // controller + ResponseBody
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        JsonResult<Void> result = new JsonResult<>();
        result.setState(OK);
        result.setMessage("Sign-up succeeds");
        return result;
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession httpSession) {
        User data = userService.login(username, password);
        JsonResult<User> result = new JsonResult<>(OK, data);
        result.setMessage("Login succeeds.");

        httpSession.setAttribute("uid", data.getUid());
        httpSession.setAttribute("username", data.getUsername());

        return result;
    }

    @RequestMapping("change_password")
    public JsonResult<Void> updatePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession httpSession) {
        Integer uid = getUidFromSession(httpSession);
        String username = getUsernameFromSession(httpSession);
        userService.changePassword(uid, username, oldPassword, newPassword);

        JsonResult<Void> result = new JsonResult<>();
        result.setState(OK);
        result.setMessage("Password successfully changed.");
        return result;
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession httpSession) {
        User data = userService.getByUid(getUidFromSession(httpSession));
        JsonResult<User> result = new JsonResult<>(OK, data);
        result.setMessage("User successfully found with the given uid.");

        return result;
    }

    @RequestMapping("change_info")
    public JsonResult<Void> updateInfo(User user, HttpSession httpSession){
        userService.changeInfo(getUidFromSession(httpSession),
                getUsernameFromSession(httpSession), user);
        JsonResult<Void> result = new JsonResult<>();
        result.setState(OK);
        result.setMessage("Info successfully changed.");
        return result;
    }
}
