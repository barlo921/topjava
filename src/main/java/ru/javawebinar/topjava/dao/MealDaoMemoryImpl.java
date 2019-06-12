package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealDaoMemoryImpl implements MealDao {

    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final static List<Meal> mealList = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public MealDaoMemoryImpl() {
        mealList.forEach(meal -> add(meal));
    }

    public static LocalDateTime stringToDateTime(final String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    private static Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public static List<Meal> getMealList() {
        return mealList;
    }

    @Override
    public void add(Meal meal) {
        meal.setId(atomicInteger.getAndIncrement());
        meals.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public void update(Meal meal) {
        Meal oldMeal = get(meal.getId());

        oldMeal.setDateTime(meal.getDateTime());
        oldMeal.setDescription(meal.getDescription());
        oldMeal.setCalories(meal.getCalories());
    }

    @Override
    public List<Meal> getAll() {
        return meals.values().stream().collect(Collectors.toList());
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }
}