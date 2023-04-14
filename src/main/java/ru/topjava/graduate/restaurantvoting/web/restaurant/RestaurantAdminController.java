package ru.topjava.graduate.restaurantvoting.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.graduate.restaurantvoting.model.Restaurant;
import ru.topjava.graduate.restaurantvoting.repository.RestaurantRepository;
import ru.topjava.graduate.restaurantvoting.service.RestaurantService;

import java.net.URI;
import java.util.List;

import static ru.topjava.graduate.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.topjava.graduate.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@RequestMapping(value = RestaurantAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RestaurantAdminController {
    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantService restaurantService;

    private final RestaurantRepository restaurantRepository;

    @GetMapping
    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        log.info("get all Restaurants");
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @GetMapping("/{id}")
    @Cacheable("restaurants")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get Restaurant id = {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "restaurants")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete Restaurant id = {}", id);
        restaurantService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(value = "restaurants", key = "#restaurant.name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        log.info("update Restaurant id = {}", id);
        restaurantRepository.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(value = "restaurants", key = "#restaurant.name")
    public ResponseEntity<Restaurant> saveWithLocation(@RequestBody Restaurant restaurant) {
        log.info("create new Restaurant");
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
