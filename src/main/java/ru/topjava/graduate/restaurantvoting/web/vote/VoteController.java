package ru.topjava.graduate.restaurantvoting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.topjava.graduate.restaurantvoting.model.Menu;
import ru.topjava.graduate.restaurantvoting.repository.MenuRepository;
import ru.topjava.graduate.restaurantvoting.repository.UserRepository;
import ru.topjava.graduate.restaurantvoting.service.MenuService;
import ru.topjava.graduate.restaurantvoting.service.VoteService;
import ru.topjava.graduate.restaurantvoting.util.DateTimeUtil;
import ru.topjava.graduate.restaurantvoting.web.AuthUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.topjava.graduate.restaurantvoting.util.DateTimeUtil.checkDateTime;

@RestController
@Slf4j
@RequestMapping(VoteController.REST_URL)
@AllArgsConstructor
public class VoteController {

    public static final String REST_URL = "/api/voting";

    private final VoteService voteService;
    private final MenuService menuService;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @GetMapping
    public List<Menu> getAllWithMealsActual(
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime) {
        log.info("get actual Menus");
        return menuService.getAllByDateTime(checkDateTime(localDateTime));
    }

    @GetMapping("/getVote")
    public ResponseEntity<Menu> getVotedMenu(@AuthenticationPrincipal AuthUser authUser,
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime) {
        log.info("get voted Menu by dateTime {}", checkDateTime(localDateTime).format(DateTimeFormatter.ISO_DATE_TIME));
        return ResponseEntity.of(menuService.getVotedMenuByDateTimeAndUserId(checkDateTime(localDateTime), authUser.id()));
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int menuId) {
        log.info("User id = {} vote for Menu id = {}", menuId, authUser.id());
        voteService.vote(menuId, authUser.getUser());
    }

    @PostMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable int menuId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("User id = {} votes for Menu id = {}", authUser.id(), menuId);
        voteService.vote(menuId, authUser.getUser());
    }
}
