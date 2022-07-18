package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer max_count;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);
        if (count > max_count)
            throw new AddressCountLimitException("Number of addresses is larger than limit.");

        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);

        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());

        Integer rows = addressMapper.insert(address);
        if (rows != 1)
            throw new InsertException("Unknown exception occurs when inserting new address.");
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address a : list) {
            a.setProvinceCode(null);
            a.setCityCode(null);
            a.setAreaCode(null);
            a.setTel(null);
            a.setIsDefault(null);
            a.setCreatedTime(null);
            a.setCreatedUser(null);
            a.setModifiedTime(null);
            a.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null)
            throw new AddressNotFoundException("Address cannot be found.");

        if (!result.getUid().equals(uid))
            throw new AccessDeniedException("Illegal data access.");

        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        if (rows < 1)
            throw new UpdateException("Unknown exception in updating is_default value");

        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1)
            throw new UpdateException("Unknown exception in updating is_default value");
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null)
            throw new AddressNotFoundException("Address cannot be found.");

        if (!result.getUid().equals(uid))
            throw new AccessDeniedException("Illegal data access.");

        Integer rows1 = addressMapper.deleteByAid(aid);
        if (rows1 != 1) {
            throw new DeleteException("Unknown exception when deleting address.");
        }

        if (result.getIsDefault() == 0) {
            return;
        }

        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            return;
        }

        Address lastModified = addressMapper.findLastModified(uid);
        Integer lastModifiedAid = lastModified.getAid();
        Integer rows2 = addressMapper.updateDefaultByAid(lastModifiedAid, username, new Date());
        if (rows2 != 1)
            throw new UpdateException("Unknown exception when updating address.");
    }
}
