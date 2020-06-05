package database;

import java.sql.*;

public class PostgreSQLDatabase {
    private final static String URL = "jdbc:postgresql://localhost:5432/bazaar_db";
    private final static String USERNAME = "bazaarman";
    private final static String PASSWORD = "bazaarman";

    public static void version() {
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT VERSION()")) {
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
