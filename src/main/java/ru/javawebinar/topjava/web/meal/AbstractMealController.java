package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

abstract class AbstractMealController {

    private static final Logger log = getLogger(AbstractMealController.class);

    @Autowired
    private MealService mealService;

    public Meal create(Meal meal, int userId) {
        log.info("create {}", meal);
        checkNew(meal);
        return mealService.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        mealService.update(meal, id);
    }

    public void delete(int id, int userId) {
        log.info("delete meal with id={}", id);
        mealService.delete(id, userId);
    }

    public Meal get(int id, int userId) {
        log.info("get {}", id);
        return mealService.get(id, userId);
    }

    public List<MealTo> getAll(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getAll {}", userId);
        return mealService.getAll(userId, startDate, endDate, startTime, endTime);
    }

}
