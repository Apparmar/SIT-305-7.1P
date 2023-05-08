package com.example.a7_1p.model;

import android.content.Intent;

import java.util.Date;

public class Item {
    private int itemId;
    private int lost;
    private String name;
    private Integer phone;
    private String description;
    private String date;
    private String location;

    public Item(int lost,  String name, Integer phone, String description, String date, String location) {
        this.lost = lost;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
    }

    public Item() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int isLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
