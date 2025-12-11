package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection conn;

    public UserDaoJDBCImpl() {
        try {
            this.conn = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка соединения", e);
        }
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "last_name VARCHAR(50), " +
                "age TINYINT" +
                ")";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при созданий", e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка дропа", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранении", e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления", e);
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT id, name, last_name, age FROM users";
        List<User> users = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
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
        String sql = "DELETE FROM users";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка очистки", e);
        }
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }
}