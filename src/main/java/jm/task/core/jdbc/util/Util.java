package jm.task.core.jdbc.util;
import java.sql.*;
import java.util.*;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123123";
    private static final String URL = "jdbc:mysql://localhost:3306/lesson";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }
}