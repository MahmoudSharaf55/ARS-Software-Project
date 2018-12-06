package ars.utils;

import java.sql.*;

public class MasterAPI {
    public static ResultSet searchUsingMasterName(String masterEmail) {
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select * from master where email=? ");
            preparedStatement.setString(1, masterEmail);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}



