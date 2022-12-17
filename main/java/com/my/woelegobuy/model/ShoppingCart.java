package com.my.woelegobuy.model;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * @author wu.haitao ,Created on {DATE}
 * Major Function：<b></b>
 * @author mender，Modified Date Modify Content:
 */
public class ShoppingCart extends LitePalSupport implements Serializable {
    private long id;
    private long goodsId;
    private int count;

    private Goods goods;
    private String userId;
    private boolean isSelect;
    private long createTime;

    public long getId() {
        return id;
    }

    public ShoppingCart setId(long id) {
        this.id = id;
        return this;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public ShoppingCart setGoodsId(long goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public ShoppingCart setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public int getCount() {
        return count;
    }

    public ShoppingCart setCount(int count) {
        this.count = count;
        return this;
    }

    public long getCreateTime() {
        return createTime;
    }

    public ShoppingCart setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public ShoppingCart setSelect(boolean select) {
        isSelect = select;
        return this;
    }

    public Goods getGoods() {
        return goods;
    }

    public ShoppingCart setGoods(Goods goods) {
        this.goods = goods;
        return this;
    }
}
