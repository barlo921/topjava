package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealController {
    private static final Logger log = getLogger(MealRestController.class);

    public void save(String id, LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                dateTime,
                description,
                calories);

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            create(meal);
        } else {
            update(meal);
        }
    }

    private Meal create(Meal meal) {
        return super.create(meal, authUserId());
    }

    private void update(Meal meal) {
        super.update(meal, authUserId());
    }

    public void delete(int id) {
        super.delete(id, authUserId());
    }

    public Meal get(int id) {
        return super.get(id, authUserId());
    }

    public List<MealTo> getAll() {
        return getAll(null, null, null, null);
    }

    public List<MealTo> getAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {

        if (startDate == null) {
            startDate = LocalDate.MIN;
        }

        if (endDate == null) {
            endDate = LocalDate.MAX;
        }

        if (startTime == null) {
            startTime = LocalTime.MIN;
        }

        if (endTime == null) {
            endTime = LocalTime.MAX;
        }

        return super.getAll(authUserId(), startDate, endDate, startTime, endTime);
    }
}