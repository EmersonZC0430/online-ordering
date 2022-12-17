package com.my.woelegobuy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.my.woelegobuy.model.User;
import com.my.woelegobuy.ui.IndexActivity;
import com.my.woelegobuy.ui.MainActivity;
import com.my.woelegobuy.ui.admin.AdminActivity;
import com.my.woelegobuy.ui.login.LoginActivity;
import com.my.woelegobuy.utils.KVUtils;


public class Splash extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(context,LoginActivity.class);
            }
        }, 2000);
    }

    private void startActivity(Context context, Class<LoginActivity> loginActivityClass) {
        startActivity(context,LoginActivity.class);
    }

}