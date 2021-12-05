package com.example.ncrexample.model;

public class Item {


    private String title;
    private int imgIcon;

    public Item(String title,int imgIcon) {
        this.title = title;
        this.imgIcon = imgIcon;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }
}
