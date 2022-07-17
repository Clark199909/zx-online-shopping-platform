package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);

        if(result != null){
            throw new UsernameDuplicatedException("Username is already used.");
        }

        String oldPassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMD5Password(oldPassword, salt);
        user.setPassword(md5Password);
        user.setSalt(salt);

        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        Integer rows = userMapper.insert(user);

        if(rows != 1){
            throw new InsertException("Data insertion has unknown exception.");
        }
    }

    @Override
    public User login(String username, String password) {

        User result = userMapper.findByUsername(username);

        if(result == null)
            throw new UserNotFoundException("No user with this username exists");

        String salt = result.getSalt();
        String encryptedPassword = getMD5Password(password, salt);
        if(!encryptedPassword.equals(result.getPassword()))
            throw new PasswordNotMatchException("Incorrect password");

        if(result.getIsDelete() == 1)
            throw new UserNotFoundException("User data does not exist.");

        // smaller data can boost running efficiency
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return user;
    }

    @Override
    public void changePassword(Integer uid,
                               String username,
                               String oldPassword,
                               String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1)
            throw new UserNotFoundException("No user with this username exists");

        String md5OldPassword = getMD5Password(oldPassword, result.getSalt());
        if(!result.getPassword().equals(md5OldPassword))
            throw new PasswordNotMatchException("Incorrect password");

        String md5NewPassword = getMD5Password(newPassword, result.getSalt());

        Integer rows = userMapper.updatePasswordByUid(uid, md5NewPassword, username, new Date());

        if(rows != 1)
            throw new UpdateException("Exception occurs in updating password");
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1)
            throw new UserNotFoundException("User data does not exist.");

        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1)
            throw new UserNotFoundException("User data does not exist.");

        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.UpdateInfoByUid(user);

        if (rows != 1)
            throw new UpdateException("Error occurs when updating info.");
    }

    private String getMD5Password(String password, String salt) {
        for(int i=0; i<3; i++)
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        return password;
    }
}
