package com.example.georide;

import java.io.Serializable;

public class MatchedUserModel implements Serializable {

    private String userName,vehicleName,price,time,pickup_distance,drop_distance,rating,imageUri;

    private double startLatitude,startLongitude,endLatitude,endLongitude;

    String getUserName() {
        return userName;
    }

    double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String getPickup_distance() {
        return pickup_distance;
    }

    public void setPickup_distance(String pickup_distance) {
        this.pickup_distance = pickup_distance;
    }

    String getDrop_distance() {
        return drop_distance;
    }

    public void setDrop_distance(String drop_distance) {
        this.drop_distance = drop_distance;
    }

    String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
