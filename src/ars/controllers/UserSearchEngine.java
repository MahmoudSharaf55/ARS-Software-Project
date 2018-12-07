package ars.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserSearchEngine implements Initializable {
    @FXML
    private JFXComboBox<String> searchBy;

    @FXML
    private JFXComboBox<String> departure;

    @FXML
    private JFXComboBox<String> destination;

    @FXML
    private JFXTextField flightNumberField;

    @FXML
    private JFXButton displayAllFlights;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBy.getItems().addAll("Departure & Destination","Flight Number");
        searchBy.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.intValue()){
                case 0:
                    departure.setDisable(false);
                    destination.setDisable(false);
                    flightNumberField.setDisable(true);
                    break;
                case 1:
                    departure.setDisable(true);
                    destination.setDisable(true);
                    flightNumberField.setDisable(false);
                    break;
            }
        });
    }
}
