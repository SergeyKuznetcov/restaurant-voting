package ru.topjava.graduate.restaurantvoting.web.user;

import ru.topjava.graduate.restaurantvoting.model.Role;
import ru.topjava.graduate.restaurantvoting.model.User;
import ru.topjava.graduate.restaurantvoting.util.JsonUtil;
import ru.topjava.graduate.restaurantvoting.web.MatcherFactory;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(User.class, "password", "votes");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;

    public static final String USER_EMAIL = "user@yandex.ru";
    public static final String ADMIN_EMAIL = "admin@gmail.com";
    public static final User user = new User(USER_ID, "User", USER_EMAIL, "user", Set.of(Role.USER));
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_EMAIL, "admin", Set.of(Role.USER, Role.ADMIN));
    public static final User guest = new User(GUEST_ID, "Guest", "guest@gmail.com", "guest", null);

    public static final List<User> users = List.of(admin, guest, user);

    public static User getNew() {
        return new User(null, "new user", "new@mail.ru", "newPass", EnumSet.of(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "updated", USER_EMAIL, "newPass", EnumSet.of(Role.USER));
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}
