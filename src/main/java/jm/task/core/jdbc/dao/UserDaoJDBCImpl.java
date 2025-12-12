package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    private static final String create = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50), " +
            "last_name VARCHAR(50), " +
            "age TINYINT" +
            ")";
    private static final String drop = "DROP TABLE IF EXISTS users";
    private static final String insert = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String deleteUser = "DELETE FROM users WHERE id = ?";
    private static final String select = "SELECT id, name, last_name, age FROM users";
    private static final String clean = "DELETE FROM users";

    private final Connection conn;

    public UserDaoJDBCImpl() {
            this.conn = Util.getConnection();
    }

    public void createUsersTable() {
        try (PreparedStatement stmt = conn.prepareStatement(create)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при созданий", e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement stmt = conn.prepareStatement(drop)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка дропа", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement stmt = conn.prepareStatement(insert)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранении", e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement stmt = conn.prepareStatement(deleteUser)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(select);
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
        try (PreparedStatement stmt = conn.prepareStatement(clean)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка очистки", e);
        }
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }
}