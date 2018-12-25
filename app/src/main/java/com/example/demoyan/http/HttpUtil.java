package com.example.demoyan.http;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void sendOkHttpRequest(String address, Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpPostRequest(String address, Callback callback, RequestBody requestBody){
        Log.e("", "sendOkHttpPostRequest: ");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void getImage(String address,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
