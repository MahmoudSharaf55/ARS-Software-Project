package ars.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserEditProfile implements Initializable {
    /** TODO: Sedky
    Update user profile in to DB and Current user
     **/
    @FXML
    JFXComboBox<Object> userGender;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userGender.getItems().addAll("Male","Female");
    }
}
