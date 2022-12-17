package com.my.woelegobuy.ui.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.woelegobuy.R;
import com.my.woelegobuy.model.Goods;
import com.my.woelegobuy.utils.BaseRecyclerAdapter;
import com.my.woelegobuy.utils.BaseRecyclerHolder;
import com.my.woelegobuy.utils.IDUtils;
import com.my.woelegobuy.utils.TimeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddGoodsActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView titleTextView;
    private ImageView rightButton;
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText stockEditText;
    private EditText describeEditText;
    private EditText imageEditText;
    private Button addImageButton;
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter<String> adapter;
    private List<String> images = new ArrayList<>();
    private Goods oldData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        initView();
        initData();
    }

    private void initData() {
        adapter = new BaseRecyclerAdapter<String>(this,images,R.layout.item_add_goods_image) {
            @Override
            public void convert(BaseRecyclerHolder holder, String item, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.image,item);
                holder.getView(R.id.remove).setOnClickListener(view -> {
                    Log.e("TAG", "convert: "+position );
                    adapter.delete(position);
                });
            }
        };
        recyclerView.setAdapter(adapter);
        Serializable data = getIntent().getSerializableExtra("data");
        if (data!=null){
            oldData = (Goods) data;
            adapter.addAll(oldData.getImages());
            nameEditText.setText(oldData.getTitle());
            priceEditText.setText(oldData.getPrice()+"");
            stockEditText.setText(oldData.getStock()+"");
            describeEditText.setText(oldData.getDescription());

        }

    }

    private void initView() {
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(view -> {
            finish();
        });
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText("添加商品");
        rightButton = (ImageView) findViewById(R.id.rightButton);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setImageResource(R.mipmap.save);
        rightButton.setOnClickListener(view -> {
            saveData();
        });
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        stockEditText = (EditText) findViewById(R.id.stockEditText);
        describeEditText = (EditText) findViewById(R.id.describeEditText);
        imageEditText = (EditText) findViewById(R.id.imageEditText);
        addImageButton = (Button) findViewById(R.id.addImageButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        addImageButton.setOnClickListener(view -> {
            if (imageEditText.getText().toString().equals("")){
                Toast.makeText(this,"图片地址不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            adapter.insert(imageEditText.getText().toString(),images.size());
            imageEditText.setText("");
        });
    }
    private void saveData() {
        String name = nameEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String stock = stockEditText.getText().toString().trim();
        String desc = describeEditText.getText().toString().trim();
        double priceD = Double.parseDouble(priceEditText.getText().toString().trim());
        int stockD = Integer.parseInt(stockEditText.getText().toString().trim());

        if (name.equals("")){
            Toast.makeText(this,"商品名称不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (price.equals("")){
            Toast.makeText(this,"价格不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (stock.equals("")){
            Toast.makeText(this,"库存不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (desc.equals("")){
            Toast.makeText(this,"描述不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (adapter.getData().size()==0){
            Toast.makeText(this,"图片至少有一个",Toast.LENGTH_SHORT).show();
            return;
        }
        if (oldData!=null){
            //保存老的商品
            oldData.setDescription(desc);
            oldData.setImages(adapter.getData());
            oldData.setPrice(priceD);
            oldData.setStock(stockD);
            oldData.setTitle(name);
            oldData.update(oldData.getId());
            Toast.makeText(this,"保存商品成功",Toast.LENGTH_SHORT).show();
        }else {
            //保存新的商品
            Goods goods = new Goods(adapter.getData(),name,priceD,stockD,desc, TimeUtils.getCurrentTime());
            goods.save();
            Toast.makeText(this,"添加商品成功",Toast.LENGTH_SHORT).show();
        }
        finish();


    }
}
