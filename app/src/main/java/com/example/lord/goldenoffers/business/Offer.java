package com.example.lord.goldenoffers.business;


import android.media.Image;

public class Offer {
    private int id;
    private int business_id;
    private String uid;
    private String product_name;
    private String price;
    private String description;
    private String photo;
    private String regDate;
    private String expDate;

    public String getUid() {
        return uid;
    }

    public int getId() {
        return id;
    }

    public int getBusiness_id() {
        return business_id;
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

    public String getPhoto() {
        return photo;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getExpDate() {
        return expDate;
    }




    public Offer(int id, int business_id,String uid, String product_name, String price, String description, String photo, String regDate, String expDate) {
        this.id = id;
        this.business_id = business_id;
        this.uid=uid;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.photo = photo;
        this.regDate = regDate;
        this.expDate = expDate;
    }







}
