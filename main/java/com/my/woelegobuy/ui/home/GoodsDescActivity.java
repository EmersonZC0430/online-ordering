package com.my.woelegobuy.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.my.woelegobuy.R;
import com.my.woelegobuy.model.Goods;
import com.my.woelegobuy.model.ShoppingCart;
import com.my.woelegobuy.model.User;
import com.my.woelegobuy.utils.KVUtils;
import com.my.woelegobuy.utils.MessageEvent;
import com.my.woelegobuy.utils.TimeUtils;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.io.File;
import java.util.List;

public class GoodsDescActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView titleTextView;
    private ImageView rightButton;
    private Banner mBanner;
    private Goods data;
    private TextView title;
    private TextView price;
    private TextView stock;
    private TextView desc;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_desc);
        initView();
        initData();
    }

    private void initData() {
        user = KVUtils.getLoginUser();
        data = ((Goods) getIntent().getSerializableExtra("data"));
        mBanner.setAdapter(new BannerImageAdapter<String>(data.getImages()) {

            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data)
                        .centerCrop()
                        .placeholder(R.mipmap.load_image)
                        .error(R.mipmap.load_image_shibai)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
                holder.imageView.setOnClickListener(view -> {
                    new XPopup.Builder(GoodsDescActivity.this)
                            .asImageViewer(holder.imageView, data, new ImageLoader())
                            .show();
                });
            }
        });
        //开始轮播
        mBanner.start();
        title.setText(data.getTitle());
        price.setText("￥"+data.getPrice());
        stock.setText("库存:"+data.getStock());
        desc.setText("商品详情:\n"+ data.getDescription());
    }
    private void initView() {
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(view -> {
            finish();
        });
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText("商品详情");
        rightButton = (ImageView) findViewById(R.id.rightButton);
        mBanner = (Banner) findViewById(R.id.mBanner);
        stock = (TextView) findViewById(R.id.stockEditText);
        title = (TextView) findViewById(R.id.title);
        price = (TextView) findViewById(R.id.price);
        desc = (TextView) findViewById(R.id.desc);
        ImageButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addShoppingCar(data);

            }
        });
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
        Toast.makeText(this, "添加到购物车成功", Toast.LENGTH_SHORT).show();
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
    public static class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView) {
            //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
            Glide.with(imageView).load(url).apply(new RequestOptions().override(Target.SIZE_ORIGINAL)).into(imageView);
        }
        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            try {
                return Glide.with(context).downloadOnly().load(uri).submit().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}