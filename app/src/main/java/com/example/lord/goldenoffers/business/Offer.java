package com.example.lord.goldenoffers.business;


import android.graphics.Bitmap;
import android.media.Image;

public class Offer {
    private int id;
    //private int business_id;
    private String uid;
    private String product_name;
    private String price;
    private String description;
    private Bitmap image;
    private String regDate;
    private String expDate;

    public String getUid() {
        return uid;
    }

    public int getId() {
        return id;
    }

    //public int getBusiness_id() {
        //return business_id;
    //}

    public String getProduct_name() {
        return product_name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getPhoto() {
        return image;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getExpDate() {
        return expDate;
    }




    public Offer(int id,String uid, String product_name, String price, String description, Bitmap image, String regDate, String expDate) {
        this.id = id;
        //this.business_id = business_id;
        this.uid=uid;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.regDate = regDate;
        this.expDate = expDate;
    }







}
