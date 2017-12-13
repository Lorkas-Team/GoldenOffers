package com.example.lord.goldenoffers.helper;

public class Offer {
    //TODO image dates x/y type
    private int id;
    private String productName, description;

    private float price;
    private String image;
    private String regDate, expDate;

    private String uid, businessName;
    private String strLongitude;
    private String strLatitude;

    public Offer(int id, String uid, String productName,
                 float price, String description, String image,
                 String regDate, String expDate) {

        this.id = id;
        this.uid = uid;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.image = image;
        this.regDate = regDate;
        this.expDate = expDate;
        businessName = "";
        strLongitude = "";
        strLatitude = "";
    }

    public Offer(int id, String uid, String productName,
                 float price, String description, String image,
                 String regDate, String expDate, String businessName,
                 String strLongitude, String strLatitude) {

        this.id = id;
        this.uid = uid;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.image = image;
        this.regDate = regDate;
        this.expDate = expDate;
        this.businessName = businessName;
        this.strLongitude = strLongitude;
        this.strLatitude = strLatitude;
    }

    public String getUID() {
        return uid;
    }
    public int getID() {
        return id;
    }
    public String getProductName() {
        return productName;
    }
    public float getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }
    public String getRegDate() {
        return regDate;
    }
    public String getExpDate() {
        return expDate;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setStrLongitude(String strLongitude) {
        this.strLongitude = strLongitude;
    }

    public void setStrLatitude(String strLatitude) {
        this.strLatitude = strLatitude;
    }
}
