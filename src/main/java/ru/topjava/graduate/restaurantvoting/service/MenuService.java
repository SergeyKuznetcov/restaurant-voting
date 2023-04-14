package ru.topjava.graduate.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduate.restaurantvoting.model.Meal;
import ru.topjava.graduate.restaurantvoting.model.Menu;
import ru.topjava.graduate.restaurantvoting.model.Vote;
import ru.topjava.graduate.restaurantvoting.repository.MealRepository;
import ru.topjava.graduate.restaurantvoting.repository.MenuRepository;
import ru.topjava.graduate.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.topjava.graduate.restaurantvoting.util.validation.ValidationUtil.checkExisted;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;
    private final VoteService voteService;
    private final VoteRepository voteRepository;

    @Transactional
    public Optional<Menu> getVotedMenuByDateTimeAndUserId(LocalDateTime localDateTime, int userId){
        List<Vote> userVotes = voteRepository.getAllByUserId(userId);
        Vote vote = userVotes.stream()
                .filter(vote1 -> vote1.getDateTime().toLocalDate().equals(localDateTime.toLocalDate()))
                .findFirst().orElse(null);
        if (vote == null)
            return Optional.empty();
        return menuRepository.findById(vote.getMenu().id());
    }

    public List<Menu> getAllByDateTime(LocalDateTime localDateTime) {
        LocalDate currDate = localDateTime.toLocalDate();
        if (localDateTime.getHour() < 11) {
            currDate = currDate.minusDays(1);
        }
        return menuRepository.findAllWithMealsByDate(currDate);
    }


    @Transactional
    @Modifying
    public void delete(int id, int restaurantId) {
        Menu menu = menuRepository.checkBelong(id, restaurantId);
        menu.getMeals().forEach(meal -> meal.getMenus().removeIf(menu1 -> menu1.id() == id));

        voteService.deleteAllByMenuId(id);
        menuRepository.deleteExisted(id);
    }

    @Transactional
    @Modifying
    public void deleteMealFromMenu(int mealId, int menuId, int restaurantId) {
        Menu menu = checkExisted(menuRepository.getWithMeals(menuId, restaurantId).orElse(null), menuId);
        Meal meal = checkExisted(mealRepository.getWithMenus(mealId, restaurantId).orElse(null), mealId);
        menu.getMeals().removeIf(meal1 -> meal1.id() == mealId);
        meal.getMenus().removeIf(menu1 -> menu1.id() == menuId);
    }

    @Transactional
    @Modifying
    public void addMealToMenu(int mealId, int menuId, int restaurantId) {
        Menu menu = checkExisted(menuRepository.getWithMeals(menuId, restaurantId).orElse(null), menuId);
        Meal meal = checkExisted(mealRepository.getWithMenus(mealId, restaurantId).orElse(null), mealId);
        menu.addMeals(meal);
        meal.addMenus(menu);
    }
}
