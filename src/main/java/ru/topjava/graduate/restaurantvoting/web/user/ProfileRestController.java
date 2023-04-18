package ru.topjava.graduate.restaurantvoting.web.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.topjava.graduate.restaurantvoting.model.User;
import ru.topjava.graduate.restaurantvoting.service.UserService;
import ru.topjava.graduate.restaurantvoting.to.UserTo;
import ru.topjava.graduate.restaurantvoting.util.UserUtil;
import ru.topjava.graduate.restaurantvoting.web.AuthUser;

import static ru.topjava.graduate.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@Slf4j
@RequestMapping(ProfileRestController.REST_URL)
@AllArgsConstructor
public class ProfileRestController extends AbstractUserController {

    final static String REST_URL = "/api/profile";

    private final UserService userService;

    @GetMapping
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        return authUser.getUser();
    }

    @DeleteMapping
    @CacheEvict("users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        userService.delete(authUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CachePut(value = "users", key = "#authUser.id()")
    @Transactional
    public void update(@RequestBody @Valid UserTo userTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} with id={}", userTo, authUser.id());
        assureIdConsistent(userTo, authUser.id());
        User user = authUser.getUser();
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }
}
