package com.my.woelegobuy.ui.shoppingCart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.my.woelegobuy.R;
import com.my.woelegobuy.model.Goods;
import com.my.woelegobuy.model.ShoppingCart;
import com.my.woelegobuy.model.User;
import com.my.woelegobuy.utils.BaseRecyclerAdapter;
import com.my.woelegobuy.utils.BaseRecyclerHolder;
import com.my.woelegobuy.utils.KVUtils;
import com.my.woelegobuy.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class ShoppingCartFragment extends Fragment {

    //初始化View
    private RecyclerView rv;

    //结算button
    private Button btn;
    //还未添加商品
    private TextView hint;
    //价格
    private TextView totalValue;

    private List<ShoppingCart> list = new ArrayList<>();
    private BaseRecyclerAdapter<ShoppingCart> adapter;
    private TextView titleTextView;
    private User user;
    private ImageView rightButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        EventBus.getDefault().register(this);
        user = KVUtils.getLoginUser();

        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {

        list.clear();
        List<ShoppingCart> data = LitePal.findAll(ShoppingCart.class);
        //看数据中是否有

        List<Goods> all = LitePal.findAll(Goods.class);
        for (ShoppingCart shoppingCart : data) {
            if (user.getUserId().equals(shoppingCart.getUserId())) {
                boolean isHave = false;
                for (Goods goods : all) {
                    if (shoppingCart.getGoodsId() == goods.getId()) {
                        shoppingCart.setGoods(goods);
                        isHave = true;
                    }
                }

                if (isHave) {
                    list.add(shoppingCart);
                }
            }
        }
        if (list.size() == 0) {
            //没有 则显示 还未添加购物
            hint.setVisibility(View.VISIBLE);
            //隐藏列表布局
            rv.setVisibility(View.INVISIBLE);
        } else {
            //没有 则显示 还未添加购物
            hint.setVisibility(View.GONE);
            //隐藏列表布局
            rv.setVisibility(View.VISIBLE);
        }
        Log.e("TAG", "initData: " + data.size() + "===" + list.size());
        if (adapter == null) {
            //初始化适配
            adapter = new BaseRecyclerAdapter<ShoppingCart>(getContext(), list, R.layout.item_order) {
                @Override
                public void convert(BaseRecyclerHolder holder, ShoppingCart item, int position, boolean isScrolling) {
                    if (item.getGoods() != null) {
                        holder.setImageByUrl(R.id.order_image_cover, item.getGoods().getImages().get(0));
                        holder.setText(R.id.order_goods_title, item.getGoods().getTitle());
                        holder.setText(R.id.order_goods_price, item.getGoods().getPrice() + "");
                    }

                    holder.setText(R.id.order_counts, "" + item.getCount());
                    holder.getView(R.id.is_select).setVisibility(View.VISIBLE);
                    holder.getView(R.id.order_counts).setVisibility(View.VISIBLE);
                    holder.getView(R.id.reduce).setVisibility(View.VISIBLE);
                    holder.getView(R.id.add).setVisibility(View.VISIBLE);
                    CheckBox view = (CheckBox) holder.getView(R.id.is_select);
                    view.setChecked(item.isSelect());
                    view.setOnCheckedChangeListener((compoundButton, b) -> {
                        item.setSelect(b);
                        item.update(item.getId());
                        calculateThePrice();
                    });
                    holder.getView(R.id.add).setOnClickListener(view1 -> {
                        add(item, position);
                    });
                    holder.getView(R.id.reduce).setOnClickListener(view1 -> {
                        reduce(item, position);
                    });
                }
            };
            rv.setAdapter(adapter);
        } else {
            adapter.addAll(list);
        }


    }

    private void add(ShoppingCart item, int position) {
        item.setCount(item.getCount() + 1);
        adapter.notifyItemChanged(position);
        calculateThePrice();
        item.update(item.getId());
    }

    private void reduce(ShoppingCart item, int position) {
        if (item.getCount() == 1) {
            adapter.delete(position);
            item.setGoods(null);
            item.setGoodsId(99999999);
            item.delete();
        } else {
            item.setCount(item.getCount() - 1);
            adapter.notifyItemChanged(position);
            item.update(item.getId());

        }

        calculateThePrice();
    }

    private void calculateThePrice() {
        double totalPrice = 0;
        for (ShoppingCart datum : adapter.getData()) {
            if (datum.isSelect()) {
                totalPrice += (datum.getGoods().getPrice() * datum.getCount());
            }
        }

        if (totalPrice == 0) {
            totalValue.setText("￥0.0");
        } else {
            DecimalFormat df = new DecimalFormat("#.00");
            String str = df.format(totalPrice);
            totalValue.setText("￥" + str);
        }
    }

    private void initView(View view) {
        rv = view.findViewById(R.id.shoppingCarRv);
        rv.setItemAnimator(null);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        btn = view.findViewById(R.id.buyBtn);
        hint = view.findViewById(R.id.empty_layout_hint);
        totalValue = view.findViewById(R.id.total_value);
        hint.setVisibility(View.INVISIBLE);
        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        titleTextView.setText("购物车");
        btn.setOnClickListener(view1 -> {
            if (list.size() > 0) {
                boolean isSelect = false;
                for (ShoppingCart datum : adapter.getData()) {
                    if (datum.isSelect()) {
                        isSelect = true;
                    }
                }
                if (isSelect) {
                    startActivity(new Intent(getActivity(), OrderSettlementActivity.class));
                } else {
                    Toast.makeText(getContext(), "还未选择商品!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "还未添加商品!", Toast.LENGTH_SHORT).show();
            }

        });

        rightButton = (ImageView) view.findViewById(R.id.rightButton);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setImageResource(R.mipmap.clear);
        rightButton.setOnClickListener(view1 -> {
            new XPopup.Builder(getActivity()).asConfirm("清空购物车", "您确定要清空购物车吗?",
                    () -> {
                        for (ShoppingCart shoppingCart : list) {
                            shoppingCart.setGoods(null);
                            shoppingCart.setGoodsId(99999999);
                            shoppingCart.delete();
                        }
                        initData();
                    })
                    .show();
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refShoppingCart(MessageEvent messageEvent) {
        Log.e("TAG", "refShoppingCart: " + messageEvent.getMessage());
        initData();
        calculateThePrice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}