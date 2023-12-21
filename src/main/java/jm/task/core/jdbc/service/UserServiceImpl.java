package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.DatabaseMetaData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final String TAB_IS_EXIST ="Show tables";
    private static final String CREATE_NEW_TAB ="CREATE TABLE users (id INT NOT NULL AUTO_INCREMENT,name VARCHAR(100) NOT NULL,lastName VARCHAR(100) NOT NULL, Age INT NOT NULL,PRIMARY KEY (`id`))";
    private static final String DROP_TAB ="DROP TABLE users";
    private static final String GET_ALL_USERS="select * from users";
    private static final String INSERT_NEW_USER="INSERT INTO users (name, lastName, Age) VALUES(?,?,?)";
    private static final String DELETE_USER="DELETE FROM users where id=? ";
    private static final String CLEAR_USERS="TRUNCATE TABLE users";

    public void createUsersTable() {
        Util connection=null;
        PreparedStatement prepStat=null;
        Statement stmt=null;
        try {
            connection=new Util();
            stmt = connection.getConnect().createStatement();
            ResultSet rs = stmt.executeQuery(TAB_IS_EXIST);
            if (rs.next()) {
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection=new Util();
            prepStat=connection.getConnect().prepareStatement(CREATE_NEW_TAB);
            prepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt!=null) {
                try {
                    prepStat.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (prepStat!=null) {
                try {
                    prepStat.close();
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
    }

    public void dropUsersTable() {
        Util connection=new Util();
        PreparedStatement prepStat=null;
        Statement stmt=null;
        try {
            connection=new Util();
            stmt = connection.getConnect().createStatement();
            ResultSet rs = stmt.executeQuery(TAB_IS_EXIST);
            if (!rs.next()) {
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            prepStat=connection.getConnect().prepareStatement(DROP_TAB);
            prepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt!=null) {
                try {
                    prepStat.close();
                    //System.out.println("prepStat closed");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (prepStat!=null) {
                try {
                    prepStat.close();
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
    }

    public void saveUser(String name, String lastName, byte age) {
        Util connection=new Util();
        PreparedStatement prepStat=null;
        try {
            prepStat= connection.getConnect().prepareStatement(INSERT_NEW_USER);
            prepStat.setString(1,name);
            prepStat.setString(2,lastName);
            prepStat.setInt(3,age);
            prepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (prepStat!=null) {
                try {
                    prepStat.close();
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
    }

    public void removeUserById(long id) {
        Util connection=new Util();
        PreparedStatement prepStat=null;
        try {
            prepStat= connection.getConnect().prepareStatement(DELETE_USER);
            prepStat.setInt(1,(int)id);
            prepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (prepStat!=null) {
                try {
                    prepStat.close();
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
    }

    public List<User> getAllUsers() {
        List<User> users=new ArrayList<>();
        Util connection=new Util();
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
        }finally {
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
        Util connection=new Util();
        PreparedStatement prepStat=null;
                try {
            prepStat= connection.getConnect().prepareStatement(CLEAR_USERS);
            prepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (prepStat!=null) {
                try {
                    prepStat.close();
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
    }
}
