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
public class Goods extends LitePalSupport implements Serializable {
    private long id;
    private List<String> images = new ArrayList<>();
    private String title;
    private double price;
    private int stock;
    private String description;
    private long createTime;

    public Goods() {
    }

    public Goods( List<String> images, String title, double price,int stock, String description, long createTime) {

        this.images = images;
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public Goods setId(long id) {
        this.id = id;
        return this;
    }

    public List<String> getImages() {
        return images;
    }

    public Goods setImages(List<String> images) {
        this.images = images;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Goods setTitle(String title) {
        this.title = title;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Goods setPrice(double price) {
        this.price = price;
        return this;
    }
    public int getStock() {
        return stock;
    }

    public Goods setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Goods setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getCreateTime() {
        return createTime;
    }

    public Goods setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }
}
