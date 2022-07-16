package com.cy.store.mapper;

import com.cy.store.entity.User;


public interface UserMapper {
    Integer insert(User user);

    User findByUsername(String username);


}
