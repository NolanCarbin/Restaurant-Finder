package com.nolancarbin.RestaurantFinder.dao;

import com.nolancarbin.RestaurantFinder.model.Restaurant;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantJdbcDao implements RestaurantDao{

    private JdbcTemplate jdbcTemplate;

    //Prices
    private static final String PRICE_1 = "$";
    private static final String PRICE_2 = "$$";
    private static final String PRICE_3 = "$$$";
    private static final String PRICE_4 = "$$$$";


    public RestaurantJdbcDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Restaurant> getAllSavedRestaurants() throws DataAccessException{
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT restaurant_id, name, location, rating, price_id, url FROM restaurant";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Restaurant restaurant = mapRowSetToRestaurant(results);
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    @Override
    public boolean saveRestaurant(Restaurant restaurant) throws DataAccessException {
        String sql = "INSERT INTO restaurant (name, location, rating, price_id, url) VALUES(?, ?, ?, ?, ?)";
        int attempted = jdbcTemplate.update(sql, restaurant.getName(), restaurant.getLocation(), restaurant.getRating(),
                makePriceId(restaurant.getPrice()), restaurant.getUrl());
        if(attempted == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        return null;
    }

    @Override
    public Restaurant getRestaurantByName(String name) {
        return null;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant updatedRestaurant, int id) {
        return null;
    }

    @Override
    public boolean deleteRestaurant(int restaurantId) throws DataAccessException {
        String sql = "DELETE FROM restaurant WHERE restaurant_id = ?";
        int attempted = jdbcTemplate.update(sql, restaurantId);
        if(attempted == 1) {
            return true;
        }
        return false;
    }

    private Restaurant mapRowSetToRestaurant(SqlRowSet rowSet) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(rowSet.getInt("restaurant_id"));
        restaurant.setName(rowSet.getString("name"));
        restaurant.setLocation(rowSet.getString("location"));
        restaurant.setRating(rowSet.getDouble("rating"));
        restaurant.setPrice(makePriceString(rowSet.getInt("price_id")));
        restaurant.setUrl(rowSet.getString("url"));
        return restaurant;
    }

    private String makePriceString(int priceId) {
        if(priceId == 1) {
            return PRICE_1;
        } else if (priceId == 2) {
            return PRICE_2;
        } else if (priceId == 3) {
            return PRICE_3;
        } else {
            return PRICE_4;
        }
    }

    private int makePriceId(String priceString) {
        if(priceString.equalsIgnoreCase(PRICE_1)) {
            return 1;
        } else if (priceString.equalsIgnoreCase(PRICE_2)) {
            return 2;
        } else if (priceString.equalsIgnoreCase(PRICE_3)) {
            return 3;
        } else {
            return 4;
        }
    }
}
