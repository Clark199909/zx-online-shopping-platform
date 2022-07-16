package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.PasswordNotMatchException;
import com.cy.store.service.ex.UserNotFoundException;
import com.cy.store.service.ex.UsernameDuplicatedException;
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

    private String getMD5Password(String password, String salt) {
        for(int i=0; i<3; i++)
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        return password;
    }
}
