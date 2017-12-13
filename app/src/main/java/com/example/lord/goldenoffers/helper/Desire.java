package com.example.lord.goldenoffers.helper;

public class Desire {

    private int dbID; //id from servers database, NOT from sqlite
    private String name;
    private float priceLow;
    private float priceHigh;

    public Desire() {
        dbID = -1;
        name = "";
        priceLow = -1;
        priceHigh = -1;
    }

    public Desire(int id, String name, float priceLow, float priceHigh) {
        this.dbID = id;
        this.name = name;
        this.priceLow = priceLow;
        this.priceHigh = priceHigh;
    }

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int id) {
        this.dbID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(float priceLow) {
        this.priceLow = priceLow;
    }

    public float getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(float priceHigh) {
        this.priceHigh = priceHigh;
    }

    public String toString() {

        return dbID + " : " + name + " (" + priceLow + " Euros / " + priceHigh + "Euros)";
    }
}
