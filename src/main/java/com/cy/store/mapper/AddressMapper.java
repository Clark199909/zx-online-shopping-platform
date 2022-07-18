package com.cy.store.mapper;

import com.cy.store.entity.Address;

import java.util.Date;
import java.util.List;

public interface AddressMapper {
    Integer insert(Address address);

    Integer countByUid(Integer uid);

    List<Address> findByUid(Integer uid);

    Address findByAid(Integer aid);

    Integer updateNonDefaultByUid(Integer uid);

    Integer updateDefaultByAid(Integer aid,
                               String modifiedUser,
                               Date modifiedTime);
}
