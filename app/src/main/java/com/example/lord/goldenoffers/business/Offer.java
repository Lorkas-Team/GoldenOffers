package com.example.lord.goldenoffers.business;


import android.graphics.Bitmap;
import android.media.Image;

public class Offer {

    public Offer(int id, String uid, String product_name, String price, String description,Bitmap image,  String regDate, String expDate, String business_name, String longitude, String latitude) {
        this.id = id;
        this.uid = uid;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.regDate = regDate;
        this.expDate = expDate;
        this.business_name = business_name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    private int id;
    private String uid;
    private String product_name;
    private String price;
    private String description;
    private Bitmap image;
    private String regDate;
    private String expDate;
    private String business_name;
    private String longitude;
    private String latitude;



    public String getBusiness_name() {
        return business_name;
    }



    public String getLongitude() {
        return longitude;
    }



    public String getLatitude() {
        return latitude;
    }





    public String getUid() {
        return uid;
    }

    public int getId() {
        return id;
    }



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
        this.uid=uid;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.regDate = regDate;
        this.expDate = expDate;
    }







}
