package com.cy.store.service;

public interface ICartService {

    void addToCart(Integer uid, Integer pid, Integer amount, String username);
}
