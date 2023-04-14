package ru.topjava.graduate.restaurantvoting.web.menu;

import ru.topjava.graduate.restaurantvoting.model.Menu;
import ru.topjava.graduate.restaurantvoting.util.RestUrlUtil;
import ru.topjava.graduate.restaurantvoting.web.MatcherFactory;
import ru.topjava.graduate.restaurantvoting.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.util.List;

public class MenuTestData {
    public static final String REST_URL_RESTAURANT1_MENU =
            RestUrlUtil.getMenuRestUrlWithRestaurantId(RestaurantTestData.RESTAURANT1_ID);

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "meals", "votes");

    public static final int MENU1_ID = 1;

    public static final Menu menu1 = new Menu(MENU1_ID, "restaurant1 menu1", LocalDate.of(2020, 1, 30));
    public static final Menu menu2 = new Menu(MENU1_ID + 1, "restaurant2 menu1", LocalDate.of(2020, 1, 30));
    public static final Menu menu3 = new Menu(MENU1_ID + 2, "restaurant3 menu1", LocalDate.of(2020, 1, 30));
    public static final Menu menu4 = new Menu(MENU1_ID + 3, "restaurant1 menu2", LocalDate.of(2020, 2, 1));

    public static final List<Menu> restaurant1Menus = List.of(menu1, menu4);

    public static Menu getNew() {
        return new Menu(null, "new menu", LocalDate.of(2023, 1, 20));
    }

    public static Menu getUpdated() {
        return new Menu(MENU1_ID, "updated menu", LocalDate.of(2021, 1, 21));
    }
}
