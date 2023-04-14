package ru.topjava.graduate.restaurantvoting.to;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.topjava.graduate.restaurantvoting.HasId;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public abstract class BaseTo implements HasId {
    protected Integer id;

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}
