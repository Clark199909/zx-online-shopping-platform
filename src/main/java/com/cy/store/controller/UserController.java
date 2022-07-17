package com.cy.store.controller;

import com.cy.store.controller.ex.*;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

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

    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    public static final Set<String> AVATAR_TYPE = new HashSet<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    @RequestMapping("change_avatar")
    // name inconsistency: use @RequestParam("file") MultipartFile file
    public JsonResult<String> changeAvatar(
            HttpSession httpSession,
            MultipartFile file) {
        if(file.isEmpty())
            throw new FileEmptyException("File is empty");
        if(file.getSize() > AVATAR_MAX_SIZE)
            throw new FileSizeException("File size is too large.");
        if(!AVATAR_TYPE.contains(file.getContentType()))
            throw new FileTypeException("File type is not supported.");

        String parent =
                httpSession.getServletContext().
                        getRealPath("upload");
        File dir = new File(parent);
        if(!dir.exists()) dir.mkdirs();

        // create an empty file of random file name with the same file type
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename =
                UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename); // empty file

        try {
            file.transferTo(dest); // write data in file to dest
        } catch (FileStateException e){
            throw new FileStateException("File state exception.");
        } catch (IOException e) {
            throw new FileUploadIOException("File I/O exception.");
        }

        String avatar = "/upload/" + filename;
        userService.changeAvatar(
                getUidFromSession(httpSession),
                avatar,
                getUsernameFromSession(httpSession));

        JsonResult<String> result = new JsonResult<>(OK, avatar);
        result.setMessage("Avatar successfully changed.");
        return result;
    }
}
