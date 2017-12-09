package com.example.lord.goldenoffers.user;

import java.util.HashMap;
import java.util.List;

public class Desire {

    private int id;
    private String name;
    private float priceLow;
    private float priceHigh;

    public Desire() {
        id = -1;
        name = "";
        priceLow = -1;
        priceHigh = -1;
    }

    public Desire(int id, String name, float priceLow, float priceHigh) {
        this.id = id;
        this.name = name;
        this.priceLow = priceLow;
        this.priceHigh = priceHigh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        return id + " : " + name + " (" + priceLow + " Euros / " + priceHigh + "Euros)";
    }
}
