package ars.controllers;

import ars.models.Master;
import ars.models.User;
import ars.utils.AuthUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {
    /**
     * TODO: Sharaf
     * Load all users info in the Dashboard using Auth.currentUser object that contain all current user data
     */
    public User currentUser = AuthUser.currentUser;


    @FXML
    private Label nameLbl,emailLbl,dateLbl,genderLbl;
    @FXML
    private Rating ratingBar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameLbl.setText(currentUser.getName());
        emailLbl.setText(currentUser.getEmail());
        dateLbl.setText(currentUser.getDateOfBirth().toString());
        genderLbl.setText(currentUser.getGender());
        ratingBar.setRating(currentUser.getRating());

    }
}
