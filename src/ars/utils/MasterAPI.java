package ars.utils;
import java.sql.*;

public class MasterAPI {


    /**
     *  Method to add new master to DB
     * @param name
     * @param phone
     * @param email
     * @param password
     */
    public static void signupMaster(String name, String phone, String email, String password){
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("insert into master (officeName,phone,email,password) values (?,?,?,?);");
            st.setString(1,name);
            st.setString(2,phone);
            st.setString(3,email);
            st.setString(4,password);
            st.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}



