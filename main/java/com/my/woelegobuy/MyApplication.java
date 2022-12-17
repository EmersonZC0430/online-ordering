package com.my.woelegobuy;

import android.app.Application;

import com.tencent.mmkv.MMKV;

import org.litepal.LitePal;

/**
 * @author wu.haitao ,Created on {DATE}
 * Major Function：<b></b>
 * @author mender，Modified Date Modify Content:
 */
public class MyApplication extends Application {
    @Override

    public void onCreate() {



        super.onCreate();
        LitePal.initialize(this);
        MMKV.initialize(this);
    }
}
