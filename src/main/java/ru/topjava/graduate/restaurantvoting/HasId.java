package ru.topjava.graduate.restaurantvoting;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface HasId {
    public Integer getId();

    void setId(int id);

    @JsonIgnore
    default boolean isNew(){
        return getId() == null;
    }
}
