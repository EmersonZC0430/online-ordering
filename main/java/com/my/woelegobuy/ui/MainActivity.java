package com.my.woelegobuy.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.my.woelegobuy.R;
import com.my.woelegobuy.ui.home.HomeFragment;
import com.my.woelegobuy.ui.my.MyFragment;
import com.my.woelegobuy.ui.shoppingCart.ShoppingCartFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * (1)注册功能:该功能点就是用户常见的信息注册，规定了用户注册的方式、密码的长短。
 * 在本平台中用户注册采用虚拟的手机号码，当用户输入手机号码后，可以直接设置密码进行注册。
 * (2) 登录功能:该功能点是用户填写的用户手机号码以及密码
 * (3)商品浏览功能:用户通过点击商品的展示图跳转到产品详情中，就可以查看每个商品的信息。
 * (4)商品搜索功能:买家用户可以通过搜索栏对于所需商品进行搜索，并显示出所搜索商品。
 * (5)购物车功能:用户可以添加所需商品到购物车，在可以选择的栏框里进行加减选择，点击购物车中的清空按钮便可以清空购物车。
 * (6)支付功能:当用户对于所需商品进行购买时，可以点击结算按钮，
 * 会跳转到订单结算界面，显示购买金额。点击付款按钮显示支付成功，点击取消支付自动返回购物车。
 *
 * 管理端，对用户的信息进行查询和更新，对商品信息的管理、订单信息的存取及处理。主要分为以下三个功能:
 * (1)用户管理功能:管理员进入操作后，填写相应信息后，便可查询用户的登录详情。
 * 通过此方式加强对平台用户的管理，增加平台的安全性.
 * (2)商品管理功能:管理员通过登录系统，对商品的信息进行管理。
 * 如增加新的商品种类，删除下架商品，给商品添加详情介绍；增加或者删除平台里某些商品的图片等。
 * (3)订单管理功能:管理员可以查询该订单的相关信息。
 */
public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragmentList = new ArrayList<>();
    private HomeFragment homeFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private MyFragment myFragment;
    private ViewPager viewPager;
    private BottomNavigationView bottomBar;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        bottomBar = (BottomNavigationView) findViewById(R.id.bottomBar);
        homeFragment = new HomeFragment();
        shoppingCartFragment = new ShoppingCartFragment();
        myFragment = new MyFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(shoppingCartFragment);
        fragmentList.add(myFragment);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (Fragment fragment : fragmentList) {
            adapter.addFragment(fragment);
        }
        viewPager.setAdapter(adapter);
        bottomBar.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_home:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.action_shopping_cart:
                            viewPager.setCurrentItem(1);
                            break;
                        case R.id.action_my:
                            viewPager.setCurrentItem(2);
                            break;
                    }
                    return false;
                });
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomBar.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomBar.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}