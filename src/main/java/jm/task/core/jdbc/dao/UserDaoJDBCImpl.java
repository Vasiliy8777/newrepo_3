package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String TAB_IS_EXIST ="Show tables";
    private static final String CREATE_NEW_TAB ="CREATE TABLE users (id INT NOT NULL AUTO_INCREMENT,name VARCHAR(100) NOT NULL,lastName VARCHAR(100) NOT NULL, Age INT NOT NULL,PRIMARY KEY (`id`))";
    private static final String DROP_TAB ="DROP TABLE users";
    private static final String GET_ALL_USERS="select * from users";
    private static final String INSERT_NEW_USER="INSERT INTO users (name, lastName, Age) VALUES(?,?,?)";
    private static final String DELETE_USER="DELETE FROM users where id=? ";
    private static final String CLEAR_USERS="TRUNCATE TABLE users";
    private static Util connection=null;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        connection=new Util();
        Statement stmt=null;
        try {
            stmt = connection.getConnect().createStatement();
            ResultSet rs = stmt.executeQuery(TAB_IS_EXIST);
            if (rs.next()) {
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement prepStat=connection.getConnect().prepareStatement(CREATE_NEW_TAB)) {
            prepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        connection=new Util();
        Statement stmt=null;
        try {
            stmt = connection.getConnect().createStatement();
            ResultSet rs = stmt.executeQuery(TAB_IS_EXIST);
            if (!rs.next()) {
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement prepStat=connection.getConnect().prepareStatement(DROP_TAB)) {
            prepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        connection=new Util();
        try (PreparedStatement prepStat= connection.getConnect().prepareStatement(INSERT_NEW_USER)) {
            prepStat.setString(1,name);
            prepStat.setString(2,lastName);
            prepStat.setInt(3,age);
            prepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        connection=new Util();
        try (PreparedStatement prepStat= connection.getConnect().prepareStatement(DELETE_USER)) {
            prepStat.setInt(1,(int)id);
            prepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        connection=new Util();
        List<User> users=new ArrayList<>();
        Statement st =null;
        try {
            st = connection.getConnect().createStatement();
            ResultSet resultSet = st.executeQuery(GET_ALL_USERS);
            while (resultSet.next()){
                User user=new User();
                user.setId((long) resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge((byte) resultSet.getInt(4));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (st!=null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection!=null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        connection=new Util();
        try (PreparedStatement prepStat= connection.getConnect().prepareStatement(CLEAR_USERS)) {
            prepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
