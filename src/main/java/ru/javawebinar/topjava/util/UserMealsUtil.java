package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //Sum calories for every day
        Map<LocalDate, Integer> daysCalories = new HashMap<>();
        mealList.forEach(userMeal -> daysCalories.merge(userMeal.getDate(), userMeal.getCalories(), Integer::sum));

        //If UserMeal time is between specified time then add new UserMealWithExceed with UserMeal parameters
        List<UserMealWithExceed> mealListWithExceeds = new ArrayList<>();
        mealList.forEach(userMeal -> {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                mealListWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        daysCalories.get(userMeal.getDate()) > caloriesPerDay));
        });
        return mealListWithExceeds;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOptional(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        //Group days and sum calories
        Map<LocalDate, Integer> daysCalories = mealList.stream().collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

        /*
            First filter meals for specified date
            Then create new UserMealWithExceed object with date of filtered UserMeal
            Get sum of day calories from Map. Set exceed value.
            Collect to List
         */
        return mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        daysCalories.get(userMeal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOptional2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, UserMealWithExceed> filteredMealMap = new HashMap<>();//Filtered map with objects of preset time
        Map<LocalDate, Integer> daysCalories = new HashMap<>();//Sum of calories for every day


        /*
            First store calories for the day. If it's not first eating then just sum calories
            Then if current UserMeal is satisfies preset time, create new UserMealWithExceed and put it in Map where LocalDate is a key.
            Exceed is false by default.
            If current UserMeal day calories more than caloriesPerDay, set exceed true for that day.
            Null check for non-sorted list. For cases when there is no objects for this date and preset time yet.
         */
        mealList.forEach(userMeal -> {
            daysCalories.merge(userMeal.getDate(), userMeal.getCalories(), Integer::sum);

            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredMealMap.put(userMeal.getDate(),
                        new UserMealWithExceed(
                                userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                false));
            }
            if (filteredMealMap.get(userMeal.getDate()) != null) {
                filteredMealMap.get(userMeal.getDate()).setExceed(daysCalories.get(userMeal.getDate()) > caloriesPerDay);
            }
        });
        return new ArrayList<>(filteredMealMap.values());
    }
}
