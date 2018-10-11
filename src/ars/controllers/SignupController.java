/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ars.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author MOHNAD
 */
public class SignupController implements Initializable {

    /**
     * Sharaf
     */
    @FXML
    JFXComboBox<Object> userGender;
    @FXML
    Pane signupUserPane,signupMasterPane;
    @FXML
    JFXToggleButton toggleSwitch;

    private FadeTransition ft;

    public void fadeTransitionIn(Node n) {
        ft = new FadeTransition(Duration.seconds(0.4), n);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    public void fadeTransitionOut(Node n) {
        ft = new FadeTransition(Duration.seconds(0.4), n);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
    }
    @FXML
    public void onBackClicked(){
        LoginController.signUpStage.close();
        LoginController.loginStage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userGender.getItems().addAll("Male","Female");
        toggleSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                fadeTransitionOut(signupUserPane);
                signupUserPane.setVisible(false);
                fadeTransitionIn(signupMasterPane);
                signupMasterPane.setVisible(true);
            }
            else{
                fadeTransitionOut(signupMasterPane);
                signupMasterPane.setVisible(false);
                fadeTransitionIn(signupUserPane);
                signupUserPane.setVisible(true);
            }
        });
    }    
    
}
