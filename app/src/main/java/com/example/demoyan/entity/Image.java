package com.example.demoyan.entity;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Image {
    private static List<Image> images = new ArrayList<>();
    public static Map<String,Integer> cacheFiles = new HashMap<String, Integer>();
    private Bitmap bitmap;
    private String url;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Image get(int index){
        return images.get(index);
    }

    public static void add(Image image){
        cacheFiles.put(image.getUrl(),images.size());
        images.add(image);
    }

    public static void clear(){
        images.clear();
    }

    public static List<Image> getImages() {
        return images;
    }

    public static List<Bitmap> getAllBitmap(){
        List<Bitmap> bitmaps = new ArrayList<>();
        for(Image image : images){
            bitmaps.add(image.getBitmap());
        }
        return bitmaps;
    }
    public static void remove(int index){
        images.remove(index);
    }
    //查看是否存在
    public static int matchImage(String url){
        for(int i =0 ; i<images.size();i++){
            if(images.get(i).getUrl().equals(url)){
                return i;
            }
        }
        return -1;
    }
    //去除重复的
    public static int removeImage(List<String> strings){
        List<String> stringList = new ArrayList<>();
        for(Image imageFile : images){
            stringList.add(imageFile.getUrl());
        }
        List<Integer> exsist = new ArrayList<>();
        for(String string : strings){
            int index = matchImage(string);
            if(index!=-1){
                exsist.add(index);
            }else{

            }
        }
        List<Image> imageList = new ArrayList<>();
        Map<String,Integer> cache = new HashMap<String, Integer>();
        for(Integer i : exsist){
            imageList.add(images.get(i));
            cache.put(images.get(i).getUrl(),i);
        }
        images = imageList;
        cacheFiles = cache;

        return exsist.size();
    }
}
