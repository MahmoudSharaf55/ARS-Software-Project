package ars.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MasterSearchController implements Initializable {
    @FXML
    private JFXComboBox<String> searchComboBox;
    @FXML
    private JFXTextField flightNum;
    @FXML
    private JFXComboBox<?> departComboBox;
    @FXML
    private JFXComboBox<?> destComboBox;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTimePicker timePicker;
    @FXML
    private TableView<?> dataTable;
    @FXML
    private JFXSpinner loadingSpinner;
    private final String MASTER_FLIGHTS = "Master Flights";
    private final String FLIGHT_NUMBER = "Flight Number";
    private final String FLIGHT_DEST_DEPART = "Flight Destination & Schedule";
    private final String FLIGHT_Date_Time = "Flight Date & Time";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchComboBox.getItems().setAll(MASTER_FLIGHTS, FLIGHT_NUMBER, FLIGHT_DEST_DEPART, FLIGHT_Date_Time);
        setupTableView();
    }

    @FXML
    void onTypeSelected(ActionEvent e) {
        switch (searchComboBox.getSelectionModel().getSelectedItem()) {
            case MASTER_FLIGHTS:
                destComboBox.setDisable(true);
                departComboBox.setDisable(true);
                timePicker.setDisable(true);
                datePicker.setDisable(true);
                flightNum.setDisable(true);
                break;
            case FLIGHT_NUMBER:
                destComboBox.setDisable(true);
                departComboBox.setDisable(true);
                timePicker.setDisable(true);
                datePicker.setDisable(true);
                flightNum.setDisable(false);
                break;
            case FLIGHT_DEST_DEPART:
                flightNum.setDisable(true);
                destComboBox.setDisable(false);
                departComboBox.setDisable(false);
                timePicker.setDisable(false);
                datePicker.setDisable(false);
                break;
            case FLIGHT_Date_Time:
                flightNum.setDisable(true);
                destComboBox.setDisable(true);
                departComboBox.setDisable(true);
                timePicker.setDisable(false);
                datePicker.setDisable(false);
                break;
            default:
        }
    }

    private void setupTableView() {

    }

    @FXML
    void manageFlight(ActionEvent event) {

    }

    @FXML
    void searchClicked(ActionEvent event) {

    }
}
