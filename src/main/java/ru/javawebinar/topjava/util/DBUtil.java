package ru.javawebinar.topjava.util;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

public class DBUtil {
    private static final Logger log = getLogger(DBUtil.class);
    private static Connection connection = null;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {

            try {
                Properties properties = new Properties();
                InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("/db.properties");
                properties.load(is);
                String driver = properties.getProperty("driver");
                String url = properties.getProperty("url");
                String user = properties.getProperty("user");
                String pass = properties.getProperty("password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, pass);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            log.debug("Connect to DB " + connection);
            return connection;
        }
    }

    public static String dateTimeToString(final LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public static LocalDateTime stringToDateTime(final String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
