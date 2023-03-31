package ru.topjava.graduate.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Meal extends AbstractNamedEntity {
    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 5000)
    private int price;

    @ManyToMany(mappedBy = "meals")
    @JsonIgnore
    private List<Menu> menus;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Meal(Integer id, String name, int price, Collection<Menu> menus) {
        super(id, name);
        this.price = price;
        setMenus(menus);
    }

    public void setMenus(Collection<Menu> menus) {
        this.menus = CollectionUtils.isEmpty(menus) ? Collections.emptyList() : List.copyOf(menus);
    }
}
