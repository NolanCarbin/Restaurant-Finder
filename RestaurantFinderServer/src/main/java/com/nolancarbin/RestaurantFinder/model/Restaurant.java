package com.nolancarbin.RestaurantFinder.model;

public class Restaurant {
    private int restaurantId;
    private String name;
    private String location;
    private double rating;
    private String price;
    private String url;


    public Restaurant(String name, String location, double rating, String price, String url) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.price = price;
        this.url = url;
    }

    public Restaurant() {

    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
