package ru.topjava.graduate.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"menus", "meals"})
public class Restaurant extends AbstractNamedEntity {
    @Column(name = "address")
    @NotBlank
    @Size(min = 8, max = 256)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Menu> menus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Meal> meals;

    public Restaurant(Integer id, String name, String address, List<Menu> menus, List<Meal> meals) {
        super(id, name);
        this.address = address;
        setMenus(menus);
        setMeals(meals);
    }

    public Restaurant(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }

    public void setMenus(Collection<Menu> menus) {
        this.menus = CollectionUtils.isEmpty(menus) ? Collections.emptyList() : List.copyOf(menus);
    }

    public void setMeals(Collection<Meal> meals) {
        this.meals = CollectionUtils.isEmpty(meals) ? Collections.emptyList() : List.copyOf(meals);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
