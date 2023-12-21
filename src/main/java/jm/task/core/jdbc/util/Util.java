package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL ="jdbc:mysql://localhost:3306/newbd123";
    private static final String USERNAME ="root";
    private static final String PASSWORD ="rootuser";
    private final Connection connect;
    public Util() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public  Connection getConnect () {
        return connect;
    }
    public void close() throws Exception {
        connect.close();
    }
}
