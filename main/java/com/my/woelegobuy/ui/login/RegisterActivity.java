package com.my.woelegobuy.ui.login;

import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.my.woelegobuy.R;
import com.my.woelegobuy.model.User;
import com.my.woelegobuy.utils.IDUtils;

import org.litepal.LitePal;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText account;
    private EditText password;
    private EditText usersite;
    private EditText confirmPassword;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        usersite = (EditText) findViewById(R.id.usersite);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                submit();
                break;
        }
    }

    private void submit() {
        String accountString = account.getText().toString().trim();
        if (TextUtils.isEmpty(accountString)) {
            Toast.makeText(this, "请输入手机号!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isPhoneNumber(accountString)) {
            Toast.makeText(this, "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
            return;
        }
        String usersiteString = usersite.getText().toString().trim();
        if (TextUtils.isEmpty(usersiteString)) {
            Toast.makeText(this, "请输入地址!", Toast.LENGTH_SHORT).show();
            return;
        }
        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "请输入密码!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordString.length()<=5) {
            Toast.makeText(this, "请6位及以上的密码!", Toast.LENGTH_SHORT).show();
            return;
        }
        String confirmPasswordString = confirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(confirmPasswordString)) {
            Toast.makeText(this, "请输入确认密码!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passwordString.equals(confirmPasswordString)) {
            Toast.makeText(this, "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
            return;
        }
        register();
    }

    private void register() {
        String accountString = account.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        String site = usersite.getText().toString().trim();
        List<User> users = LitePal.where("account=?", accountString).find(User.class);
        if (users.size() > 0) {
            Toast.makeText(this, "此账号已被注册!", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User()
                .setUserId(IDUtils.getId())
                .setAccount(accountString)
                .setPassword(passwordString)
                .setPaymentPassword("")
                .setUsersite(site)
                .setLastLoginTime(-1)
                .setCreateTime(System.currentTimeMillis());
        user.save();
        finish();
        Toast.makeText(this, "恭喜您, 注册成功!", Toast.LENGTH_SHORT).show();
    }

    public boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[^4,\\D])" + "|(134[^9,\\D])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        return false;
    }

}