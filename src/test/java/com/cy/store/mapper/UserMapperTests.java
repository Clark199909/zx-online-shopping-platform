package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("kev");
        user.setPassword("123");

        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(10, "321", "admin", new Date());
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(10));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(9);
        user.setPhone("11111111111");
        user.setEmail("1111@gmail.com");
        user.setGender(1);
        user.setModifiedUser("admin");
        user.setModifiedTime(new Date());
        Integer rows = userMapper.UpdateInfoByUid(user);
        System.out.println("rows=" + rows);
    }
}
