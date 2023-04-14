package ru.topjava.graduate.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduate.restaurantvoting.exception.DataConflictException;
import ru.topjava.graduate.restaurantvoting.model.Meal;
import ru.topjava.graduate.restaurantvoting.service.MealService;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

    @Query("SELECT ml FROM Meal ml JOIN FETCH Menu m WHERE m.id = :menuId")
    List<Meal> getAllWithMenusByMenuId(int menuId);

    @Query("SELECT ml FROM Meal ml JOIN FETCH Menu m WHERE ml.restaurant.id = :restaurantId AND ml.id=:mealId")
    Optional<Meal> getWithMenus(int mealId, int restaurantId);

    @Query("SELECT ml FROM Meal ml WHERE ml.id = :mealId AND ml.restaurant.id = :restaurantId")
    Optional<Meal> findByIdAndRestaurantId(int mealId, int restaurantId);

    List<Meal> findAllByRestaurantId(int restaurantId);

    void deleteAllByRestaurantId(int restaurantId);

    List<Meal> findAllByMenusId(int menuId);

    default Meal checkBelong(int mealId, int restaurantId){
        return findByIdAndRestaurantId(mealId, restaurantId).orElseThrow(
                ()->new DataConflictException("Meal with id = " + mealId + " doesn't belong to Restaurant with id = " + restaurantId));
    }
}
