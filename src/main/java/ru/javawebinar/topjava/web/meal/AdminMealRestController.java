package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class AdminMealRestController extends AbstractMealController {

    public Meal create(Meal meal) {
        return super.create(meal, authUserId());
    }

    @Override
    public void update(Meal meal, int id) {
        super.update(meal, id);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public Meal get(int id) {
        return super.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return super.getAll();
    }

    @Override
    public List<Meal> getAll(int userId) {
        return super.getAll(userId);
    }
}