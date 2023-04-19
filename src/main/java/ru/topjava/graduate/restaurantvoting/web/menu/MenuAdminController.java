package ru.topjava.graduate.restaurantvoting.web.menu;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.graduate.restaurantvoting.model.Meal;
import ru.topjava.graduate.restaurantvoting.model.Menu;
import ru.topjava.graduate.restaurantvoting.repository.MealRepository;
import ru.topjava.graduate.restaurantvoting.repository.MenuRepository;
import ru.topjava.graduate.restaurantvoting.repository.RestaurantRepository;
import ru.topjava.graduate.restaurantvoting.service.MenuService;
import ru.topjava.graduate.restaurantvoting.util.RestUrlUtil;

import java.net.URI;
import java.util.List;

import static ru.topjava.graduate.restaurantvoting.util.validation.ValidationUtil.*;

@RestController
@Slf4j
@RequestMapping(MenuAdminController.REST_URL)
@AllArgsConstructor
public class MenuAdminController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menus";

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;
    private final MenuService menuService;

    @GetMapping
    public List<Menu> getAllByRestaurantId(@PathVariable int restaurantId) {
        log.info("get all Menu for Restaurant id = {}", restaurantId);
        return menuRepository.findAllByRestaurantId(restaurantId);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<Menu> get(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("get Menu id = {} for Restaurant id = {}", menuId, restaurantId);
        return ResponseEntity.of(checkExisted(menuRepository.findByIdAndRestaurantId(menuId, restaurantId), menuId));
    }

    @GetMapping("/{menuId}/meals")
    public List<Meal> getAllByMenuId(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("get all Meals for Menu id = {} for Restaurant id = {}", menuId, restaurantId);
        return mealRepository.findAllByMenusId(menuId);
    }

    @PatchMapping("/{menuId}/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addMealToMenu(@PathVariable int restaurantId, @PathVariable int menuId, @PathVariable int id) {
        log.info("add Meal id = {} to Menu id = {} for Restaurant id = {}", id, menuId, restaurantId);
        menuService.addMealToMenu(id, menuId, restaurantId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @Valid @RequestBody Menu menu, @PathVariable int id) {
        log.info("update Menu id = {}", id);
        menuRepository.getExisted(id);
        assureIdConsistent(menu, id);
        menu.setRestaurant(restaurantRepository.getExisted(restaurantId));
        menuRepository.save(menu);
    }

    @DeleteMapping("/{menuId}/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMealFromMenu(@PathVariable int restaurantId, @PathVariable int menuId, @PathVariable int id) {
        log.info("delete Meal id = {} from Menu id = {} for Restaurant id = {}", id, menuId, restaurantId);
        menuService.deleteMealFromMenu(id, menuId, restaurantId);
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("delete Menu id = {} for Restaurant id = {}", menuId, restaurantId);
        menuService.delete(menuId, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> saveWithLocation(@PathVariable int restaurantId, @Valid @RequestBody Menu menu) {
        log.info("create Menu for Restaurant id = {}", restaurantId);
        checkNew(menu);
        menu.setRestaurant(restaurantRepository.getExisted(restaurantId));
        Menu created = menuRepository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(RestUrlUtil.getMenuRestUrlWithRestaurantId(restaurantId) + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
