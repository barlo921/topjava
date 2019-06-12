package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Map;

public interface MealDao {
    void add(Meal meal);

    void delete(int id);

    void update(Meal meal);

    List<Meal> getAll();

    Meal get(int id);
}
