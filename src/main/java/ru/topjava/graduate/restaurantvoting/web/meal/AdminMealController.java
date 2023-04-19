package ru.topjava.graduate.restaurantvoting.web.meal;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.graduate.restaurantvoting.model.Meal;
import ru.topjava.graduate.restaurantvoting.repository.MealRepository;
import ru.topjava.graduate.restaurantvoting.repository.RestaurantRepository;
import ru.topjava.graduate.restaurantvoting.service.MealService;
import ru.topjava.graduate.restaurantvoting.util.RestUrlUtil;

import java.net.URI;
import java.util.List;

import static ru.topjava.graduate.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.topjava.graduate.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@RequestMapping(AdminMealController.REST_URL)
@AllArgsConstructor
public class AdminMealController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/meals";

    private final MealService mealService;

    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Meal> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get Meal id = {}, from Restaurant id = {}", id, restaurantId);
        return ResponseEntity.of(mealRepository.findByIdAndRestaurantId(id, restaurantId));
    }

    @GetMapping
    public List<Meal> getAllByRestaurantId(@PathVariable int restaurantId) {
        log.info("get all meals for Restaurant id = {}", restaurantId);
        return mealRepository.findAllByRestaurantId(restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete Meal id = {} from Restaurant id {}", id, restaurantId);
        mealService.delete(restaurantId, id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Meal meal, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update Meal id = {} from Restaurant id = {}", id, restaurantId);
        assureIdConsistent(meal, id);
        mealRepository.getExisted(id);
        meal.setRestaurant(restaurantRepository.getExisted(restaurantId));
        mealRepository.save(meal);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> saveWithLocation(@PathVariable int restaurantId, @Valid @RequestBody Meal meal) {
        log.info("create new Meal {} for Restaurant id = {}", meal, restaurantId);
        meal.setRestaurant(restaurantRepository.getExisted(restaurantId));
        checkNew(meal);
        Meal created = mealRepository.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(RestUrlUtil.getMealRestUrlWithRestaurantId(restaurantId) + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
