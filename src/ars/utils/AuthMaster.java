package ars.utils;

import ars.models.Master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthMaster {
    public static Master currentMaster;

    public static void setCurrentMaster(Master currentMaster) {
        AuthMaster.currentMaster = currentMaster;
    }

    /**
     * Method to add new master to DB
     *
     * @param master object with all master data
     */
    public static void signupMaster(Master master) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("insert into master (officeName,phone,email,password) values (?,?,?,?);");
            st.setString(1, master.getOfficeName());
            st.setString(2, master.getPhone());
            st.setString(3, master.getEmail());
            st.setString(4, master.getPassword());
            st.executeUpdate();
            ResultSet resultSet = MasterAPI.searchUsingMasterName(master.getEmail());
            if (resultSet != null) {
                if (resultSet.next()) {
                    master.setMasterID(resultSet.getInt("id"));
                }
            }
            currentMaster = master;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
