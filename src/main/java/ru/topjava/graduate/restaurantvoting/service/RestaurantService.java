package ru.topjava.graduate.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduate.restaurantvoting.model.Menu;
import ru.topjava.graduate.restaurantvoting.repository.MealRepository;
import ru.topjava.graduate.restaurantvoting.repository.MenuRepository;
import ru.topjava.graduate.restaurantvoting.repository.RestaurantRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final MenuService menuService;

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;

    @Transactional
    @Modifying
    public void delete(int restaurantId) {
        restaurantRepository.getExisted(restaurantId);
        List<Menu> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menus.forEach(menu -> menuService.delete(menu.id(), restaurantId));
        mealRepository.deleteAllByRestaurantId(restaurantId);
        restaurantRepository.deleteExisted(restaurantId);
    }
}
