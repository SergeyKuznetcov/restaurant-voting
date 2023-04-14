package ru.topjava.graduate.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduate.restaurantvoting.model.Meal;
import ru.topjava.graduate.restaurantvoting.repository.MealRepository;
import ru.topjava.graduate.restaurantvoting.repository.MenuRepository;
import ru.topjava.graduate.restaurantvoting.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class MealService {

    private MealRepository mealRepository;

    @Transactional
    @Modifying
    public void delete(int restaurantId, int id) {
        Meal meal = mealRepository.checkBelong(id, restaurantId);
        meal.getMenus().forEach(menu -> menu.getMeals().removeIf(meal1 -> meal1.id() == id));

        mealRepository.deleteExisted(id);
    }
}
