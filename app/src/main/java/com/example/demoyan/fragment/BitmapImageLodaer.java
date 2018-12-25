package com.example.demoyan.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.demoyan.R;
import com.example.demoyan.config.Config;
import com.example.demoyan.entity.ImageFile;
import com.example.demoyan.util.ScreenUtils;
import com.youth.banner.loader.ImageLoader;

public class BitmapImageLodaer extends ImageLoader {
    @Override
    public void displayImage(Context context, Object obj, ImageView imageView) {
        ImageFile imageFile = (ImageFile) obj;
        Glide.with(context)
                .load(Config.baseUrl+imageFile.getFileUrl())
                .placeholder(R.mipmap.ic_launcher)
                .override(ScreenUtils.getScreenWidth(context),ScreenUtils.getScreenHeight(context))
                .into(imageView);
//        Bitmap uri = (Bitmap) obj;
//        imageView.setImageBitmap(uri);
    }

}
