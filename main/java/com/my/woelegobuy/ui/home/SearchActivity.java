package com.my.woelegobuy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.woelegobuy.R;
import com.my.woelegobuy.model.Goods;
import com.my.woelegobuy.model.ShoppingCart;
import com.my.woelegobuy.model.User;
import com.my.woelegobuy.utils.BaseRecyclerAdapter;
import com.my.woelegobuy.utils.BaseRecyclerHolder;
import com.my.woelegobuy.utils.KVUtils;
import com.my.woelegobuy.utils.MessageEvent;
import com.my.woelegobuy.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView titleTextView;
    private ImageView rightButton;
    private RecyclerView recyclerView;
    private EditText editTex;
    private List<Goods> data = new ArrayList<>();
    private List<Goods> searchData = new ArrayList<>();
    private BaseRecyclerAdapter<Goods> adapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
    }

    private void initView() {
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(view -> {
            finish();
        });
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText("搜索");
        rightButton = (ImageView) findViewById(R.id.rightButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editTex = (EditText) findViewById(R.id.editTex);
        editTex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                initSearch(editable.toString());
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void initSearch(String s) {
        searchData.clear();
        for (Goods datum : data) {
            if (datum.getTitle().contains(s)) {
                searchData.add(datum);
            }
        }
        adapter.addAll(searchData);
    }
    private void initData() {
        user = KVUtils.getLoginUser();
        List<Goods> all = LitePal.findAll(Goods.class);
        data.clear();
        if (all.size() > 0) {
            data.addAll(all);
        }
        adapter = new BaseRecyclerAdapter<Goods>(this, searchData, R.layout.item_goods) {
            @Override
            public void convert(BaseRecyclerHolder holder, Goods item, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.cover, item.getImages().get(0));
                holder.setText(R.id.goods_title, item.getTitle());
                holder.setText(R.id.price, item.getPrice() + "");
                holder.setText(R.id.stockEditText, "库存:"+item.getStock() + "");
                holder.getView(R.id.add_shopping_car_btn).setOnClickListener(view -> {
                    addShoppingCar(item);
                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((parent, view, position) -> {
            toGoodsDescAc(position);
        });
    }
    private void toGoodsDescAc(int pos) {
        Intent intent= new Intent(this,GoodsDescActivity.class);
        intent.putExtra("data",adapter.getData().get(pos));
        startActivity(intent);
    }

    private void addShoppingCar(Goods goods) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setGoods(goods);
        shoppingCart.setGoodsId(goods.getId());
        List<ShoppingCart> shoppingCarts = LitePal.where("goodsId = ?",
                goods.getId() + "").find(ShoppingCart.class);
        Log.e("TAG", "addShoppingCar: " + shoppingCarts.size() + "==" + user.getUserId());
        boolean isHave = false;
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getUserId().equals(user.getUserId()) && cart.getGoodsId() == goods.getId()) {
                isHave = true;
            }
        }
        shoppingCart.setUserId(user.getUserId());
        if (!isHave) {//购物车中没有此商品
            shoppingCart.setCount(1);
            shoppingCart.setCreateTime(TimeUtils.getCurrentTime());
            shoppingCart.save();
        } else {//购物车中有此商品
            shoppingCart.setCount(shoppingCarts.get(0).getCount() + 1);
            shoppingCart.update(shoppingCarts.get(0).getId());
        }
        Toast.makeText(this, "添加到购物车成功", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new MessageEvent("add goods"));
    }
}