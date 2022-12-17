package com.my.woelegobuy.ui.shoppingCart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.my.woelegobuy.R;
import com.my.woelegobuy.model.Goods;
import com.my.woelegobuy.model.OrderModel;
import com.my.woelegobuy.model.ShoppingCart;
import com.my.woelegobuy.model.User;
import com.my.woelegobuy.utils.BaseRecyclerAdapter;
import com.my.woelegobuy.utils.BaseRecyclerHolder;
import com.my.woelegobuy.utils.IDUtils;
import com.my.woelegobuy.utils.KVUtils;
import com.my.woelegobuy.utils.MessageEvent;
import com.my.woelegobuy.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderSettlementActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView titleTextView;
    private RecyclerView recyclerView;
    private Button settlement;
    private List<ShoppingCart> list = new ArrayList<>();
    private BaseRecyclerAdapter<ShoppingCart> adapter;
    private User user;
    private TextView totalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_settlement);
        initView();
        initData();
    }

    private void initData() {
        user = KVUtils.getLoginUser();
        List<ShoppingCart> data = LitePal.findAll(ShoppingCart.class);
        //看数据中是否有
        double totalPrice = 0;
        List<Goods> all = LitePal.findAll(Goods.class);
        for (ShoppingCart shoppingCart : data) {
            if (!shoppingCart.isSelect()){
                continue;
            }
            if (user.getUserId().equals(shoppingCart.getUserId())) {
                boolean isHave = false;
                for (Goods goods : all) {
                    if (shoppingCart.getGoodsId() == goods.getId()) {
                        shoppingCart.setGoods(goods);
                        isHave = true;
                    }
                }

                if (isHave) {
                    totalPrice += (shoppingCart.getGoods().getPrice() * shoppingCart.getCount());
                    list.add(shoppingCart);
                }
            }
        }
        adapter = new BaseRecyclerAdapter<ShoppingCart>(this, list, R.layout.item_order) {
            @Override
            public void convert(BaseRecyclerHolder holder, ShoppingCart item, int position, boolean isScrolling) {
                if (item.getGoods() != null) {
                    holder.setImageByUrl(R.id.order_image_cover, item.getGoods().getImages().get(0));
                    holder.setText(R.id.order_goods_title, item.getGoods().getTitle());
                    holder.setText(R.id.order_goods_price, item.getGoods().getPrice() + "");
                }

                holder.setText(R.id.order_counts, "x" + item.getCount() + "  ");
                holder.getView(R.id.order_counts).setVisibility(View.VISIBLE);
                CheckBox view = (CheckBox) holder.getView(R.id.is_select);
                view.setChecked(item.isSelect());

            }
        };
        recyclerView.setAdapter(adapter);
        DecimalFormat df = new DecimalFormat("#.00");
        String str = df.format(totalPrice);
        totalValue.setText("￥" + str);
    }

    private void initView() {
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(view -> {
            finish();
        });
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText("订单结算");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        settlement = (Button) findViewById(R.id.settlement);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        totalValue = (TextView) findViewById(R.id.total_value);
        settlement.setOnClickListener(view -> {
            settlement();
        });
    }
    private void settlement(){
        new XPopup.Builder(this)
                .hasStatusBarShadow(false)
                //.dismissOnBackPressed(false)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .autoOpenSoftInput(true)
                .isDarkTheme(true)
//              .autoFocusEditText(false) //是否让弹窗内的EditText自动获取焦点，默认是true
                //.moveUpToKeyboard(false)   //是否移动到软键盘上面，默认为true
                .asInputConfirm("请输入密码", null, null, "密码",
                        new OnInputConfirmListener() {
                            @Override
                            public void onConfirm(String password) {
//                                new XPopup.Builder(getContext()).asLoading().show();
                                if (user.getPassword().equals(password)) {
                                    boolean isgou=true;
                                    for (ShoppingCart shoppingCart : list) {
                                        if(shoppingCart.getGoods().getStock()<shoppingCart.getCount()){
                                            isgou=false;
                                            break;
                                        }

                                    }
                                    if(isgou) {
                                        Toast.makeText(OrderSettlementActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                                        saveData();
                                    }else{
                                        Toast.makeText(OrderSettlementActivity.this, "支付失败，库存不足，请修改数量！", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(OrderSettlementActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .show();
    }
    private void saveData(){
        long time=TimeUtils.getCurrentTime();
        for (ShoppingCart shoppingCart : list) {
            OrderModel orderModel = new OrderModel()
                    .setOrderId(IDUtils.getId())
                    .setAccount(user.getAccount())
                    .setUsersite(user.getUsersite())
                    .setCount(shoppingCart.getCount())
                    .setCreateTime(time)
                    .setImages(shoppingCart.getGoods().getImages())
                    .setPrice(shoppingCart.getGoods().getPrice())
                    .setTitle(shoppingCart.getGoods().getTitle())
                    .setUserId(user.getUserId());
            shoppingCart.getGoods().setStock(shoppingCart.getGoods().getStock()-shoppingCart.getCount()).save();
            orderModel.save();
            shoppingCart.setGoods(null);
            shoppingCart.delete();
        }
        EventBus.getDefault().post(new MessageEvent("add goods"));
        finish();
    }
}