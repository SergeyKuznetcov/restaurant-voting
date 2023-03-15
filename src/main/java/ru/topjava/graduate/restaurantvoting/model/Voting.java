package ru.topjava.graduate.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "voting")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voting extends AbstractBaseEntity{
    @Column(name = "date", nullable = false, columnDefinition = "timestamp")
    @NotNull
    private LocalDate date;

    @ManyToMany
    @JoinTable(
            name = "menu_voting",
            joinColumns = @JoinColumn(name = "voting_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private Set<Menu> menus;

    public Voting(Integer id, LocalDate date, Set<Menu> menus) {
        super(id);
        this.date = date;
    }

    public void setMenus(Set<Menu> menus){
        this.menus = CollectionUtils.isEmpty(menus) ? new HashSet<>() : Set.copyOf(menus);
    }
}
