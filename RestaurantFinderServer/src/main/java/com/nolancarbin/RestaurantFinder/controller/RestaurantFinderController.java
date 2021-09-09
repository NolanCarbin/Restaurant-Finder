package com.nolancarbin.RestaurantFinder.controller;

import com.nolancarbin.RestaurantFinder.dao.RestaurantDao;
import com.nolancarbin.RestaurantFinder.model.Restaurant;
import com.nolancarbin.RestaurantFinder.services.YelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin //use this if needed to make multiple calls through local port
public class RestaurantFinderController {

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    YelpService yelpService;


    //http://localhost:8080/search?term=restaurants&location=Detroit&radius=8046&categories=italian,american&price=1
    //radius is in miles, Integer no decimal
    @RequestMapping(path= "/search", method = RequestMethod.GET)
    public List<Restaurant> searchForRestaurants(@RequestParam(defaultValue = "Restaurant") String term,
                                                 @RequestParam String location,
                                                 @RequestParam Integer radius,
                                                 @RequestParam String categories,
                                                 @RequestParam Integer price) {
        String apiQuery = "term=" + term + "&location=" + location + "&radius=" + yelpService.convertMilesToMeters(radius) +
                "&categories=" + categories + "&price=" + price;
        return yelpService.getSearchResults(apiQuery);
    }


    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/restaurants", method = RequestMethod.GET)
    public List<Restaurant> getAllSavedRestaurants() {
        return restaurantDao.getAllSavedRestaurants();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/restaurants", method = RequestMethod.POST)
    public boolean saveRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantDao.saveRestaurant(restaurant);
    }

    @RequestMapping(path = "/restaurants/{id}", method = RequestMethod.DELETE)
    public boolean deleteRestaurant(@PathVariable int id) {
        return restaurantDao.deleteRestaurant(id);
    }


}
