package com.example.demoyan.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageFile {
    private static List<ImageFile> imageFiles = new ArrayList<>();

    private String fileName;
    private String fileUrl;

    public ImageFile() {
    }

    public ImageFile(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public static List<ImageFile> getImageFiles() {
        return imageFiles;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public static void addImage(ImageFile imageFile){
        imageFiles.add(imageFile);
    }

    public static ImageFile getImage(int index){
        return imageFiles.get(index);
    }

    public static void clear(){
        imageFiles.clear();
    }
}
