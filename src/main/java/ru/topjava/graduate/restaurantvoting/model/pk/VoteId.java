package ru.topjava.graduate.restaurantvoting.model.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteId implements Serializable {
    @Column(name = "menu_id")
    private Integer menu_id;
    @Column(name = "user_id")
    private Integer user_id;
}
