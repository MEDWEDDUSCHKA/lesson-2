package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABEL_SQL = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50), " +
            "last_name VARCHAR(50), " +
            "age TINYINT" +
            ")";
    private static final String DROP_TABEL_SQL= "DROP TABLE IF EXISTS users";
    private static final String INSERT_USER_SQL = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String DELETE_FROM_USER_SQL = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_USERS_SQL = "SELECT id, name, last_name, age FROM users";
    private static final String CLEAN_TABLE_SQL = "DELETE FROM users";

    private final Connection conn = new Util().getConnection();


    public void createUsersTable() {
        try (PreparedStatement stmt = conn.prepareStatement(CREATE_TABEL_SQL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при созданий", e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement stmt = conn.prepareStatement(DROP_TABEL_SQL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка дропа", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_USER_SQL)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранении", e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_FROM_USER_SQL)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(SELECT_USERS_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("last_name"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пользователей", e);
        }

        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement stmt = conn.prepareStatement(CLEAN_TABLE_SQL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка очистки", e);
        }
    }
}