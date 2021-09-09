package com.nolancarbin.RestaurantFinder.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nolancarbin.RestaurantFinder.model.Restaurant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class YelpService {

    @Value("${yelp.api.url}")
    private String baseApiURL;

    @Value("${yelp.api.token}")
    private String TOKEN;

    private RestTemplate restTemplate;

    public YelpService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Restaurant> getSearchResults(String searchString) {
        List<Restaurant> restaurants = new ArrayList<>();
        String yelpSearchUrl = baseApiURL + searchString;
        HttpEntity<String> requestEntity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(
                yelpSearchUrl, HttpMethod.GET,
                requestEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            JsonNode root = jsonNode.path("businesses");
            for(int i = 0; i < root.size(); i++) {
                JsonNode business = root.path(i);
                String name = business.path("name").asText();
                String url = business.path("url").asText();
                double rating = business.path("rating").asDouble();
                String price = business.path("price").asText();
                JsonNode locations = business.path("location");
                JsonNode displayAddress = locations.path("display_address");
                String location = "";
                for(int j = 0; j < displayAddress.size(); j++) {
                    location += displayAddress.path(j).asText() + " ";
                }
                Restaurant restaurant = new Restaurant(name, location.trim(), rating, price, url);
                restaurants.add(restaurant);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return restaurants;
    }


    private HttpHeaders makeHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);
        return headers;
    }

    public int convertMilesToMeters(int miles) {
        return miles * 1609;
    }
}
