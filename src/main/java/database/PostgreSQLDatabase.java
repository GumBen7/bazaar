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

    public static ResultSet execute(String query, String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement pst = con.prepareStatement(query);
            for (int i = 0; i < args.length; i++) {
                pst.setString(i + 1, args[0]);
            }
            return pst.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ResultSet execute(String query, String arg1, Long arg2) {
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, arg1);
            pst.setLong(2, arg2);
            return pst.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ResultSet execute(String query, Double arg1, Double arg2) {
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setDouble(1, arg1);
            pst.setDouble(2, arg2);
            return pst.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
