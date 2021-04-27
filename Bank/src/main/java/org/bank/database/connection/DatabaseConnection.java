package org.bank.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
//    private DatabaseConnection(){
//
//    }
//
//    private static Connection conn;
//
//    public static Connection getConnection() {
//        try {
//        Class.forName("org.postgresql.Driver");
//        String url="awsdatadase.c9lte7mow2q1.us-east-2.rds.amazonaws.com";
//        String username="postgres";
//        String password="Shad0wlord";
//        conn = DriverManager.getConnection(url,username,password);
//        } catch (SQLException | ClassNotFoundException e) {
//        }
//        return conn;
//    }

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Sh@d0wlord";


    private static Connection conn;

    public static Connection getConnection() {

        try {

            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return conn;
    }
}
