package ars.utils;

import java.sql.*;

public class UserAPI {


    /**
     * @param name     username from user signup
     * @param date     user date of birth
     * @param gender   user gender
     * @param email    user email
     * @param password user password
     */
    public static void signupUser(String name, String date, String gender, String email, String password) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement st = connection.prepareStatement("insert into user (name,date,gender,email,password) values (?,?,?,?,?)");
            st.setString(1, name);
            st.setString(2, date);
            st.setString(3, gender);
            st.setString(4, email);
            st.setString(5, password);
            st.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
