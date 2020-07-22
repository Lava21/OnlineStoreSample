package com.mas.sampleonlinestore.model;

public class ProductModel {

    public String name, store, img;
    public long price, price_old;
    public int discount;
    public float rating;

    public ProductModel(String name, String store, String img, long price, long price_old, int discount, float rating) {
        this.name = name;
        this.store = store;
        this.img = img;
        this.price = price;
        this.price_old = price_old;
        this.discount = discount;
        this.rating = rating;
    }
}
