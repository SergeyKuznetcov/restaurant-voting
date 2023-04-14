package ru.topjava.graduate.restaurantvoting.web.vote;

import ru.topjava.graduate.restaurantvoting.model.Meal;
import ru.topjava.graduate.restaurantvoting.model.Menu;
import ru.topjava.graduate.restaurantvoting.model.Role;
import ru.topjava.graduate.restaurantvoting.model.User;
import ru.topjava.graduate.restaurantvoting.web.menu.MenuTestData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class VoteTestData {

    public static final int USER_ID = 1;
    public static final LocalDateTime testDateTime = LocalDateTime.of(2020, 1, 30, 15, 0);
    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "user", Set.of(Role.USER));

    public static final List<Menu> actualMenus = List.of(MenuTestData.menu1, MenuTestData.menu2, MenuTestData.menu3);
    public static final Menu menu1 = new Menu(null, "test menu1", LocalDate.now());
    public static final Menu menu2 = new Menu(null, "test menu2", LocalDate.now());

}
