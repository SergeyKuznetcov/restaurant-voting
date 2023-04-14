package ru.topjava.graduate.restaurantvoting.web.restaurant;

import ru.topjava.graduate.restaurantvoting.model.Restaurant;
import ru.topjava.graduate.restaurantvoting.web.MatcherFactory;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "meals", "menus");

    public static final int RESTAURANT1_ID = 1;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "restaurant1", "restaurant1 address");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "restaurant2", "restaurant2 address");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "restaurant3", "restaurant3 address");

    public static final List<Restaurant> restaurants =List.of(restaurant1, restaurant2, restaurant3);

    public static Restaurant getNew(){
        return new Restaurant(null, "new restaurant", "new restaurant address");
    }

    public static Restaurant getUpdated(){
        return new Restaurant(RESTAURANT1_ID, "updated name", "updated address");
    }
}
