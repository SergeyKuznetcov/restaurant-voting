package ru.topjava.graduate.restaurantvoting.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduate.restaurantvoting.model.Vote;
import ru.topjava.graduate.restaurantvoting.repository.MenuRepository;
import ru.topjava.graduate.restaurantvoting.repository.VoteRepository;
import ru.topjava.graduate.restaurantvoting.service.MenuService;
import ru.topjava.graduate.restaurantvoting.service.VoteService;
import ru.topjava.graduate.restaurantvoting.web.AbstractControllerTest;
import ru.topjava.graduate.restaurantvoting.web.menu.MenuAdminController;
import ru.topjava.graduate.restaurantvoting.web.menu.MenuTestData;
import ru.topjava.graduate.restaurantvoting.web.user.UserTestData;

import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduate.restaurantvoting.web.vote.VoteController.REST_URL;
import static ru.topjava.graduate.restaurantvoting.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {

    private final String REST_URL_SLASH = REST_URL + "/";

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private VoteController voteController;
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
                .param("localDateTime", VoteTestData.testDateTime1.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(actualMenus));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_EMAIL)
    void getActualVotedMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/getVote")
                .param("localDateTime", VoteTestData.testDateTime1.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(MenuTestData.menu1));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_EMAIL)
    void vote() throws Exception {
        Vote vote = voteRepository.getByMenuIdAndUserId(NEW_VOTED_MENU_ID, USER_ID).orElse(null);
        Assertions.assertNull(vote);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + NEW_VOTED_MENU_ID)
                .param("localDateTime", testDateTime2.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(status().isNoContent());
        vote = voteRepository.getByMenuIdAndUserId(NEW_VOTED_MENU_ID, USER_ID).orElse(null);
        Assertions.assertNotNull(vote);
        Assertions.assertEquals(NEW_VOTED_MENU_ID, vote.getMenu().id());
        Assertions.assertEquals(USER_ID, vote.getUser().id());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_EMAIL)
    void update() throws Exception {
        Vote vote = voteRepository.getByMenuIdAndUserId(OLD_VOTED_MENU_ID, USER_ID).orElse(null);
        Assertions.assertNotNull(vote);
        Assertions.assertEquals(OLD_VOTED_MENU_ID, vote.getMenu().id());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + UPDATED_VOTED_MENU_ID)
                .param("localDateTime", testDateTime1.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(status().isNoContent());
        vote = voteRepository.getByMenuIdAndUserId(UPDATED_VOTED_MENU_ID, USER_ID).orElse(null);
        Assertions.assertNotNull(vote);
        Assertions.assertEquals(UPDATED_VOTED_MENU_ID, vote.getMenu().id());
        Assertions.assertEquals(USER_ID, vote.getUser().id());
    }
}