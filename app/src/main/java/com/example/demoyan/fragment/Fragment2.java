package com.example.demoyan.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demoyan.MainActivity;
import com.example.demoyan.R;
import com.example.demoyan.config.Config;
import com.example.demoyan.http.HttpUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Response;

public class Fragment2 extends Fragment {
    private CoordinatorLayout mCoordinatorLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    //设置图片资源:url或本地资源
    Integer[] urls = { R.drawable.house1,
            R.drawable.house1,
            R.drawable.house1,
            R.drawable.house1,
            R.drawable.house1};
    ArrayList<String> list = new ArrayList<>();
    //设置图片标题:自动对应
    String[] titles=new String[]{"智能家居","门锁","影音","灯光","安防"};
    private Banner banner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbarLayout);
        mCollapsingToolbarLayout.setTitle("Sunzxyong");
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置折叠时候title字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.RED);

        /*轮播图*/
        banner = (Banner) view.findViewById(R.id.banner);
        banner.setImages(Arrays.asList(urls))
                .setImageLoader(new BannerImageLoader())
                .start();
        banner.start();


        return view;
    }


}
