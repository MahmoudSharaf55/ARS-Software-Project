/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ars.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
    public void onBackClicked(){
        LoginController.signUpStage.close();
        LoginController.loginStage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userGender.getItems().addAll("Male","Female");
    }    
    
}
