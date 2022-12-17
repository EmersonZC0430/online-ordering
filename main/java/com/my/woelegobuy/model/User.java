package com.my.woelegobuy.model;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wu.haitao ,Created on {DATE}
 * Major Function：<b></b>
 * @author mender，Modified Date Modify Content:
 */
public class User extends LitePalSupport implements Serializable {
    private String userId;
    private String account;
    private String usersite;
    private String password;
    private String paymentPassword;
    private List<Address> address = new ArrayList<>();
    private long createTime;
    private long lastLoginTime = -1;

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public User setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public long getCreateTime() {
        return createTime;
    }

    public User setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public User setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getUsersite() {
        return usersite;
    }

    public User setUsersite(String usersite) {
        this.usersite = usersite;
        return this;
    }


    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPaymentPassword() {
        return paymentPassword;
    }

    public User setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
        return this;
    }

    public List<Address> getAddress() {
        return address;
    }

    public User setAddress(List<Address> address) {
        this.address = address;
        return this;
    }

    static class Address implements Serializable {
        private String name;
        private String phone;
        private String address;

        public String getName() {
            return name;
        }

        public Address setName(String name) {
            this.name = name;
            return this;
        }

        public String getPhone() {
            return phone;
        }

        public Address setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public String getAddress() {
            return address;
        }

        public Address setAddress(String address) {
            this.address = address;
            return this;
        }
    }
}
