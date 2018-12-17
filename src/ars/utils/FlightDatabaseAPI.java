package ars.utils;

import ars.models.Flight;
import ars.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static ResultSet searchUsingMasterID(int masterID) {

        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("select flight.*, m.*, s.latidude as 'srclat', s.longitude as 'srclon', d.longitude as 'destlon', d.latidude as 'destlat' from flight   inner join master m on flight.master_id = m.id     inner join airports d on flight.dest = d.name inner JOIN airports s on flight.src = s.name where master_id = ?;");
            statement.setInt(1, masterID);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet searchUsingFlightNumber(String flightNumber) {

        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("select * from flight inner join master m on flight.master_id = m.id where flightNumber = ?");
            statement.setString(1, flightNumber);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet searchUsingSrcAndDestination(String src, String dest, Date dateAndTime) {

        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("select * from flight inner join master m on flight.master_id = m.id where src = ? and dest = ? and dateAndTime >= ?");
            statement.setString(1, src);
            statement.setString(2, dest);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            statement.setString(3, dateFormat.format(dateAndTime));
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet searchUsingDateAndTime(Date date) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("select * from flight inner join master m on flight.master_id = m.id where  dateAndTime >= ?");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            statement.setString(1, dateFormat.format(date));
            return statement.executeQuery();
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

    public static int updateFlight(Flight flight) {
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update  flight SET src = ? , dest = ?, delay =? , seats =?,dateAndTime = ?,price=? where flightNumber =?");
            preparedStatement.setString(7, flight.getFlightNumber());
            preparedStatement.setString(1, flight.getSrc());
            preparedStatement.setString(2, flight.getDest());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Calendar c = Calendar.getInstance();
            c.setTime(flight.getDateAndTime());
            c.add(Calendar.DATE, flight.getDelay());
            preparedStatement.setString(5, dateFormat.format(c.getTime()));
            preparedStatement.setInt(6, flight.getPrice());
            preparedStatement.setInt(4, flight.getSeats());
            preparedStatement.setInt(3, flight.getDelay());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int updateUser(User user) {

        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update user set name = ?, date = ? , email = ?, gender = ? where id = ?");
            System.out.println(user.getUserID());
            System.out.println(user.getGender());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getDateOfBirth().toString());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getGender());
            preparedStatement.setInt(5, user.getUserID());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int deleteFlight(Flight flight) {
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("delete from flight where flightNumber = ?");
            preparedStatement.setString(1, flight.getFlightNumber());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static ResultSet getPassengersUsingFlightNumber(String flightNum) {
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select  t.ticket_number, f.flightNumber,f.master_id,u.* from flight f inner join ticket t on f.flightNumber = t.flightNumber inner  join user u on t.user_id = u.id where f.flightNumber = ? ");
            preparedStatement.setString(1, flightNum);
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    public static ResultSet getFlightsAssociatedWithAllTickets(int masterID) {
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select f.dest, count(*) as count from ticket inner join flight f on ticket.flightNumber = f.flightNumber where master_id=? group by f.dest ; ");
            preparedStatement.setInt(1, masterID);
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getFlightsCount(int masterID) {
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select count(*) as count from ticket inner join flight f on ticket.flightNumber = f.flightNumber where master_id=? group by f.master_id ; ");
            preparedStatement.setInt(1, masterID);
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
