package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


}
