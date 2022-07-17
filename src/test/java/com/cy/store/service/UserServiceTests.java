package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private IUserService userService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("white");
            user.setPassword("123");

            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        User user = userService.login("test004", "123");
        System.out.println(user);
    }

    @Test
    public void changePassword() {
        userService.changePassword(9, "Anthony", "123", "321");
    }

    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(9));
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setUsername("Anthony");
        user.setPhone("11111111111");
        user.setEmail("2222@gmail.com");
        user.setGender(1);
        userService.changeInfo(9, "Anthony", user);
        System.out.println(user);
    }

    @Test
    public void changeAvatar(){
        userService.changeAvatar(9, "/update/haha.png", "admin");
    }

}