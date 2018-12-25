package com.example.demoyan.fragment;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.demoyan.R;
import com.youth.banner.loader.ImageLoader;

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object obj, ImageView imageView) {
        int uri = (int) obj;
        imageView.setImageResource(uri);
//        Uri uri = Uri.parse((String) obj);
//        imageView.setImageURI(uri);
    }
    
}