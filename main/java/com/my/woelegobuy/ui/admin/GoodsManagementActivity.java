package com.my.woelegobuy.ui.admin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.my.woelegobuy.R;
import com.my.woelegobuy.model.Goods;
import com.my.woelegobuy.utils.BaseRecyclerAdapter;
import com.my.woelegobuy.utils.BaseRecyclerHolder;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class GoodsManagementActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView titleTextView;
    private ImageView rightButton;
    private TextView stockEditText;
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter<Goods> adapter;
    private List<Goods> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_management);
        initView();
        initData();
    }

    private void initData() {
        List<Goods> all = LitePal.findAll(Goods.class);
        if (all.size() > 0) {
            data.addAll(all);
        }
        data.sort((goods, t1) -> (int) (t1.getCreateTime() -goods.getCreateTime()));
        adapter = new BaseRecyclerAdapter<Goods>(this, data, R.layout.item_order) {
            @Override
            public void convert(BaseRecyclerHolder holder, Goods item, int position, boolean isScrolling) {
                holder.setText(R.id.order_goods_title, item.getTitle());
                holder.setText(R.id.order_goods_price, item.getPrice() + "");
                holder.setText(R.id.stockEditText, "库存:"+item.getStock() + "");
                holder.setImageByUrl(R.id.order_image_cover, item.getImages().get(0));
                holder.getView(R.id.delete).setVisibility(View.VISIBLE);
                holder.getView(R.id.delete).setOnClickListener(view -> {
                    deleteGoods(item, position);
                });
            }
        };
        adapter.setOnItemClickListener((parent, view, position) -> {
            Intent intent = new Intent(this, AddGoodsActivity.class);
            intent.putExtra("data", data.get(position));
            startActivity(intent);

        });
        recyclerView.setAdapter(adapter);
    }
    private void deleteGoods(Goods item, int pos) {
        new XPopup.Builder(this).asConfirm("删除商品",
                "您确定要删除这个商品吗?\n删除商品后不可恢复",
                () -> {

                    item.delete();
                    adapter.delete(pos);
                })
                .show();
    }

    private void initView() {
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        rightButton = (ImageView) findViewById(R.id.rightButton);
        rightButton.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        titleTextView.setText("商品管理");
        backButton.setOnClickListener(view -> {
            finish();
        });
        rightButton.setOnClickListener(view -> {
            startActivity(new Intent(this, AddGoodsActivity.class));
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        List<Goods> all = LitePal.findAll(Goods.class);

        data.clear();
        if (all.size() > 0) {
            data.addAll(all);
        }
        data.sort((goods, t1) -> (int) (t1.getCreateTime() -goods.getCreateTime()));
        adapter.addAll(data);


    }
}