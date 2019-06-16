package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

abstract class AbstractMealController {

    private static final Logger log = getLogger(AbstractMealController.class);

    @Autowired
    private MealService service;

    public Meal create(Meal meal, int userId){
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, id);
    }

    public void delete(int id){
        log.info("delete meal with id={}", id);
        service.delete(id);
    }

    public Meal get(int id){
        log.info("get {}", id);
        return service.get(id);
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll().stream()
                .sorted(this::compare)
                .collect(Collectors.toList());
    }

    public List<Meal> getAll(int userId) {
        log.info("getAll {}", userId);
        return service.getAll(userId).stream()
                .sorted(this::compare)
                .collect(Collectors.toList());
    }

    private int compare(Meal m1, Meal m2) {
        if (m1.getDateTime().compareTo(m2.getDateTime()) > 0) {
            return -1;
        } else if (m1.getDateTime().compareTo(m2.getDateTime()) < 0) {
            return 1;
        } else return 0;
    }

}
