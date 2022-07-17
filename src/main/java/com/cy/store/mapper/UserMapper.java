package com.cy.store.mapper;

import com.cy.store.entity.User;

import java.util.Date;


public interface UserMapper {
    Integer insert(User user);

    User findByUsername(String username);

    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);

    User findByUid(Integer uid);

    Integer UpdateInfoByUid(User user);

    // can use @Param("UID") Integer uid
    // if needed to inject uid into UID, which
    // is used in SQL
    Integer updateAvatarByUid(Integer uid,
                              String avatar,
                              String modifiedUser,
                              Date modifiedTime);
}
