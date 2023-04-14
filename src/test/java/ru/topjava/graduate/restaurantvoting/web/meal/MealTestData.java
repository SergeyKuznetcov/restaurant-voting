package ru.topjava.graduate.restaurantvoting.web.meal;

import ru.topjava.graduate.restaurantvoting.model.Meal;
import ru.topjava.graduate.restaurantvoting.util.RestUrlUtil;
import ru.topjava.graduate.restaurantvoting.web.MatcherFactory;
import ru.topjava.graduate.restaurantvoting.web.restaurant.RestaurantTestData;

import java.util.List;

public class MealTestData {

    public static final String REST_URL_RESTAURANT1_MEAL = RestUrlUtil.getMealRestUrlWithRestaurantId(RestaurantTestData.RESTAURANT1_ID);

    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class, "restaurant", "menus");

    public static final int MEAL1_ID = 1;

    public static final Meal meal1 = new Meal(MEAL1_ID, "restaurant1meal1", 100);
    public static final Meal meal2 = new Meal(MEAL1_ID + 1, "restaurant1meal2", 200);
    public static final Meal meal3 = new Meal(MEAL1_ID + 2, "restaurant2meal1", 300);
    public static final Meal meal4 = new Meal(MEAL1_ID + 3, "restaurant2meal2", 400);
    public static final Meal meal5 = new Meal(MEAL1_ID + 4, "restaurant3meal1", 500);
    public static final Meal meal6 = new Meal(MEAL1_ID + 5, "restaurant3meal2", 600);

    public static final List<Meal> restaurant1Meals = List.of(meal1, meal2);

    public static Meal getNew(){
        return new Meal(null, "newMeal", 666);
    }

    public static Meal getUpdated(){
        return new Meal(MEAL1_ID, "updated meal", 999);
    }
}
