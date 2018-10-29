/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ars.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ars.utils.cipherEncryptionAndDecryption;
import ars.utils.masterDBC;
import ars.utils.userDBC;
import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
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
    JFXComboBox<Object> userGender,masterGender;
    @FXML
    Pane signupUserPane,signupMasterPane;
    @FXML
    JFXToggleButton toggleSwitch;
    @FXML
    JFXTextField userName,userEmail,masterName,masterEmail;
    @FXML
    JFXPasswordField userPassword,masterPassword;
    @FXML
    JFXDatePicker userDate;
    @FXML
    JFXButton userSignupBtn,masterSignupBtn;

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
    @FXML
    public void onSignupMasterClicked(){
        if (masterName.getText().isEmpty()){
            masterName.setUnFocusColor(Paint.valueOf("#ab0529"));
        }

        if (masterGender.getSelectionModel().getSelectedItem() == null){
            masterGender.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (masterEmail.getText().isEmpty()){
            masterEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (masterPassword.getText().isEmpty()){
            masterPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (!masterName.getText().isEmpty()&&masterGender.getSelectionModel().getSelectedItem()!=null&&!masterEmail.getText().isEmpty()&&!masterPassword.getText().isEmpty()){
            String encrypted = cipherEncryptionAndDecryption.encrypt(masterPassword.getText(),"team");
            masterDBC.inserMaster(masterName.getText(), masterGender.getSelectionModel().getSelectedItem().toString(), masterEmail.getText(), encrypted);
        }

        masterfieldFocusColorChanged();
    }
    @FXML
    public void onSignupUserClicked(){
        if (userName.getText().isEmpty()){
            userName.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (userDate.getValue() == null){
            userDate.setDefaultColor(Paint.valueOf("#ab0529"));
        }
        if (userGender.getSelectionModel().getSelectedItem() == null){
            userGender.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (userEmail.getText().isEmpty()){
            userEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (userPassword.getText().isEmpty()){
            userPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (!userName.getText().isEmpty()&&userDate.getValue()!=null&&userGender.getSelectionModel().getSelectedItem()!=null&&!userEmail.getText().isEmpty()&&!userPassword.getText().isEmpty()){
            String encryoted = cipherEncryptionAndDecryption.encrypt(userPassword.getText(),"team");
            userDBC.insertUser(userName.getText(),userDate.getValue().toString(),userGender.getSelectionModel().getSelectedItem().toString(),userEmail.getText(),encryoted);
        }
        userfieldFocusColorChanged();
    }

    @FXML
    public void masterfieldFocusColorChanged(){
        ChangeListener<Boolean> fieldsFocus = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false){
                    if (masterName.getText().isEmpty()){
                        masterName.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                    else {
                        masterName.setUnFocusColor(Paint.valueOf("#009688"));
                    }

                    if (masterGender.getSelectionModel().getSelectedItem() == null){
                        masterGender.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                    else {
                        masterGender.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (masterEmail.getText().isEmpty()){
                        masterEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                    else {
                        masterEmail.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (masterPassword.getText().isEmpty()){
                        masterPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                    else {
                        masterPassword.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                }
            }
        };
        masterName.focusedProperty().addListener(fieldsFocus);
        masterPassword.focusedProperty().addListener(fieldsFocus);
        masterEmail.focusedProperty().addListener(fieldsFocus);
        masterGender.focusedProperty().addListener(fieldsFocus);

    }

    @FXML
    public void userfieldFocusColorChanged(){
        ChangeListener<Boolean> fieldsFocus = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false){
                    if (userName.getText().isEmpty()){
                        userName.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                    else {
                        userName.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (userDate.getValue() == null){
                        userDate.setDefaultColor(Paint.valueOf("#ab0529"));
                    }
                    else {
                        userDate.setDefaultColor(Paint.valueOf("#009688"));
                    }
                    if (userGender.getSelectionModel().getSelectedItem() == null){
                        userGender.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                    else {
                        userGender.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (userEmail.getText().isEmpty()){
                        userEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                    else {
                        userEmail.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (userPassword.getText().isEmpty()){
                        userPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                    else {
                        userPassword.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                }
            }
        };
        userName.focusedProperty().addListener(fieldsFocus);
        userPassword.focusedProperty().addListener(fieldsFocus);
        userEmail.focusedProperty().addListener(fieldsFocus);
        userGender.focusedProperty().addListener(fieldsFocus);
        userDate.focusedProperty().addListener(fieldsFocus);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userGender.getItems().addAll("Male","Female");
        masterGender.getItems().addAll("Male","Female");
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
