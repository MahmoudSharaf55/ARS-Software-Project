/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ars.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import ars.utils.cipherEncryptionAndDecryption;
import ars.utils.masterDBC;
import ars.utils.userDBC;
import com.jfoenix.controls.*;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
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
    JFXComboBox<Object> userGender;
    @FXML
    Pane signupUserPane,signupMasterPane;
    @FXML
    JFXToggleButton toggleSwitch;
    @FXML
    JFXTextField userName,userEmail,masterName,masterEmail,masterPhone;
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

        if (masterPhone.getText().isEmpty()){
            masterPhone.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (masterEmail.getText().isEmpty()){
            masterEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (masterPassword.getText().isEmpty()){
            masterPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (!masterName.getText().isEmpty()&&!masterPhone.getText().isEmpty()&&!masterEmail.getText().isEmpty()&&!masterPassword.getText().isEmpty()){
            int flag = 0;
            try {
                Connection connection = masterDBC.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select email from master;");
                while (resultSet.next()){
                    if (resultSet.getString("email").equals(masterEmail.getText())){
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0){
                    masterEmail.setUnFocusColor(Paint.valueOf("#009688"));
                    String encrypted = cipherEncryptionAndDecryption.encrypt(masterPassword.getText(),"team");
                    masterDBC.inserMaster(masterName.getText(), masterPhone.getText(), masterEmail.getText(), encrypted);
                }
                else{
                    masterEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
                }
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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
            int flag = 0;
            try {
                Connection connection = userDBC.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select email from user;");
                while (resultSet.next()){
                    if (resultSet.getString("email").equals(userEmail.getText())){
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0){
                    userEmail.setUnFocusColor(Paint.valueOf("#009688"));
                    String encryoted = cipherEncryptionAndDecryption.encrypt(userPassword.getText(),"team");
                    userDBC.insertUser(userName.getText(),userDate.getValue().toString(),userGender.getSelectionModel().getSelectedItem().toString(),userEmail.getText(),encryoted);
                }
                else{
                    userEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
                }
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

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

                    if (masterPhone.getText().isEmpty()){
                        masterPhone.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                    else {
                        masterPhone.setUnFocusColor(Paint.valueOf("#009688"));
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
        masterPhone.focusedProperty().addListener(fieldsFocus);

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
