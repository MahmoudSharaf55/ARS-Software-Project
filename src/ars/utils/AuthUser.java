package ars.utils;

import ars.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthUser {
    public static User currentUser;

    public static void setCurrentUser(User currentUser) {
        AuthUser.currentUser = currentUser;
    }

    /**
     * @param user the user needed to be added to DB
     */
    public static void signupUser(User user) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement st = connection.prepareStatement("insert into user (name,date,gender,email,password) values (?,?,?,?,?)");
            st.setString(1, user.getName());
            st.setString(2, user.getDateOfBirth().toString());
            st.setString(3, user.getGender());
            st.setString(4, user.getEmail());
            st.setString(5, user.getPassword());
            st.executeUpdate();
            currentUser = user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
