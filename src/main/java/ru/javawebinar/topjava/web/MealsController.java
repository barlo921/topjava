package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DBUtil;
import ru.javawebinar.topjava.util.MealMemoryService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsController extends HttpServlet {
    private static final Logger log = getLogger(MealsController.class);

    private MealDao mealDao;

    public MealsController() {
        super();
        log.debug("Instantiate. Meal DAO");
        mealDao = new MealDaoMemoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("Optional: set attribute and forward to meals.jsp");

        String forward;
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            log.debug("Optional: action=delete");
            mealDao.delete(Integer.parseInt(req.getParameter("mealId")));
            req.setAttribute("mealList", MealsUtil.getNonFilteredWithExcess(MealMemoryService.getMealList(), 2000));
            forward = "/mealsOptional.jsp";
        } else if (action.equalsIgnoreCase("edit")) {
            log.debug("Optional: action=edit");
            req.setAttribute("meal", mealDao.getById(Integer.parseInt(req.getParameter("mealId"))));
            forward = "/meal.jsp";
        } else {
            log.debug("Optional: action=list");
            req.setAttribute("mealList", MealsUtil.getNonFilteredWithExcess(MealMemoryService.getMealList(), 2000));
            forward = "/mealsOptional.jsp";
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        log.debug("Optional: Form update");

        Meal meal = new Meal();
        meal.setId(Integer.parseInt(req.getParameter("id")));
        meal.setDateTime(DBUtil.stringToDateTime(req.getParameter("dateTime")));
        meal.setDescription(req.getParameter("description"));
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));

        mealDao.update(meal);

        req.setAttribute("mealList", MealsUtil.getNonFilteredWithExcess(MealMemoryService.getMealList(), 2000));
        String forward = "/mealsOptional.jsp";
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
