package com.example.georide;

import java.io.Serializable;

public class MatchedUserModel implements Serializable {

    private String userName,vehicleName,price,time,pickup_distance,drop_distance,rating,imageUri;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPickup_distance() {
        return pickup_distance;
    }

    public void setPickup_distance(String pickup_distance) {
        this.pickup_distance = pickup_distance;
    }

    public String getDrop_distance() {
        return drop_distance;
    }

    public void setDrop_distance(String drop_distance) {
        this.drop_distance = drop_distance;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
