package com.example.demoyan;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.demoyan.constant.ResultCode;
import com.example.demoyan.entity.Image;
import com.example.demoyan.fragment.BannerImageLoader;
import com.example.demoyan.fragment.BitmapImageLodaer;
import com.example.demoyan.fragment.Fragment5;
import com.example.demoyan.fragment.Fragment6;
import com.example.demoyan.netty.advertise.LockClient;
import com.example.demoyan.netty.advertise.lock.AdvertiseServer;
import com.youth.banner.BannerConfig;
import com.youth.banner.transformer.CubeOutTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Projector extends AppCompatActivity {

    private static FragmentManager fragmentManager;
    private Fragment fragment5,fragment6;
    private static List<Fragment> fragments = new ArrayList<>();
    private static Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projector);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        //去掉虚拟按键全屏显示
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                        // bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        init();
        new AdvertiseServer(8869).start();
        LockClient.getClient().sendMessage("registerT");
    }
    private void init(){
        fragmentManager = getSupportFragmentManager();
        fragment5 = new Fragment5();
        fragment6 = new Fragment6();

        fragments.add(0,fragment5);
        fragments.add(1,fragment6);

        mFragment = fragment5;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.view_content,mFragment);
        ft.commitAllowingStateLoss();
    }
    public static void switchFragment(Fragment from, Fragment to) {

        if (mFragment != to) {
            mFragment = to;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.view_content, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ResultCode.SWITCH_FRAGMENT5:
                    switchFragment(mFragment,fragments.get(0));
                    break;
                case ResultCode.SWITCH_FRAGMENT6:
                    switchFragment(mFragment,fragments.get(1));
                    break;
            }
        }

    };
}
