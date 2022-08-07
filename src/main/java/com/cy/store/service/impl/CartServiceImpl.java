package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.IProductService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private IProductService productService;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {

        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date now = new Date();

        if (result == null) {

            Cart cart = new Cart();

            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);

            Product product = productService.findById(pid);

            cart.setPrice(product.getPrice());

            cart.setCreatedUser(username);
            cart.setCreatedTime(now);
            cart.setModifiedUser(username);
            cart.setModifiedTime(now);

            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("Unknown error occurs when inserting product to cart");
            }
        } else {
            Integer cid = result.getCid();
            Integer num = result.getNum() + amount;

            Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
            if (rows != 1) {
                throw new InsertException("Unknown error occurs when inserting product to cart");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {

        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("Cart does not exist!");
        }

        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("Illegal Access");
        }

        Integer num = result.getNum() + 1;
        Date now = new Date();

        Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
        if (rows != 1) {
            throw new InsertException("Unknown exception occurs when modifying cart");
        }
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);

        list.removeIf(cart -> !cart.getUid().equals(uid));
        return list;
    }
}
