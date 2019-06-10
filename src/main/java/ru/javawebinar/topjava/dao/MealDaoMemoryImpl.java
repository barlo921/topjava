package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealMemoryService;

import java.util.List;

public class MealDaoMemoryImpl implements MealDao {

    private List<Meal> meals;

    public MealDaoMemoryImpl() {
        meals = MealMemoryService.getMealList();
    }

    @Override
    public void add(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void delete(int id) {
        int i = 0;
        for (Meal meal :
                meals) {
            if (meal.getId() == id) {
                meals.remove(i);
            }
            i++;
        }
    }

    @Override
    public void clear() {
        meals.clear();
    }

    @Override
    public void update(Meal meal) {
        Meal oldMeal = meals.get(meal.getId());

        oldMeal.setDateTime(meal.getDateTime());
        oldMeal.setDescription(meal.getDescription());
        oldMeal.setCalories(meal.getCalories());
    }

    @Override
    public List<Meal> get() {
        return meals;
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }
}