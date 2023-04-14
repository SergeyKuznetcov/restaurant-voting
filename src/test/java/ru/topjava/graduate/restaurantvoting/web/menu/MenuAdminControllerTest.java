package ru.topjava.graduate.restaurantvoting.web.menu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduate.restaurantvoting.model.Menu;
import ru.topjava.graduate.restaurantvoting.repository.MenuRepository;
import ru.topjava.graduate.restaurantvoting.util.JsonUtil;
import ru.topjava.graduate.restaurantvoting.web.AbstractControllerTest;
import ru.topjava.graduate.restaurantvoting.web.user.UserTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduate.restaurantvoting.web.menu.MenuTestData.*;

class MenuAdminControllerTest extends AbstractControllerTest {

    private final String REST_URL_RESTAURANT1_SLASH = REST_URL_RESTAURANT1_MENU + "/";

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_EMAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT1_SLASH + MenuTestData.MENU1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_EMAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT1_MENU))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(restaurant1Menus));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_EMAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_RESTAURANT1_SLASH + MENU1_ID))
                .andExpect(status().isNoContent());
        Assertions.assertNull(menuRepository.get(MENU1_ID));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_EMAIL)
    void createWithLocation() throws Exception {
        Menu newMenu = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT1_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)));

        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuRepository.getExisted(newId), newMenu);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_EMAIL)
    void update() throws Exception {
        Menu updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT1_SLASH + MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(menuRepository.getExisted(MENU1_ID), updated);
    }
}