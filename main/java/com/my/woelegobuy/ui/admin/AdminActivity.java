package com.my.woelegobuy.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.my.woelegobuy.R;
import com.my.woelegobuy.ui.login.LoginActivity;
import com.my.woelegobuy.utils.KVUtils;

public class AdminActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView titleTextView;
    private Button userManagement;
    private Button goodsManagement;
    private Button orderManagement;
    //private Button adminManagement;
    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
    }

    private void initView() {
        backButton = (ImageView) findViewById(R.id.backButton);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        userManagement = (Button) findViewById(R.id.userManagement);
        goodsManagement = (Button) findViewById(R.id.goodsManagement);
        //adminManagement = (Button) findViewById(R.id.adminManagement);
        orderManagement = (Button) findViewById(R.id.orderManagement);
        titleTextView.setText("管理后台");
        logOut = (Button) findViewById(R.id.logOut);
        //点击用户管理
        userManagement.setOnClickListener(view -> {
            startActivity(new Intent(this, UserActivity.class));
        });
        //点击商品管理
        goodsManagement.setOnClickListener(view -> {
            startActivity(new Intent(this, GoodsManagementActivity.class));
        });
        //点击订单管理
        orderManagement.setOnClickListener(view -> {
            startActivity(new Intent(this, OrderActivity.class));
        });

        //点击修改管理员信息
        //adminManagement.setOnClickListener(view -> {
        //    startActivity(new Intent(this, AdminManagementActivity.class));
        //});
        //点击退出登录
        logOut.setOnClickListener(view -> {
            KVUtils.putString("userId", null);
            KVUtils.putBoolean("isLogin", false);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }

}