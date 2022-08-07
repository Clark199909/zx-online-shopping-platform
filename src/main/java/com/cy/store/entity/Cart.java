package com.cy.store.entity;

import java.io.Serializable;
import java.util.Objects;

public class Cart extends BaseEntity implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;

    @Override
    public String toString() {
        return "Cart{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", price=" + price +
                ", num=" + num +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(cid, cart.cid) && Objects.equals(uid, cart.uid) && Objects.equals(pid, cart.pid) && Objects.equals(price, cart.price) && Objects.equals(num, cart.num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cid, uid, pid, price, num);
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getCid() {
        return cid;
    }

    public Integer getUid() {
        return uid;
    }

    public Integer getPid() {
        return pid;
    }

    public Long getPrice() {
        return price;
    }

    public Integer getNum() {
        return num;
    }
}
