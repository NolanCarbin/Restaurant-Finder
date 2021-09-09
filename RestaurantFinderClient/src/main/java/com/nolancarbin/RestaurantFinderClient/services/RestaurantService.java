package com.nolancarbin.RestaurantFinderClient.services;

import com.nolancarbin.RestaurantFinderClient.model.Restaurant;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RestaurantService {

    private static final String BASE_URL = "http://localhost:8080/";

    private RestTemplate restTemplate;

    private List<Restaurant> savedRestaurants;


    public RestaurantService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Used to call the Yelp API
    public List<Restaurant> searchForRestaurants(String searchQuery) {
        String url = BASE_URL + "search?";
        HttpEntity<String> requestEntity = new HttpEntity<>(makeHttpHeaders());

        Restaurant[] response = restTemplate.exchange(
                url + searchQuery,
                HttpMethod.GET,
                requestEntity,
                Restaurant[].class).getBody();
        return Arrays.asList(response);
    }

    public void getSavedRestaurants() {
        HttpEntity<String> requestEntity = new HttpEntity(makeHttpHeaders());
        String url = BASE_URL + "restaurants";
        Restaurant[] response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                Restaurant[].class).getBody();
        this.savedRestaurants = Arrays.asList(response);
    }


    public boolean saveRestaurant(Restaurant restaurant) {
        HttpEntity<Restaurant> requestEntity = new HttpEntity<>(restaurant, makeHttpHeaders());
        String url = BASE_URL + "restaurants";
        Boolean response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                boolean.class).getBody();
        return response;
    }

    public boolean deleteSavedRestaurant(int id) {
        HttpEntity<String> requestEntity = new HttpEntity("", makeHttpHeaders());
        String url = BASE_URL + "restaurants/" + id;
        Boolean response = restTemplate.exchange(
                url, HttpMethod.DELETE,
                requestEntity,
                Boolean.class).getBody();
        return response;
    }


    private HttpHeaders makeHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public void displaySavedRestaurantsIdsAndNames() {
        System.out.println("\n************************");
        System.out.println("ID : Restaurant Name");
        for(Restaurant restaurant : this.savedRestaurants) {
            System.out.println(restaurant.getRestaurantId() + ": " + restaurant.getName());
        }
        System.out.println("************************\n");
    }

    public void displaySavedRestaurantById(int id) {
        for(Restaurant restaurant : this.savedRestaurants) {
            if(restaurant.getRestaurantId() == id) {
                displayRestaurant(restaurant);
                return;
            }
        }
        System.out.println(String.format("Restaurant ID of %d was not found", id));
    }

    public String getSavedRestaurantNameById(int id) {
        for(Restaurant restaurant : this.savedRestaurants) {
            if(restaurant.getRestaurantId() == id) {
                return restaurant.getName();
            }
        }
        System.out.println("Restaurant was not found");
        return null;
    }

    public void displayRestaurant(Restaurant restaurant) {
        System.out.println();
        System.out.println("Name: " + restaurant.getName());
        System.out.println(restaurant.getPrice());
        System.out.println("Rating: " + restaurant.getRating());
        System.out.println(restaurant.getUrl());
        System.out.println(restaurant.getLocation());
        System.out.println();
    }
}
