package com.eagleeye.eagleeye;

import android.net.Uri;

import static android.R.attr.rating;

/**
 * Created by 2dam on 09/02/2017.
 */

public class Lugar {
    String id;
    String name;
    Double lat;
    Double lng;
    String adress;
    String phone;
    Uri web;
    float rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Uri getWeb() {
        return web;
    }

    public void setWeb(Uri web) {
        this.web = web;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
