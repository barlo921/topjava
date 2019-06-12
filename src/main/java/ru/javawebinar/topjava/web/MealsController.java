package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;

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

        String forward = "/meals.jsp";
        String action = req.getParameter("action");

        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "delete":
                log.debug("Optional: action=delete");
                mealDao.delete(Integer.parseInt(req.getParameter("mealId")));
                resp.sendRedirect(req.getRequestURI());
                break;
            case "edit":
                log.debug("Optional: action=edit");
                req.setAttribute("meal", mealDao.get(Integer.parseInt(req.getParameter("mealId"))));
                forward = "/meal.jsp";
                req.getRequestDispatcher(forward).forward(req, resp);
                break;
            case "add":
                log.debug("Optional: Add meal");
                forward = "/meal.jsp";
                req.getRequestDispatcher(forward).forward(req, resp);
                break;
            default:
                log.debug("Optional: action=list");
                req.setAttribute("mealList", MealsUtil.getFilteredWithExcessInOnePass2(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                forward = "/meals.jsp";
                req.getRequestDispatcher(forward).forward(req, resp);
                break;
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        log.debug("Optional: Form add/update");

        Meal meal = new Meal();
        meal.setDateTime(MealDaoMemoryImpl.stringToDateTime(req.getParameter("dateTime")));
        meal.setDescription(req.getParameter("description"));
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));

        String id = req.getParameter("id");
        if (id.equals("")) {
            mealDao.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            mealDao.update(meal);
        }

        req.setAttribute("mealList", MealsUtil.getFilteredWithExcessInOnePass2(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        String forward = "/meals.jsp";
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
