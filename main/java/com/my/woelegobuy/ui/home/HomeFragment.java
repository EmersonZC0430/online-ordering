package com.my.woelegobuy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private TextView titleTextView;
    private Banner mBanner;
    private RecyclerView recyclerView;
    private ArrayList<Integer> images = new ArrayList<>();
    private List<Goods> data = new ArrayList<>();
    private BaseRecyclerAdapter<Goods> adapter;
    private User user;
    private ImageView rightButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        titleTextView = inflate.findViewById(R.id.titleTextView);
        titleTextView.setText("首页");
        initView(inflate);
        loadBanner();
//        initData();
        return inflate;
    }


    private void initView(View inflate) {
        titleTextView = inflate.findViewById(R.id.titleTextView);
        titleTextView.setText("首页");
        mBanner = (Banner) inflate.findViewById(R.id.mBanner);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rightButton = (ImageView) inflate.findViewById(R.id.rightButton);
        rightButton.setImageResource(R.mipmap.search);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(),SearchActivity.class));
        });
    }

    private void loadBanner() {
        images.clear();
        images.add(R.mipmap.one);
        images.add(R.mipmap.two);
        images.add(R.mipmap.three);
        images.add(R.mipmap.four);
        images.add(R.mipmap.five);
        mBanner.setAdapter(new BannerImageAdapter<Integer>(images) {

            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }
        });
        //开始轮播
        mBanner.start();
    }

    private void initData() {
        user = KVUtils.getLoginUser();
        List<Goods> all = LitePal.findAll(Goods.class);
        data.clear();
        if (all.size() > 0) {
            data.addAll(all);
        }
        Log.e("TAG", "initData: " + data.size());
        adapter = new BaseRecyclerAdapter<Goods>(getContext(), data, R.layout.item_goods) {
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
        Intent intent= new Intent(getActivity(),GoodsDescActivity.class);
        intent.putExtra("data",adapter.getData().get(pos));
        startActivity(intent);
    }

    private void addShoppingCar(Goods goods) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setGoods(goods);
        shoppingCart.setGoodsId(goods.getId());

        List<ShoppingCart> shoppingCarts = LitePal.where("goodsId = ?", goods.getId() + "").find(ShoppingCart.class);
        Log.e("TAG", "addShoppingCar: " + shoppingCarts.size() + "==" + user.getUserId());
        boolean isHave = false;
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getUserId().equals(user.getUserId()) && cart.getGoodsId() == goods.getId()) {
                isHave = true;
            }
        }
        shoppingCart.setUserId(user.getUserId());
        if (!isHave) {
            //购物车中没有此商品

            shoppingCart.setCount(1);
            shoppingCart.setCreateTime(TimeUtils.getCurrentTime());
            shoppingCart.save();
        } else {
            //购物车中有此商品
            shoppingCart.setCount(shoppingCarts.get(0).getCount() + 1);
            shoppingCart.update(shoppingCarts.get(0).getId());
        }
        Toast.makeText(getContext(), "添加到购物车成功", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new MessageEvent("add goods"));
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止轮播
        mBanner.stop();
    }
    @Override
    public void onResume() {
        super.onResume();
        //停止轮播
        initData();
    }
}