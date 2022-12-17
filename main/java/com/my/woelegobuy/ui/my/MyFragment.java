package com.my.woelegobuy.ui.my;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.my.woelegobuy.R;
import com.my.woelegobuy.model.User;
import com.my.woelegobuy.ui.login.LoginActivity;
import com.my.woelegobuy.utils.IDUtils;
import com.my.woelegobuy.utils.KVUtils;

import org.litepal.LitePal;


public class MyFragment extends Fragment {

    private TextView titleTextView;
    private ImageView backButton;
    private TextView account;
    private EditText usersite;
    private EditText password;
    private User user;
    private Button logOut, mBtnUpdate;
    private LinearLayout order;
    private String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_my, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initView(View inflate) {
        titleTextView = inflate.findViewById(R.id.titleTextView);
        titleTextView.setText("我的");
        account = inflate.findViewById(R.id.account);
        usersite = inflate.findViewById(R.id.usersite);
        password = inflate.findViewById(R.id.password);
        mBtnUpdate = inflate.findViewById(R.id.user_alter);
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//修改
                String addr = usersite.getText().toString().trim();
                String pass = password.getText().toString().trim();
                Log.d("TAG", "onClick: 修改 = " + addr + " == " + pass);
                User mUser = user;
                mUser.setPassword(pass)
                        .setUsersite(addr);
                mUser.save();
                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
            }
        });
        logOut = inflate.findViewById(R.id.logOut);
        logOut.setOnClickListener(view -> {
            KVUtils.putString("userId", null);
            KVUtils.putBoolean("isLogin", false);
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
        order = (LinearLayout) inflate.findViewById(R.id.order);
        order.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), OrderHistoryActivity.class));
        });
    }
    private void initData() {//获取登录的用户
        user = KVUtils.getLoginUser();
        account.setText(user.getAccount());
        password.setText(user.getPassword());
        usersite.setText(user.getUsersite());
        userId = user.getUserId();
    }
}