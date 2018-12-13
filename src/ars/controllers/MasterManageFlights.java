package ars.controllers;

import ars.models.Flight;
import ars.utils.AuthMaster;
import ars.utils.FlightDatabaseAPI;
import ars.utils.UtilityServices;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class MasterManageFlights implements Initializable {
    public static Flight selectedFlight;
    @FXML
    private JFXTextField flightNumberTextField, delayTF;

    @FXML
    private JFXComboBox<String> departComboBox;

    @FXML
    private JFXComboBox<String> destComboBox;

    @FXML
    private JFXTextField numberOfSeatsTF;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private JFXTextField priceTextField;
    @FXML
    private StackPane sp;
    private Flight currentFlight;
    private Flight flightToEdit;
    private ArrayList<String> airportsList;

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
        destComboBox.getItems().setAll(FXCollections.observableArrayList(airportsList));
        if (selectedFlight != null) {
            currentFlight = selectedFlight;
            flightNumberTextField.setText(currentFlight.getFlightNumber());
            selectedFlight = null;
        }


    }


    @FXML
    void cancel(ActionEvent event) {
        if (flightToEdit != null) {
            if (FlightDatabaseAPI.deleteFlight(flightToEdit) > 0) {
                UtilityServices.displayDialog(new Text("Successfully Deleted !"), new Text("The Flight is deleted from the database"), sp);
                resetFields();

            } else {
                UtilityServices.displayDialog(new Text("Successfully Updated !"), new Text("The Update is stored in the database"), sp);

            }

        } else {
            UtilityServices.displayDialog(new Text("Check Your inputs !"), new Text("Please enter a valid flight number to load the flight and try again"), sp);

        }

    }

    @FXML
    void edit(ActionEvent event) {
        if (flightToEdit != null) {
            if (!destComboBox.getSelectionModel().isEmpty() && !departComboBox.getSelectionModel().isEmpty()
                    && datePicker.getValue() != null && timePicker.getValue() != null
                    && !priceTextField.getText().isEmpty() && !numberOfSeatsTF.getText().isEmpty()) {
                LocalDateTime localDateTime = LocalDateTime.of(datePicker.getValue(), timePicker.getValue());
                Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                Flight flight = new Flight(flightToEdit.getFlightNumber(), departComboBox.getSelectionModel().getSelectedItem().toString(), destComboBox.getSelectionModel().getSelectedItem().toString(), date, Integer.parseInt(priceTextField.getText()), Integer.parseInt(numberOfSeatsTF.getText()), Integer.parseInt(delayTF.getText()), AuthMaster.currentMaster.getMasterID());
                if (FlightDatabaseAPI.updateFlight(flight) > 0) {
                    UtilityServices.displayDialog(new Text("Successfully Updated !"), new Text("The Update is stored in the database"), sp);
                } else {
                    UtilityServices.displayDialog(new Text("Can't Update !"), new Text("Please  check your inputs and try again"), sp);

                }

            } else {
                UtilityServices.displayDialog(new Text("Check Your inputs !"), new Text("Please  check your inputs and try again"), sp);

            }
        } else {
            UtilityServices.displayDialog(new Text("Check Your inputs !"), new Text("Please enter a valid flight number to load the flight and try again"), sp);
        }
    }

    @FXML
    void loadFlightInfo(ActionEvent event) {
        resetFields();
        flightToEdit = null;
        if (currentFlight == null) {
            if (!flightNumberTextField.getText().isEmpty()) {
                ResultSet resultSet = FlightDatabaseAPI.searchByFlightNumber(flightNumberTextField.getText());
                try {
                    if (resultSet.next()) {
                        if (AuthMaster.currentMaster.getMasterID() == resultSet.getInt("master_id")) {
                            currentFlight = new Flight(resultSet.getString("flightNumber"), resultSet.getString("src"),
                                    resultSet.getString("dest"), resultSet.getTimestamp("dateAndTime"),
                                    resultSet.getInt("price"), resultSet.getInt("seats"),
                                    resultSet.getInt("delay"), resultSet.getInt("master_id"));
                        } else {
                            UtilityServices.displayDialog(new Text("Missing Permissions"), new Text("Please enter a valid flight number that's actually\ncreated by your own master account"), sp);
                            return;
                        }
                    } else {
                        UtilityServices.displayDialog(new Text("Can't Find Flight"), new Text("Please enter a valid flight number that's actually\navailable in the database"), sp);
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return;
                }
            } else {
                UtilityServices.displayDialog(new Text("Check Your inputs !"), new Text("Please enter a valid flight number and try again"), sp);
                return;
            }

        }
        priceTextField.setText(currentFlight.getPrice() + "");
        numberOfSeatsTF.setText(currentFlight.getSeats() + "");
        datePicker.setValue(currentFlight.getDateAndTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        timePicker.setValue(currentFlight.getDateAndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        departComboBox.getSelectionModel().select(currentFlight.getSrc());
        delayTF.setText("0");
        destComboBox.getSelectionModel().select(currentFlight.getDest());
        flightToEdit = currentFlight;
        currentFlight = null;
    }

    private void resetFields() {
        priceTextField.setText("");
        numberOfSeatsTF.setText("");
        datePicker.setValue(null);
        timePicker.setValue(null);
        departComboBox.getSelectionModel().clearSelection();
        delayTF.setText("");
        destComboBox.getSelectionModel().clearSelection();
    }
}
