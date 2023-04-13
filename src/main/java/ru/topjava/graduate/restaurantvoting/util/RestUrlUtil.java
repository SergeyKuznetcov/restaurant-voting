package ru.topjava.graduate.restaurantvoting.util;

public class RestUrlUtil {
    public static final String RESTAURANT_REST_URL = "/api/admin/restaurants";

    public static String getRestaurantRestUrlWithId(int id){
        return RESTAURANT_REST_URL + "/" + id;
    }

    public static String getMealRestUrlWithRestaurantId(int id){
        return getRestaurantRestUrlWithId(id) + "/meals";
    }

    public static String getMenuRestUrlWithRestaurantId(int id){
        return getRestaurantRestUrlWithId(id) + "/menus";
    }
}
