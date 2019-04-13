package com.sust.iuttechfest.shop;

import java.io.Serializable;

public class ShopItem implements Serializable {
    private String seller;
    private String title;
    private String price;
    private String address;
    private String phoneNo;
    private String quantity;

    public ShopItem(String seller, String title, String price, String address, String phoneNo, String quantity, String imageuri) {
        this.seller = seller;
        this.title = title;
        this.price = price;
        this.address = address;
        this.phoneNo = phoneNo;
        this.quantity = quantity;
        this.imageuri = imageuri;
    }

    public ShopItem() {
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    private String imageuri;

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
