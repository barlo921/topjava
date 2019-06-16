package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealController {

    public Meal create(Meal meal) {
        return super.create(meal, authUserId());
    }

    public void update(Meal meal) {
        super.update(meal, authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public Meal get() {
        return super.get(authUserId());
    }

    public List<Meal> getAll() {
        return super.getAll(authUserId());
    }
}