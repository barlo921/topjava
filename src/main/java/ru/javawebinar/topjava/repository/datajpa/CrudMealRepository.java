package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    int delete(@Param("id") int id, @Param("user_id") int userId);

    @Query(value = "SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    Optional<Meal> findById(@Param("id") int id, @Param("user_id") int userId);

    @Query(value = "SELECT m FROM Meal m WHERE m.user.id=:user_id ORDER BY m.dateTime DESC")
    List<Meal> findAll(@Param("user_id") int userId);

    @Query(value = "SELECT m FROM Meal m " +
            "WHERE m.user.id=:user_id AND m.dateTime BETWEEN :start_date AND :end_date ORDER BY m.dateTime DESC")
    List<Meal> filtered(@Param("start_date") LocalDateTime startDate,
                        @Param("end_date") LocalDateTime endDate,
                        @Param("user_id") int userId);
}
