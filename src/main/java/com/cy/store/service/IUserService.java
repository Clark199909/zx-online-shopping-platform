package com.cy.store.service;

import com.cy.store.entity.User;

import javax.servlet.http.HttpSession;

public interface IUserService {
    void reg(User user);

    User login(String username, String password);

    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword);

    User getByUid(Integer uid);

    void changeInfo(Integer uid, String username, User user);
}
