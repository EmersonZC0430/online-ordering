package com.my.woelegobuy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.my.woelegobuy.R;
import com.my.woelegobuy.model.Goods;
import com.my.woelegobuy.model.User;
import com.my.woelegobuy.ui.admin.AdminActivity;
import com.my.woelegobuy.ui.login.LoginActivity;
import com.my.woelegobuy.utils.IDUtils;
import com.my.woelegobuy.utils.KVUtils;
import com.my.woelegobuy.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initData();
        Boolean isLogin = KVUtils.getBoolean("isLogin", false);
        if (isLogin){
            User loginUser = KVUtils.getLoginUser();
            if (loginUser!=null){
                startActivity(new Intent(this,MainActivity.class));
            }else {
                startActivity(new Intent(this, AdminActivity.class));
            }
        }else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void initData() {
//        //添加新的
//        List<String> newImages = new ArrayList<>();
//        newImages.add("新的图片地址");
//        newImages.add("新的图片地址");
//        newImages.add("新的图片地址");
//        newImages.add("新的图片地址");
//        Goods newGood2 = new Goods(newImages,"xxxx",17.80,"xxxx", TimeUtils.getCurrentTime());
//        newGood2.save();



        //获取是否保存默认数据
        Boolean isSaveDefData = KVUtils.getBoolean("isSaveDefData", false);
        if (isSaveDefData){
            return;
        }


        List<String> images = new ArrayList<>();
        images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c3185c500efca801203d2202698f.jpg%40900w_1l_2o_100sh.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641368794&t=40966a38f2e0912478beebe9dd5bd7a8");
        images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0176a35bfb9e53a8012092521da5ef.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641368873&t=154e21a175d079bc3e82b51e8d2b70a7");
        images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcp1.douguo.com%2Fupload%2Fcaiku%2Fb%2F9%2Fe%2Fyuan_b9c12894051f43521ac929c6675dc15e.jpeg&refer=http%3A%2F%2Fcp1.douguo.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641368938&t=f7710e0e785190dfe66a32efa1ca55f7");
        Goods goods1 = new Goods(images,"佛跳墙",88.80,10,"清朝道光年间，福州官钱局的官员宴请福建布政使周莲。席间有道叫做“福寿全”的菜、是用鸡、鸭、羊肘、猪蹄、排骨、鸽蛋等以慢火煨制成的。周莲吃后很满意。回家后即命厨师郑春发依法仿制在原菜基础上，减少了肉类用量，又加入了多种海鲜，使成菜内容更加丰富，鲜美可口。后来，郑离开布政使衙门，到福州东街上开了一家“三友斋”菜馆（今福州“聚春园“菜馆的前身），在一次文人聚会的筵席上送上此菜。文人们品后纷纷叫好，有人即席赋诗曰：“坛启荤香飘四邻，佛闻弃禅跳墙来。”从此，这道菜就叫做“佛跳墙”。", TimeUtils.getCurrentTime());
        goods1.save();
        images.clear();
        images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.redocn.com%2Fsheji%2F20181113%2Fmeicaikouroumeishihaibaosheji_9873511.jpg&refer=http%3A%2F%2Fimg.redocn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641369174&t=431bad8d0b8ac757516309ef07864be8");
        Goods goods2 = new Goods(images,"梅菜扣肉",66.88,20,"梅菜扣肉具体起源时间已无可考证。梅菜是广东客家特产，以鲜梅菜为原料经腌制后再脱盐等工艺制成的产品。梅菜历史悠久，闻名中外，是岭南三大名菜之一，为岭南著名传统特产，历史上作为宫廷食品而被称为“惠州贡菜”。民间用新鲜的梅菜经晾晒、精选、飘盐等多道工序制成，色泽金黄、香气扑鼻，清甜爽口，不寒、不燥、不湿、不热，有增强消化，清热解暑，消滞健胃，降脂降压的功效。悠久的历史，独特的风味和功效，以及近千年的传播，铸就了“惠州梅菜”的盛名 [4]  。有古诗描述称：“苎萝西子十里绿，惠州梅菜一枝花”，而惠州被国务院授予“中国梅菜之乡”的称号。", TimeUtils.getCurrentTime());
        goods2.save();
        images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic95.nipic.com%2Ffile%2F201604" +
                "16%2F21537075_092911773000_2.jpg&refer=http%3A%2F%2Fpic95.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641979725&t=1584c9b8486a5d8ec2aa985810b4cc3d");
        images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem" +
                "%2F201611%2F08%2F20161108205154_dHFuX.thumb.400_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641979912&t=7f05b968efd31cd79f8f568c6cb9c3de");
        Goods goods3 = new Goods(images,"北京烤鸭",55.08,30,"烤鸭是具有世界声誉的北京著名菜式，起源于中国南北朝时期，《食珍录》中已记有炙鸭，在当时是宫廷食品。用料为优质肉食鸭北京鸭，" +
                "果木炭火烤制，色泽红润，肉质肥而不腻，外脆里嫩。北京烤鸭分为两大流派，而北京最著名的烤鸭店也即是两派的代表。它以色泽红艳，肉质细嫩，味道醇厚，肥而不腻的特色，被誉为“天下美味”。", TimeUtils.getCurrentTime());
        goods3.save();
        images.clear();
        images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.f600.cn%2FJMSpider%2F3dc96d85fdbc44e39627a45c1e393ce" +
                "5%2F14F4F58AF77.jpg&refer=http%3A%2F%2Fimage.f600.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641980214&t=707255c2c77beae4a04ecd2440d9acb6");
        images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhiphotos.baidu.com%2Fnuomi%2Fpic%2Fitem%2" +
                "Fc83d70cf3bc79f3dc792a8b8b3a1cd11738b29c2.jpg&refer=http%3A%2F%2Fhiphotos.baidu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641980253&t=aa06bee4b9e98e7617f6a679ee180f94");
        Goods goods4 = new Goods(images,"逍遥镇胡辣汤",26.88,40,"逍遥镇胡辣汤是中华风味名吃之一，色香味俱佳，且能醒酒提神，开胃健脾，源于河南省周口市西华县逍遥镇。\n" +
                "正宗的胡辣汤中只用胡椒不用辣椒，即“胡乱辣”，各种辣味加在一起。经过民间若干年的加工发展，一种以适合北方人口味、辣味醇郁、汤香扑鼻的胡辣汤在逍遥镇诞生。", TimeUtils.getCurrentTime());
        goods4.save();
        images.clear();
        //存储是否保存默认数据
        KVUtils.putBoolean("isSaveDefData",true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}