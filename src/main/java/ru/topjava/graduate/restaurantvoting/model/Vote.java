package ru.topjava.graduate.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends AbstractBaseEntity {
    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private LocalDateTime dateTime;
    @OneToOne
    @JoinColumn(name = "voting_id")
    private Voting voting;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public Vote(Integer id, LocalDateTime dateTime, User user, Menu menu) {
        super(id);
        this.dateTime = dateTime;
        this.user = user;
        this.menu = menu;
    }
}
