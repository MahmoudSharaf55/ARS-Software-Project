package ars.controllers;

import ars.models.Ticket;
import ars.utils.AuthUser;
import ars.utils.DBConnection;
import ars.utils.UtilityServices;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class UserEditingTicket  implements Initializable

    {
        /**
         TODO: Sedky load flight info from flight like dashboard and active cancel button (Delete)
         **/
        @FXML
        Label ticketNumber, source, dect, numFlight, price, date;
        @FXML
        JFXButton cancel;
        @FXML
        StackPane spTicket;

        @FXML
        void deleteTicket () {
        try {
            Connection deleteConnection = DBConnection.getConnection();
            PreparedStatement deleteStatement = deleteConnection.prepareStatement("delete from ticket where user_id = ?");
            deleteStatement.setInt(1, AuthUser.currentUser.getUserID());
            deleteStatement.executeUpdate();
            ticketNumber.setText("N/A");
            numFlight.setText("N/A");
            source.setText("N/A");
            dect.setText("N/A");
            date.setText("N/A");
            price.setText("N/A");
            UtilityServices.displayDialog(new Text("Confirm Cancel"), new Text("You are cancel Successfully...  "), spTicket);

        } catch (SQLException ex) {

            ex.printStackTrace();
        }


    }



        @Override
        public void initialize (URL location, ResourceBundle resources){
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("select ticket_number,flight_number from ticket where user_id = ?");
            PreparedStatement statement1 = c.prepareStatement("select src,dest,dateAndTime,price from flight where flightNumber = ?");
            statement.setInt(1, AuthUser.currentUser.getUserID());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String flightNumber = resultSet.getString("flight_number");

                statement1.setString(1, flightNumber);
                ResultSet flightResultSet = statement1.executeQuery();
                if (flightResultSet.next()) {
                    ticketNumber.setText(resultSet.getString("ticket_number"));
                    numFlight.setText(flightNumber);
                    source.setText(flightResultSet.getString("src"));
                    dect.setText(flightResultSet.getString("dest"));
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:MM");
                    date.setText(dateFormat.format(flightResultSet.getDate("dateAndTime")));
                    price.setText(String.valueOf(flightResultSet.getInt("price")));
                }
                Ticket ticket = new Ticket(resultSet.getInt("ticket_number"), AuthUser.currentUser.getUserID(), flightNumber);
            } else {
                ticketNumber.setText("N/A");
                numFlight.setText("N/A");
                source.setText("N/A");
                dect.setText("N/A");
                date.setText("N/A");
                price.setText("N/A");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    }
