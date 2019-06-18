package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository mealRepository;
    private UserRepository userRepository;

    @Autowired
    public MealServiceImpl(MealRepository mealRepository, UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    public Meal create(Meal meal, int userId) {
        return mealRepository.save(meal, userId);
    }

    public void update(Meal meal, int userId) throws NotFoundException {
        checkNotFoundWithId(mealRepository.save(meal, userId), meal.getId());
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(mealRepository.delete(id, userId), id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(mealRepository.get(id, userId), id);
    }

    @Override
    public List<MealTo> getAll(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getFilteredWithExcess(mealRepository.getAll(userId, startDate, endDate), userRepository.get(userId).getCaloriesPerDay(), startTime, endTime).stream()
                .sorted(this::compare)
                .collect(Collectors.toList());
    }

    private int compare(MealTo m1, MealTo m2) {
        if (m1.getDateTime().compareTo(m2.getDateTime()) > 0) {
            return -1;
        } else if (m1.getDateTime().compareTo(m2.getDateTime()) < 0) {
            return 1;
        } else return 0;
    }
}