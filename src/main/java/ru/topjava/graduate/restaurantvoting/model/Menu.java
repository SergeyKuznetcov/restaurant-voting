package ru.topjava.graduate.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends AbstractNamedEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menu_meal",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id"))
    private List<Meal> meals;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<Vote> votes;

    public Menu(Integer id, String name, LocalDate date, Restaurant restaurant, Collection<Meal> meals) {
        super(id, name);
        this.date = date;
        this.restaurant = restaurant;
        setMeals(meals);
    }

    public Menu(Integer id, String name, LocalDate date) {
        super(id, name);
        this.date = date;
    }

    public void addMeals(Meal... meals) {
        this.meals.addAll(List.of(meals));
    }

    public void removeMealById(int id) {
        meals.removeIf(meal -> meal.getId() != null && meal.getId() == id);
    }

    public void setMeals(Collection<Meal> meals) {
        this.meals = CollectionUtils.isEmpty(meals) ? Collections.emptyList() : List.copyOf(meals);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
