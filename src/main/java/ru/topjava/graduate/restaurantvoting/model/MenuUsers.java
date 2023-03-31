package ru.topjava.graduate.restaurantvoting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.topjava.graduate.restaurantvoting.model.pk.MenuUsersId;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuUsers {
    @EmbeddedId
    private MenuUsersId menuUsersId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("menuId")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @Column(name = "vote_date_time")
    @CreationTimestamp
    private LocalDateTime dateTime;
}
