package ru.topjava.graduate.restaurantvoting.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "meal")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant", "menus"})
public class Meal extends AbstractNamedEntity {
    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 5000)
    private int price;

    @ManyToMany(mappedBy = "meals")
    private List<Menu> menus;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Meal(Integer id, String name, int price, Collection<Menu> menus) {
        super(id, name);
        this.price = price;
        setMenus(menus);
    }

    public Meal(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public void addMenus(Menu... menus) {
        this.menus.addAll(List.of(menus));
    }

    public void setMenus(Collection<Menu> menus) {
        this.menus = CollectionUtils.isEmpty(menus) ? Collections.emptyList() : List.copyOf(menus);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
