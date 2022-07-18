package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(9);
        address.setPhone("123456788");
        address.setName("Carmelo");

        Integer rows = addressMapper.insert(address);
        System.out.println(rows);
    }

    @Test
    public void countByUid() {
        System.out.println(addressMapper.countByUid(9));
    }

    @Test
    public void findByUid() {
        System.err.println(addressMapper.findByUid(9));
    }

    @Test
    public void updateNonDefaultByUid() {
        Integer uid = 9;
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        System.out.println("rows=" + rows);
    }
    @Test
    public void updateDefaultByAid() {
        Integer aid = 4;
        String modifiedUser = "admin";
        Date modifiedTime = new Date();
        Integer rows = addressMapper.updateDefaultByAid(aid, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }
    @Test
    public void findByAid() {
        Integer aid = 4;
        Address result = addressMapper.findByAid(aid);
        System.out.println(result);
    }

}
