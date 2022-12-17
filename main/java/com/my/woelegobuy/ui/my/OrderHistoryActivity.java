package com.my.woelegobuy.ui.my;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.woelegobuy.R;
import com.my.woelegobuy.model.OrderModel;
import com.my.woelegobuy.model.User;
import com.my.woelegobuy.utils.BaseRecyclerAdapter;
import com.my.woelegobuy.utils.BaseRecyclerHolder;
import com.my.woelegobuy.utils.KVUtils;
import com.my.woelegobuy.utils.TimeUtils;

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OrderHistoryActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView titleTextView;
    private ImageView rightButton;
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter<OrderModel> adapter;
    private long ordertimes ;
    List<Long> orderstimes =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        initView();
        initData();
    }
    List<OrderModel> data = new ArrayList<>();
    ConcurrentHashMap<Integer, Boolean> shows = new ConcurrentHashMap<>();

    private void sortdata() {
        ordertimes =0;
        shows.clear();
        orderstimes.clear();
        List<OrderModel> datanew = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if(!orderstimes.contains(data.get(i).getCreateTime()))
                orderstimes.add(data.get(i).getCreateTime());
        }

        for (int i = 0; i < orderstimes.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if(data.get(j).getCreateTime()==orderstimes.get(i)){
                    datanew.add(data.get(j));
                }
            }
        }

        for (int i = 0; i < datanew.size(); i++) {
            if (datanew.get(i).getCreateTime()!=ordertimes) {
                shows.put(i, true);
                ordertimes = datanew.get(i).getCreateTime();
            } else {
                shows.put(i, false);
            }

        }
        data.clear();
        data.addAll(datanew);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {
        User loginUser = KVUtils.getLoginUser();
        data = LitePal.where("userId =?", loginUser.getUserId()).find(OrderModel.class);
//        data =LitePal.findAll(OrderModel.class);
        Log.e("TAG", "initData: " + data.size());
        data.sort((goods, t1) -> (int) (t1.getCreateTime() - goods.getCreateTime()));
        sortdata();
        adapter = new BaseRecyclerAdapter<OrderModel>(this, data, R.layout.item_new_order) {
            @Override
            public void convert(BaseRecyclerHolder holder, OrderModel item, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.order_image_cover, item.getImages().get(0));
                holder.setText(R.id.order_goods_title, item.getTitle());
                holder.setText(R.id.order_goods_price, item.getPrice() + "");
                holder.setText(R.id.order_counts1, " x" + item.getCount());
                DecimalFormat df = new DecimalFormat("#.00");
                holder.setText(R.id.order_counts, "总价: " + df.format(item.getPrice() * item.getCount()) + "  ");
                holder.getView(R.id.order_counts1).setVisibility(View.VISIBLE);
                holder.getView(R.id.order_counts).setVisibility(View.VISIBLE);
                holder.getView(R.id.time).setVisibility(View.VISIBLE);
                holder.setText(R.id.time, "购买时间: "+ TimeUtils.timeFormat(item.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                holder.getView(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除
                        delItem(item);
                    }
                });


                TextView mtv_orderid = holder.getView(R.id.mtv_orderid);
                mtv_orderid.setVisibility(View.GONE);
                mtv_orderid.setText(String.format("购买时间:%s",  TimeUtils.timeFormat(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss")));

                if (shows.get(position) != null && shows.get(position)) {
                    mtv_orderid.setVisibility(View.VISIBLE);
                }
            }
        };
        recyclerView.setAdapter(adapter);

    }

    private void delItem(OrderModel item) {
        item.delete();
        data.remove(item);
        sortdata();
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        rightButton = (ImageView) findViewById(R.id.rightButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton.setOnClickListener(view -> {
            finish();
        });
        titleTextView.setText("历史订单");
    }
}