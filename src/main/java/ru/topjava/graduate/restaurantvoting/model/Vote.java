package ru.topjava.graduate.restaurantvoting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor
public class Vote extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "vote_date_time")
    @CreationTimestamp
    private LocalDateTime dateTime;

    public Vote(Menu menu, User user) {
        this.menu = menu;
        this.user = user;
    }

    public Vote(Integer id, Menu menu, User user, LocalDateTime dateTime) {
        super(id);
        this.menu = menu;
        this.user = user;
        this.dateTime = dateTime;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
