package ars.utils;
import java.sql.*;

public class masterDBC {

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/airline","root","root");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void inserMaster(String name,String gender,String email,String password){
        Connection connection = getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("insert into master (officeName,gender,email,password) values (?,?,?,?);");
            st.setString(1,name);
            st.setString(2,gender);
            st.setString(3,email);
            st.setString(4,password);
            st.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}



