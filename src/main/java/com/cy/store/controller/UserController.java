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
    public JsonResult<User> login(String username, String password) {
        User data = userService.login(username, password);
        JsonResult<User> result = new JsonResult<>(OK, data);
        result.setMessage("Login succeeds.");
        return result;
    }
}
