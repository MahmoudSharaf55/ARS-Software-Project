package ars.controllers;

import ars.models.Master;
import ars.models.Ticket;
import ars.models.User;
import ars.utils.AuthUser;
import ars.utils.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {
    /**
     * TODO: Sharaf
     * Load all users info in the Dashboard using Auth.currentUser object that contain all current user data
     */
    public User currentUser = AuthUser.currentUser;


    @FXML
    private Label nameLbl,emailLbl,dateLbl,genderLbl,flightNumberLbl,srcLbl,destLbl,flightDateLbl,flightPriceLbl;
    @FXML
    private Rating ratingBar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameLbl.setText(currentUser.getName());
        emailLbl.setText(currentUser.getEmail());
        dateLbl.setText(currentUser.getDateOfBirth().toString());
        genderLbl.setText(currentUser.getGender());
        ratingBar.setRating(currentUser.getRating());

        ratingBar.ratingProperty().addListener((observable, oldValue, newValue) -> {
            AuthUser.currentUser.setRating(newValue.intValue());
            Connection connection = DBConnection.getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement("update user set rating = ? where id = ?");
                statement.setInt(1,newValue.intValue());
                statement.setInt(2,AuthUser.currentUser.getUserID());
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });

        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("select ticket_number,flight_number from ticket where user_id = ?");
            PreparedStatement statement1 = c.prepareStatement("select src,dest,dateAndTime,price from flight where flightNumber = ?");
            statement.setInt(1,AuthUser.currentUser.getUserID());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                String flightNumber = resultSet.getString("flight_number");
                statement1.setString(1,flightNumber);
                ResultSet flightResultSet = statement1.executeQuery();
                if (flightResultSet.next()){
                    flightNumberLbl.setText(flightNumber);
                    srcLbl.setText(flightResultSet.getString("src"));
                    destLbl.setText(flightResultSet.getString("dest"));
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:MM");
                    flightDateLbl.setText(dateFormat.format(flightResultSet.getDate("dateAndTime")));
                    flightPriceLbl.setText(String.valueOf(flightResultSet.getInt("price")));
                }
                Ticket ticket = new Ticket(resultSet.getInt("ticket_number"),AuthUser.currentUser.getUserID(),flightNumber);
                Ticket.currentTicket = ticket;
            }
            else {
                flightNumberLbl.setText("N/A");
                srcLbl.setText("N/A");
                destLbl.setText("N/A");
                flightDateLbl.setText("N/A");
                flightPriceLbl.setText("N/A");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
