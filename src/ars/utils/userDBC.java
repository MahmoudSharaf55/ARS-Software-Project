package ars.utils;
import java.sql.*;

public class userDBC {


    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/airline","root","");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     *
     * @param name username from user signup
     * @param date  user date of birth
     * @param gender user gender
     * @param email user email
     * @param password  user password
     */
    public static void insertUser(String name,String date,String gender,String email,String password){
        try {
            Connection connection = getConnection();
            PreparedStatement st = connection.prepareStatement("insert into user (name,date,gender,email,password) values (?,?,?,?,?)");
            st.setString(1,name);
            st.setString(2,date);
            st.setString(3,gender);
            st.setString(4,email);
            st.setString(5,password);
            st.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
