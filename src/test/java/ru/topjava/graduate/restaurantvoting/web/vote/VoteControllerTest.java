package ru.topjava.graduate.restaurantvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduate.restaurantvoting.model.Menu;
import ru.topjava.graduate.restaurantvoting.model.Vote;
import ru.topjava.graduate.restaurantvoting.repository.MenuRepository;
import ru.topjava.graduate.restaurantvoting.repository.VoteRepository;
import ru.topjava.graduate.restaurantvoting.service.MenuService;
import ru.topjava.graduate.restaurantvoting.service.VoteService;
import ru.topjava.graduate.restaurantvoting.util.JsonUtil;
import ru.topjava.graduate.restaurantvoting.web.AbstractControllerTest;
import ru.topjava.graduate.restaurantvoting.web.menu.MenuAdminController;
import ru.topjava.graduate.restaurantvoting.web.menu.MenuTestData;
import ru.topjava.graduate.restaurantvoting.web.restaurant.RestaurantTestData;
import ru.topjava.graduate.restaurantvoting.web.user.UserTestData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduate.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_MATCHER;
import static ru.topjava.graduate.restaurantvoting.web.restaurant.RestaurantTestData.restaurants;
import static ru.topjava.graduate.restaurantvoting.web.vote.VoteController.REST_URL;
import static ru.topjava.graduate.restaurantvoting.web.vote.VoteTestData.actualMenus;

class VoteControllerTest extends AbstractControllerTest {

    private final String REST_URL_SLASH = REST_URL + "/";

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private VoteService voteService;
    @Autowired
    private MenuAdminController menuAdminController;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRepository menuRepository;


    @Test
    @WithUserDetails(value = UserTestData.USER_EMAIL)
    void getAllActualMenus() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("localDateTime", VoteTestData.testDateTime.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(actualMenus));;

    }

    @Test
    @WithUserDetails(value = UserTestData.USER_EMAIL)
    void getActualVotedMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/getVote")
                .param("localDateTime", VoteTestData.testDateTime.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(MenuTestData.menu1));
    }
}