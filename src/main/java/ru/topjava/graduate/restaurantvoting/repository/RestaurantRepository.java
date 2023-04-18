package ru.topjava.graduate.restaurantvoting.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduate.restaurantvoting.model.Restaurant;

import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Cacheable("restaurants")
    Optional<Restaurant> findById(int id);
}
