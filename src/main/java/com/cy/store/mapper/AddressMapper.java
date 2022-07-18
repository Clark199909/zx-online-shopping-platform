package com.cy.store.mapper;

import com.cy.store.entity.Address;

import java.util.List;

public interface AddressMapper {
    Integer insert(Address address);

    Integer countByUid(Integer uid);

    List<Address> findByUid(Integer uid);
}
