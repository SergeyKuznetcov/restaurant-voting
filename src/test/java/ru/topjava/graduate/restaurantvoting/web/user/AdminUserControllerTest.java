package ru.topjava.graduate.restaurantvoting.web.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduate.restaurantvoting.model.User;
import ru.topjava.graduate.restaurantvoting.repository.UserRepository;
import ru.topjava.graduate.restaurantvoting.util.JsonUtil;
import ru.topjava.graduate.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduate.restaurantvoting.web.user.AdminUserController.REST_URL;
import static ru.topjava.graduate.restaurantvoting.web.user.UserTestData.*;

class AdminUserControllerTest extends AbstractControllerTest {

    private final String REST_URL_SLASH = REST_URL + "/";

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_EMAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + UserTestData.USER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_EMAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(users));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_EMAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + GUEST_ID))
                .andExpect(status().isNoContent());
        Assertions.assertNull(userRepository.get(GUEST_ID));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_EMAIL)
    void createWithLocation() throws Exception {
        User newUser = getNew();
        System.out.println(newUser);
        System.out.println(jsonWithPassword(newUser, "634563456"));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, "newPass"))
                .content(JsonUtil.writeValue(newUser)));

        User created = USER_MATCHER.readFromJson(action);
        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userRepository.getExisted(newId), newUser);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_EMAIL)
    void update() throws Exception {
        User updated = UserTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, "newPass")))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userRepository.getExisted(USER_ID), getUpdated());
    }
}