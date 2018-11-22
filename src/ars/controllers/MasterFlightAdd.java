package ars.controllers;

import ars.models.Airport;
import ars.utils.AuthMaster;
import ars.utils.DBConnection;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        airportsList = new ArrayList<>();

        try {
            ResultSet resultSet = DBConnection.getConnection().prepareStatement("select name from airports;").executeQuery();
            while (resultSet.next()) {
                airportsList.add(resultSet.getString("name"));
            }

            departComboBox.getItems().setAll(FXCollections.observableArrayList(airportsList));
            destComboBox.getItems().setAll(FXCollections.observableArrayList(airportsList));


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void addFlight(ActionEvent event) {
        while (true) {
            try {

                String rand = "48498e";
                PreparedStatement prepareStatement = DBConnection.getConnection().prepareStatement("select flightNumber from  flight where  flightNumber = ?;");
                prepareStatement.setString(1, rand);
                ResultSet resultSet = prepareStatement.executeQuery();
                if (resultSet.next()) {

                } else {
                    break;
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


}
