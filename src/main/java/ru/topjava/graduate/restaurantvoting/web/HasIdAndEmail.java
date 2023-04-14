package ru.topjava.graduate.restaurantvoting.web;

import ru.topjava.graduate.restaurantvoting.HasId;

public interface HasIdAndEmail extends HasId {
    String getEmail();
}