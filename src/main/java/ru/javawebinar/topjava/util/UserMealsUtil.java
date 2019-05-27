package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //Filter UserMeals for preset time
        List<UserMeal> filteredMealList = new ArrayList<>(mealList);
        filteredMealList.removeIf(userMeal -> !TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime));

        List<UserMealWithExceed> mealWithExceeds = new ArrayList<>();

        for (UserMeal userMeal :
                filteredMealList) {
            /*
                First filter meals for specified date
                Then get just calories
                Sum calories
             */
            boolean exceed = false;
            int calories = mealList.stream().filter(meal -> userMeal.getDateTime().toLocalDate().equals(meal.getDateTime().toLocalDate()))
                    .map(UserMeal::getCalories)
                    .mapToInt(Integer::intValue)
                    .sum();

            if (calories>caloriesPerDay)
                exceed=true;

            UserMealWithExceed userMealWithExceed;
            userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed);
            mealWithExceeds.add(userMealWithExceed);
        }
        return mealWithExceeds;
    }
}
