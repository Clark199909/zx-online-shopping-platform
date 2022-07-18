package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("address")
@RestController
public class AddressController extends BaseController {
    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession httpSession) {
        Integer uid = getUidFromSession(httpSession);
        String username = getUsernameFromSession(httpSession);
        addressService.addNewAddress(uid, username, address);
        JsonResult<Void> result = new JsonResult<>();
        result.setState(OK);
        result.setMessage("New address successfully added!");
        return result;
    }

    @RequestMapping({"/",""})
    public JsonResult<List<Address>> getByUid(HttpSession httpSession) {
        Integer uid = getUidFromSession(httpSession);
        List<Address> data = addressService.getByUid(uid);
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.setDefault(aid, uid, username);

        JsonResult<Void> result = new JsonResult<Void>();
        result.setState(OK);
        result.setMessage("Address sets as default successfully!");
        return result;
    }

    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.delete(aid, uid, username);

        JsonResult<Void> result = new JsonResult<>();
        result.setState(OK);
        result.setMessage("Address successfully deleted!");
        return result;
    }
}
