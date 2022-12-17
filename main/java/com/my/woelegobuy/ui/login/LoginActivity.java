package com.my.woelegobuy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.my.woelegobuy.R;
import com.my.woelegobuy.model.User;
import com.my.woelegobuy.ui.MainActivity;
import com.my.woelegobuy.ui.admin.AdminActivity;
import com.my.woelegobuy.utils.KVUtils;
import com.my.woelegobuy.utils.TimeUtils;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText account;
    private EditText usersite;
    private EditText password;
    private CheckBox cbRm;
    private TextView btnRegister;
    private LinearLayout ly1;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        account = (EditText) findViewById(R.id.account);

        password = (EditText) findViewById(R.id.password);
        cbRm = (CheckBox) findViewById(R.id.cb_rm);
        btnRegister = (TextView) findViewById(R.id.btn_register);
        ly1 = (LinearLayout) findViewById(R.id.ly_1);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                submit();
                break;
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private void submit() {
        // validate
        String accountString = account.getText().toString().trim();
        if (TextUtils.isEmpty(accountString)) {
            Toast.makeText(this, "请输入手机号!", Toast.LENGTH_SHORT).show();
            return;
        }
        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "请输入密码!", Toast.LENGTH_SHORT).show();
            return;
        }
        login();
    }
    private void login() {
        String accountString = account.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        if (accountString.equals("77889900")) {
            if (passwordString.equals("admin123")) {
                Toast.makeText(this, "管理员登录成功!", Toast.LENGTH_SHORT).show();
                KVUtils.putBoolean("isLogin",true);
                startActivity(new Intent(this, AdminActivity.class));
                finish();
            } else {
                Toast.makeText(this, "请输入正确的密码!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        List<User> users = LitePal.where("account=?", accountString).find(User.class);
        if (users.size() > 0) {
            User user = users.get(0);
            user.setLastLoginTime(TimeUtils.getCurrentTime());
            user.save();
            if (user.getPassword().equals(passwordString)) {
                Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
                KVUtils.putBoolean("isLogin",true);
                KVUtils.putString("userId",user.getUserId());
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }else {
                Toast.makeText(this, "密码错误!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "该账号还未注册,请注册后使用!", Toast.LENGTH_SHORT).show();
        }


    }
}