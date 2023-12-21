package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
public class Main {
    public static void main(String[] args) {
        UserService userServ = new UserServiceImpl();
        userServ.createUsersTable();
        userServ.dropUsersTable();
        userServ.dropUsersTable();
        userServ.createUsersTable();
        userServ.saveUser("Tom", "Doe", (byte) 23);
        userServ.saveUser("Mot", "Fot", (byte) 53);
        userServ.saveUser("Fred", "Dost", (byte) 93);
        userServ.saveUser("Bob", "Ford", (byte) 45);
        userServ.saveUser("Mot", "Port", (byte) 33);
        System.out.println(userServ.getAllUsers());
        userServ.removeUserById(3);
        System.out.println(userServ.getAllUsers());
       userServ.cleanUsersTable();
       System.out.println(userServ.getAllUsers());
       userServ.dropUsersTable();
    }
}
