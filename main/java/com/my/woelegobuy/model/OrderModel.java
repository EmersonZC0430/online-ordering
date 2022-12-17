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
public class OrderModel extends LitePalSupport implements Serializable {
    private String orderId;
    private String userId;

    private String usersite;
    private String site;
    private int count;
    private double price;
    private long createTime;
    private List<String> images = new ArrayList<>();
    private String title;
    private String account;


    public List<String> getImages() {
        return images;
    }

    public OrderModel setImages(List<String> images) {
        this.images = images;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public OrderModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public OrderModel setAccount(String account) {
        this.account = account;
        return this;
    }
    public String getUsersite() {
        return usersite;
    }

    public OrderModel setUsersite(String usersite) {
        this.usersite = usersite;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderModel setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public OrderModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public int getCount() {
        return count;
    }

    public OrderModel setCount(int count) {
        this.count = count;
        return this;
    }

    public String getSite() {
        return site;
    }

    public OrderModel setSite(String site) {
        this.site = site;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public OrderModel setPrice(double price) {
        this.price = price;
        return this;
    }

    public long getCreateTime() {
        return createTime;
    }

    public OrderModel setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

}
