package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MealDaoDBImpl implements MealDao {

    private Connection connection;

    public MealDaoDBImpl() {
        connection = DBUtil.getConnection();
    }

    @Override
    public void add(final Meal meal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into meals(date,description,calories) values (?, ?, ?)");
            preparedStatement.setString(1, DBUtil.dateTimeToString(meal.getDateTime()));
            preparedStatement.setString(2, meal.getDescription());
            preparedStatement.setInt(3, meal.getCalories());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from meals where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clear() {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("delete * from meals");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Meal meal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update meals set date=?, description=?, calories=? where id=?");
            preparedStatement.setString(1, DBUtil.dateTimeToString(meal.getDateTime()));
            preparedStatement.setString(2, meal.getDescription());
            preparedStatement.setInt(3, meal.getCalories());
            preparedStatement.setInt(4, meal.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Meal> get() {
        List<Meal> mealList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from meals");
            while (rs.next()) {
                LocalDateTime dateTime = DBUtil.stringToDateTime(rs.getString("date"));
                String description = rs.getString("description");
                int calories = rs.getInt("calories");
                int id = rs.getInt("id");
                mealList.add(new Meal(dateTime, description, calories, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mealList;
    }

    @Override
    public Meal getById(int id) {
        Meal meal = null;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from meals where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                LocalDateTime dateTime = DBUtil.stringToDateTime(rs.getString("date"));
                String description = rs.getString("description");
                int calories = rs.getInt("calories");
                int currentId = rs.getInt("id");
                meal = new Meal(dateTime, description, calories, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meal;
    }
}