package ars.utils;

import ars.models.Flight;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

    public static int addFlight(Flight flight) {
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into flight (flightNumber, src, dest, dateAndTime, price, seats, delay, master_id) values (?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, flight.getFlightNumber());
            preparedStatement.setString(2, flight.getSrc());
            preparedStatement.setString(3, flight.getDest());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            preparedStatement.setString(4, dateFormat.format(flight.getDateAndTime()));
            preparedStatement.setInt(5, flight.getPrice());
            preparedStatement.setInt(6, flight.getSeats());
            preparedStatement.setInt(7, flight.getDelay());
            preparedStatement.setInt(8, flight.getMasterID());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

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
