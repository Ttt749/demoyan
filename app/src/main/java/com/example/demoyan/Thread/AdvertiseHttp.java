package com.example.demoyan.Thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;

import com.example.demoyan.MainActivity;
import com.example.demoyan.config.Config;
import com.example.demoyan.entity.Image;
import com.example.demoyan.entity.ImageFile;
import com.example.demoyan.fragment.Fragment5;
import com.example.demoyan.http.HttpUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class AdvertiseHttp extends Thread{

    private String url;

    public AdvertiseHttp(String url){
        this.url = url;
    }

    @Override
    public void run() {
        HttpUtil.sendOkHttpRequest(Config.baseUrl + this.url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ImageFile.clear();
                String bytes = response.body().string();
                try {
                    Gson gson = new Gson();
                    List<String> urls = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(bytes);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        ImageFile imageFile = gson.fromJson(json.toString(),ImageFile.class);
                        ImageFile.addImage(imageFile);
                        urls.add(imageFile.getFileUrl());
                    }
                    Message message = new Message();
                    message.what = 3;
                    Fragment5.handler.sendMessage(message);
//                    Image.removeImage(urls);
//                    for(String url : urls){
//                        if(Image.cacheFiles.containsKey(url)){
//                            continue;
//                        }
//                        if(Image.matchImage(url) != -1){
//
//                        }
//                        getImage(url.trim());
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getImage(final String url){
        HttpUtil.sendOkHttpRequest(Config.baseUrl + url, new okhttp3.Callback() {
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
                Image image = new Image();
                image.setBitmap(bmp);
                image.setUrl(url);
                Image.add(image);
                Message message = new Message();
                message.what = 3;
                Fragment5.handler.sendMessage(message);
            }
        });
    }
}
