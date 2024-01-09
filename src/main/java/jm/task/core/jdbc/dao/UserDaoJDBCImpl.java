package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
private static final String TAB_IS_EXIST = "Show tables";
private static final String CREATE_NEW_TAB = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT,name VARCHAR(100) NOT NULL,lastName VARCHAR(100) NOT NULL, Age INT NOT NULL,PRIMARY KEY (`id`))";
private static final String DROP_TAB = "DROP TABLE IF EXISTS users";
private static final String GET_ALL_USERS = "select * from users";
private static final String INSERT_NEW_USER = "INSERT INTO users (name, lastName, Age) VALUES(?,?,?)";
private static final String DELETE_USER = "DELETE FROM users where id=? ";
private static final String CLEAR_USERS = "TRUNCATE TABLE users";
private static Connection connection = new Util().getConnect();

public UserDaoJDBCImpl() {
}
public void createUsersTable() {
    try (PreparedStatement prepStat = connection.prepareStatement(CREATE_NEW_TAB)) {
        prepStat.execute();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
public void dropUsersTable() {
    try (PreparedStatement prepStat = connection.prepareStatement(DROP_TAB)) {
        prepStat.execute();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
public void saveUser(String name, String lastName, byte age) {
    try (PreparedStatement prepStat = connection.prepareStatement(INSERT_NEW_USER)) {
        prepStat.setString(1, name);
        prepStat.setString(2, lastName);
        prepStat.setInt(3, age);
        prepStat.execute();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
public void removeUserById(long id) {
    try (PreparedStatement prepStat = connection.prepareStatement(DELETE_USER)) {
        prepStat.setInt(1, (int) id);
        prepStat.execute();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    try (PreparedStatement prepStat = connection.prepareStatement(GET_ALL_USERS)) {
        prepStat.execute();
        ResultSet resultSet = prepStat.getResultSet();
        while (resultSet.next()) {
            User user = new User();
            user.setId((long) resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setAge((byte) resultSet.getInt(4));
            users.add(user);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return users;
}
public void cleanUsersTable() {
    try (PreparedStatement prepStat = connection.prepareStatement(CLEAR_USERS)) {
        prepStat.execute();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
}
