package ars.controllers;

import ars.models.Airport;
import ars.models.Flight;
import ars.utils.AuthMaster;
import ars.utils.DBConnection;
import ars.utils.FlightDatabaseAPI;
import ars.utils.UtilityServices;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class MasterFlightAdd implements Initializable {
    /**
     * TODO: Toqa
     * Fill both combo box with airports names from DB and store these names and both latitude and longitude
     * in array list from type Airport
     * Then add airportsList to both combo box
     * in the initialize method
     * this link will help to understand arraylist https://www.geeksforgeeks.org/arraylist-in-java/ and combobox http://tutorials.jenkov.com/javafx/combobox.html
     * check that all data is entered and create random flight number of 2 letters and 4 num using Math.rand()
     * insert it to Flight table  in DB
     * if any error change unfocus color of the fields to red color
     * U CAN DO IT !!!! :D
     */
    private ArrayList<String> airportsList;

    @FXML
    JFXComboBox departComboBox, destComboBox;
    @FXML
    JFXTextField numOfSeats, price;
    @FXML
    JFXDatePicker flightDate;
    @FXML
    JFXTimePicker timePicker;
    @FXML
    StackPane sp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        airportsList = new ArrayList<>();


        ResultSet resultSet = FlightDatabaseAPI.getAllAirports();

        try {
            while (resultSet.next()) {
                airportsList.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        departComboBox.getItems().setAll(FXCollections.observableArrayList(airportsList));


    }

    @FXML
    public void addFlight(ActionEvent event) {


        if (!destComboBox.getSelectionModel().isEmpty() && !departComboBox.getSelectionModel().isEmpty()
                && flightDate.getValue() != null && timePicker.getValue() != null
                && !price.getText().isEmpty() && !numOfSeats.getText().isEmpty()) {
            int randomPIN = (int) (Math.random() * 9000) + 1000;
            String twoLetter = AuthMaster.currentMaster.getOfficeName().substring(0, 2);
            String flightNumber = twoLetter + randomPIN;
            while (true) {
                try {
                    if (!FlightDatabaseAPI.searchByFlightNumber(flightNumber).next()) {
                        break;
                    } else {
                        randomPIN = (int) (Math.random() * 9000) + 1000;
                        flightNumber = twoLetter + randomPIN;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            LocalDateTime localDateTime = LocalDateTime.of(flightDate.getValue(), timePicker.getValue());
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Flight flight = new Flight(flightNumber, departComboBox.getSelectionModel().getSelectedItem().toString(), destComboBox.getSelectionModel().getSelectedItem().toString(), date, Integer.parseInt(price.getText()), Integer.parseInt(numOfSeats.getText()), 0, AuthMaster.currentMaster.getMasterID());
            if (FlightDatabaseAPI.addFlight(flight) > 0) {
                UtilityServices.displayDialog(new Text("Your flight is available Now !"), new Text("The flight is saved into the Database with \nGenerated flight number : " + flightNumber), sp);
                timePicker.setValue(null);
                flightDate.setValue(null);
                numOfSeats.setText("");
                price.setText("");
                destComboBox.setDisable(true);
                numOfSeats.setDisable(true);
                price.setDisable(true);
                timePicker.setDisable(true);
                flightDate.setDisable(true);

            } else {
                UtilityServices.displayDialog(new Text("Error Check your inputs"), new Text("Make sure that you entered all required field's correctly"), sp);

            }

        } else {
            UtilityServices.displayDialog(new Text("Error Check your inputs"), new Text("Make sure that you entered all required field's correctly"), sp);
        }

    }


    @FXML
    public void onDepartureAirportSelected(ActionEvent event) {
        destComboBox.getItems().setAll(FXCollections.observableArrayList(airportsList));
        destComboBox.getItems().remove(departComboBox.getSelectionModel().getSelectedIndex());
        destComboBox.setDisable(false);

    }

    @FXML
    public void onDestAirportSelected(ActionEvent event) {
        numOfSeats.setDisable(false);
        price.setDisable(false);
        timePicker.setDisable(false);
        flightDate.setDisable(false);
    }

}
