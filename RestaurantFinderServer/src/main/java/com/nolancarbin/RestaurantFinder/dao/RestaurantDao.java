package com.nolancarbin.RestaurantFinder.dao;

import com.nolancarbin.RestaurantFinder.model.Restaurant;

import java.util.List;

public interface RestaurantDao {
    List<Restaurant> getAllSavedRestaurants();

    Restaurant getRestaurantById(int id);

    Restaurant getRestaurantByName(String name);

    boolean saveRestaurant(Restaurant restaurant);

    Restaurant updateRestaurant(Restaurant updatedRestaurant, int id);

    boolean deleteRestaurant(int id);
}
