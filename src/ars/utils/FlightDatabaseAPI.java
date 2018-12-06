package ars.utils;

import ars.models.Flight;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightDatabaseAPI {
    public static ResultSet getAllAirports() {
        ResultSet resultSet;
        try {
            resultSet = DBConnection.getConnection().prepareStatement("select name from airports;").executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static boolean addFlight(Flight flight){
//
//    }

    public static ResultSet searchByFlightNumber(String flightNum) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("select * from flight where flightNumber = ?");
            statement.setString(1, flightNum);
            return statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
