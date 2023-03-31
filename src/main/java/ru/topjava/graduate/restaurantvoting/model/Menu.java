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
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_meal",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id"))
    private List<Meal> meals;

    @OneToMany(mappedBy = "menu")
    private List<MenuUsers> users;

    public Menu(Integer id, String name, LocalDate date, Restaurant restaurant, Collection<Meal> meals) {
        super(id, name);
        this.date = date;
        this.restaurant = restaurant;
        setMeals(meals);
    }

    public void setMeals(Collection<Meal> meals) {
        this.meals = CollectionUtils.isEmpty(meals) ? Collections.emptyList() : List.copyOf(meals);
    }
}
