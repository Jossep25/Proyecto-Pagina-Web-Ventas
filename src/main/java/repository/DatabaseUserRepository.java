package repository;

import interfaces.UserRepository;
import model.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUserRepository implements UserRepository {
    private final Connection connection;

    public DatabaseUserRepository() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_ventas";
        String jdbcUser = "root";
        String jdbcPassword = "";
        connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
    }

    @Override
    public Users findByUsername(String username) throws SQLException {
        String query = "SELECT id, username, password, email, role_id, first_name, last_name, dni FROM users WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new Users(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getInt("role_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("dni")
            );
        } else {
            return null;
        }
    }

    @Override
    public boolean save(Users user) throws SQLException {
        String query = "INSERT INTO users (username, password,email, role_id, first_name, last_name, dni) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getEmail());
        statement.setInt(4, user.getRoleId());
        statement.setString(5, user.getFirstName());
        statement.setString(6, user.getLastName());
        statement.setString(7, user.getDni());
        int rowsInserted = statement.executeUpdate();
        return rowsInserted > 0;
    }

    @Override
    public boolean register(Users user) throws SQLException {
        return save(user);
    }

    @Override
    public boolean authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
}
