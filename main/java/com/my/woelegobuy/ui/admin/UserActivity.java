package com.my.woelegobuy.ui.admin;

import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.my.woelegobuy.R;
import com.my.woelegobuy.model.User;
import com.my.woelegobuy.utils.BaseRecyclerAdapter;
import com.my.woelegobuy.utils.BaseRecyclerHolder;
import com.my.woelegobuy.utils.TimeUtils;

import org.litepal.LitePal;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView titleTextView;
    private RecyclerView recyclerView;
    private List<User> userList;
    private BaseRecyclerAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        initData();
    }

    private void initData() {
        userList = LitePal.findAll(User.class);
        adapter = new BaseRecyclerAdapter<User>(this, userList, R.layout.item_user) {
            @Override
            public void convert(BaseRecyclerHolder holder, User item, int position, boolean isScrolling) {
                holder.setText(R.id.account, "手机号: " + item.getAccount());
                holder.setText(R.id.password, "密码: " + item.getPassword());
                holder.setText(R.id.usersite, "地址: " + item.getUsersite());
                if (item.getLastLoginTime() != -1) {
                    holder.setText(R.id.lastTime, "上次登录时间: " + TimeUtils.timeFormat(item.getLastLoginTime(),
                            "yyyy-MM-dd HH:mm:ss"));
                } else {
                    holder.setText(R.id.lastTime, "上次登录时间: 还未登录");
                }
                holder.setText(R.id.createTime, "注册时间: " + TimeUtils.timeFormat(item.getCreateTime(),
                        "yyyy-MM-dd HH:mm:ss"));
                holder.getView(R.id.deleteButton).setOnClickListener(view -> {
                    deleteUser(item,position);
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void deleteUser(User item,int position) {
        new XPopup.Builder(this).asConfirm("删除账号",
                "您确定要删除账号吗?\n删除账号后该用户信息将会被删除,订单会保留\n请谨慎操作",
                () -> {
                    item.delete();
                    adapter.delete(position);
                })
                .show();
    }

    private void initView() {
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(view -> {
            finish();
        });
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText("用户管理");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}