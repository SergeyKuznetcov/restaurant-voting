package ru.topjava.graduate.restaurantvoting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduate.restaurantvoting.model.User;
import ru.topjava.graduate.restaurantvoting.repository.UserRepository;
import ru.topjava.graduate.restaurantvoting.to.UserTo;
import ru.topjava.graduate.restaurantvoting.util.JsonUtil;
import ru.topjava.graduate.restaurantvoting.util.UserUtil;
import ru.topjava.graduate.restaurantvoting.web.AbstractControllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduate.restaurantvoting.web.user.ProfileRestController.REST_URL;
import static ru.topjava.graduate.restaurantvoting.web.user.UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL;
import static ru.topjava.graduate.restaurantvoting.web.user.UserTestData.*;

class ProfileRestControllerTest extends AbstractControllerTest {

    private final String REST_URL_SLASH = REST_URL + "/";

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userRepository.findAll(), admin, guest);
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void update() throws Exception {
        UserTo updatedTo = new UserTo(USER_ID, "newName", USER_EMAIL, "newPassword");
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userRepository.getExisted(USER_ID), UserUtil.updateFromTo(new User(user.id(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles()), updatedTo));
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "password", null);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void updateDuplicate() throws Exception {
        UserTo updatedTo = new UserTo(USER_ID, "newName", ADMIN_EMAIL, "newPassword");
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_EMAIL)));
    }
}