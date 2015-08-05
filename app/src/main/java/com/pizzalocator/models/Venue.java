package com.pizzalocator.models;


import java.io.Serializable;

import lombok.Data;

@Data
public class Venue implements Serializable{
    private String name;
    private String formattedPhone;
    private String address;
    private String city;
    private String zipCode;
    private String state;
    private boolean hasMenu;
    private String mobileUrl;
    private double latitude;
    private double longitude;

    public Venue() {}


    public Venue(String name, String formattedPhone, String address, String city, String zipCode, String state, boolean hasMenu, String mobileUrl, double latitude, double longitude) {
        this.name = name;
        this.formattedPhone = formattedPhone;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.hasMenu = hasMenu;
        this.mobileUrl = mobileUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
