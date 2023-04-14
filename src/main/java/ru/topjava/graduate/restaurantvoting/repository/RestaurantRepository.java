package ru.topjava.graduate.restaurantvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduate.restaurantvoting.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

}
