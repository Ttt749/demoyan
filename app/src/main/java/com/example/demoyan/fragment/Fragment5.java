package com.example.demoyan.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demoyan.MainActivity;
import com.example.demoyan.R;
import com.example.demoyan.config.Config;
import com.example.demoyan.entity.Image;
import com.example.demoyan.entity.ImageFile;
import com.example.demoyan.http.HttpUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.transformer.CubeOutTransformer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class Fragment5 extends Fragment {

    private static Banner banner;
    Integer[] urls = { R.drawable.house1,
            R.drawable.house1,
            R.drawable.house1,
            R.drawable.house1,
            R.drawable.house1};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment5,container,false);

        /*轮播图*/
        banner = (Banner) view.findViewById(R.id.projector_banner);
        banner.setImages(Arrays.asList(urls))
                .setBannerAnimation(CubeOutTransformer.class)
                .setDelayTime(5000)
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setImageLoader(new BannerImageLoader())
                .start();
        banner.start();
        return view;
    }
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    Integer[] urls = { R.drawable.timg,
                            R.drawable.timg,
                            R.drawable.timg,
                            R.drawable.timg,
                            R.drawable.timg};
                    banner.setImages(Arrays.asList(urls))
                            .setBannerAnimation(CubeOutTransformer.class)
                            .setDelayTime(3000)
                            .setBannerStyle(BannerConfig.NOT_INDICATOR)
                            .setImageLoader(new BannerImageLoader())
                            .start();
                    banner.start();
                    break;
                case 3:
                    List<Bitmap> images = Image.getAllBitmap();
                    banner.setImages(ImageFile.getImageFiles())
                            .setBannerAnimation(CubeOutTransformer.class)
                            .setDelayTime(3000)
                            .setBannerStyle(BannerConfig.NOT_INDICATOR)
                            .setImageLoader(new BitmapImageLodaer())
                            .start();
                    banner.start();
                    break;
            }
        }

    };
    public static void getImage(final String string){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtil.sendOkHttpRequest(Config.baseUrl + string, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("", "handleMessage: "+ e);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        byte[] bytes = response.body().bytes();
                        //response.body().close();
                        //把byte字节组装成图片
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Log.e("", "handleMessage: "+bmp );
                        Message message = new Message();
                        message.what = 3;
                        message.obj = bmp;
                        MainActivity.handler.sendMessage(message);
                    }
                });
            }
        }).start();
    }
}
