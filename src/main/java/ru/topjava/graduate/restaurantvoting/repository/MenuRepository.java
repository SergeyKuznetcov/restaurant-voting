package ru.topjava.graduate.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduate.restaurantvoting.exception.DataConflictException;
import ru.topjava.graduate.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m JOIN FETCH Meal ml WHERE ml.id=:mealId")
    List<Menu> getAllWithMealsByMealIdAndRestaurantId(int mealId);

    @Query("SELECT m FROM Menu m JOIN FETCH Meal ml WHERE m.id = :menuId AND  m.restaurant.id = :restaurantId")
    Optional<Menu> getWithMeals(int menuId, int restaurantId);

    @Query("SELECT m FROM Menu m JOIN FETCH Vote v WHERE m.id = :menuId")
    Optional<Menu> getWithVotes(int menuId);

    @Query("SELECT m FROM Menu m WHERE m.date = :date")
    List<Menu> findAllWithMealsByDate(LocalDate date);

    List<Menu> findAllByRestaurantId(int restaurantId);

    Optional<Menu> findByIdAndRestaurantId(int menuId, int restaurantId);

    default Menu checkBelong(int menuId, int restaurantId) {
        return findByIdAndRestaurantId(menuId, restaurantId).orElseThrow(
                () -> new DataConflictException("Menu id = " + menuId + "doesn't belong Restaurant id = " + restaurantId));
    }
}
