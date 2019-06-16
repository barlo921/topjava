package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static List<User> USERS = Arrays.asList(
            new User("Gosha", "gosha@afg.ru", "12345", Role.ROLE_USER),
            new User("Pasha", "pasha@afg.ru", "123", Role.ROLE_USER),
            new User("Admin", "admin@afg.ru", "admin", Role.ROLE_USER, Role.ROLE_ADMIN)
    );
}
