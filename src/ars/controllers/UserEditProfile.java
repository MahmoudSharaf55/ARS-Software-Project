package ars.controllers;

import ars.models.User;
import ars.utils.AuthUser;
import ars.utils.CipherEncryptionAndDecryption;
import ars.utils.DBConnection;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UserEditProfile implements Initializable {
    /** TODO: Sedky
    Update user profile in to DB and Current user
     **/
    @FXML
    JFXComboBox<Object> editUserGender;
    @FXML
    JFXTextField editUserName,editUserMail;
    @FXML
    JFXPasswordField oldPassword,newPassword;
    @FXML
    JFXDatePicker editUserDOB;
    @FXML
    JFXButton submit;





    @FXML
    public void onUserEditProfile() {
        if (editUserName.getText().isEmpty()) {
            editUserName.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (editUserDOB.getValue() == null) {
            editUserDOB.setDefaultColor(Paint.valueOf("#ab0529"));
        }
        if (editUserGender.getSelectionModel().getSelectedItem() == null) {
            editUserGender.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (editUserMail.getText().isEmpty() || !editUserMail.getText().contains("@")) {
            editUserMail.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        /*if (oldPassword.getText().isEmpty()) {
            oldPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (newPassword.getText().isEmpty()) {
            newPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
        }*/


        if (!editUserName.getText().isEmpty() && editUserDOB.getValue() != null && editUserGender.getSelectionModel().getSelectedItem() != null && editUserMail.getText().contains("@")) {
            int passFlag = 0;
            if (editUserMail.getText().equals(AuthUser.currentUser.getEmail())) {
                editUserMail.setUnFocusColor(Paint.valueOf("#009688"));
                Connection connection = DBConnection.getConnection();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("update user set name=?,date=?,gender=?,email=? where id=?");
                    preparedStatement.setString(1, editUserName.getText());
                    preparedStatement.setDate(2, Date.valueOf(editUserDOB.getValue()));
                    preparedStatement.setString(3, editUserGender.getSelectionModel().getSelectedItem().toString());
                    preparedStatement.setString(4, editUserMail.getText());
                    preparedStatement.setInt(5, AuthUser.currentUser.getUserID());
                    preparedStatement.executeUpdate();
                    AuthUser.currentUser.setName(editUserName.getText());
                    AuthUser.currentUser.setDateOfBirth(Date.valueOf(editUserDOB.getValue()));
                    AuthUser.currentUser.setGender(editUserGender.getSelectionModel().getSelectedItem().toString());
                    AuthUser.currentUser.setEmail(editUserMail.getText());
                    UserController.sideNameLbl.setText(AuthUser.currentUser.getName());
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                if (!oldPassword.getText().isEmpty()){
                    passFlag = 1;
                }
            } else if (!editUserMail.getText().equals(AuthUser.currentUser.getEmail())) {
                int flag = 0;
                try {
                    Connection connection = DBConnection.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select email from user;");
                    while (resultSet.next()) {
                        if (resultSet.getString("email").equals(editUserMail.getText()) && !editUserMail.getText().contains(("@"))) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        editUserMail.setUnFocusColor(Paint.valueOf("#009688"));
                            try {
                                PreparedStatement preparedStatement = connection.prepareStatement("update user set name=?,date=?,gender=?,email=? where id=?");
                                preparedStatement.setString(1, editUserName.getText());
                                preparedStatement.setDate(2, Date.valueOf(editUserDOB.getValue()));
                                preparedStatement.setString(3, editUserGender.getSelectionModel().getSelectedItem().toString());
                                preparedStatement.setString(4, editUserMail.getText());
                                preparedStatement.setInt(5, AuthUser.currentUser.getUserID());
                                preparedStatement.executeUpdate();
                                AuthUser.currentUser.setName(editUserName.getText());
                                AuthUser.currentUser.setDateOfBirth(Date.valueOf(editUserDOB.getValue()));
                                AuthUser.currentUser.setGender(editUserGender.getSelectionModel().getSelectedItem().toString());
                                AuthUser.currentUser.setEmail(editUserMail.getText());
                                UserController.sideNameLbl.setText(AuthUser.currentUser.getName());
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        }else {
                            editUserMail.setUnFocusColor(Paint.valueOf("#ab0529"));
                        }
                        if (!oldPassword.getText().isEmpty()){
                            passFlag = 1;
                        }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (passFlag == 1 && CipherEncryptionAndDecryption.encrypt(oldPassword.getText(),"team").equals(AuthUser.currentUser.getPassword())){
                oldPassword.setUnFocusColor(Paint.valueOf("#009688"));
                if (!newPassword.getText().isEmpty()){
                    newPassword.setUnFocusColor(Paint.valueOf("#009688"));
                    try{
                        Connection connection = DBConnection.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("update user set password = ? where id=?");
                        preparedStatement.setString(1, CipherEncryptionAndDecryption.encrypt(newPassword.getText(),"team"));
                        preparedStatement.setInt(2, AuthUser.currentUser.getUserID());
                        preparedStatement.executeUpdate();
                        AuthUser.currentUser.setPassword(CipherEncryptionAndDecryption.encrypt(newPassword.getText(),"team"));
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
                else{
                    newPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
                }
            }
            else {
                    oldPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
            }

        }
    }



/*
try {
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select email from user;");
                while (resultSet.next()) {
                    if (resultSet.getString("email").equals(editUserMail.getText())&&AuthUser.currentUser.getEmail().equals(editUserMail.getText())) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    editUserMail.setUnFocusColor(Paint.valueOf("#009688"));
                    if (CipherEncryptionAndDecryption.encrypt(oldPassword.getText(),"team").equals(AuthUser.currentUser.getPassword()))
                    {
                        try {
                            PreparedStatement preparedStatement=connection.prepareStatement("update user set name=?,date=?,gender=?,email=?,password=? where id=?");
                            String encrypted = CipherEncryptionAndDecryption.encrypt(newPassword.getText(), "team");
                            preparedStatement.setString(1,editUserName.getText());
                            preparedStatement.setDate(2,Date.valueOf(editUserDOB.getValue()));
                            preparedStatement.setString(3,editUserGender.getSelectionModel().getSelectedItem().toString());
                            preparedStatement.setString(4,editUserMail.getText());
                            preparedStatement.setString(5,encrypted);
                            preparedStatement.setInt(6,AuthUser.currentUser.getUserID());
                            preparedStatement.executeUpdate();
                            AuthUser.currentUser.setName(editUserName.getText());
                            AuthUser.currentUser.setDateOfBirth(Date.valueOf(editUserDOB.getValue()));
                            AuthUser.currentUser.setGender(editUserGender.getSelectionModel().getSelectedItem().toString());
                            AuthUser.currentUser.setEmail(editUserMail.getText());
                            AuthUser.currentUser.setPassword(encrypted);
                            changeSidePaneLabel();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else {
                        oldPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                } else {
                    editUserMail.setUnFocusColor(Paint.valueOf("#ab0529"));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

 */







    @FXML
    public void editUserFieldFocusColorChanged() {
        ChangeListener<Boolean> fieldsFocus = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false) {
                    if (editUserName.getText().isEmpty()) {
                        editUserName.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        editUserName.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (editUserDOB.getValue() == null) {
                        editUserDOB.setDefaultColor(Paint.valueOf("#ab0529"));
                    } else {
                        editUserDOB.setDefaultColor(Paint.valueOf("#009688"));
                    }
                    if (editUserGender.getSelectionModel().getSelectedItem() == null) {
                        editUserGender.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        editUserGender.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (editUserMail.getText().isEmpty()) {
                        editUserMail.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        editUserMail.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    /*if (oldPassword.getText().isEmpty()) {
                        oldPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        oldPassword.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (newPassword.getText().isEmpty()){
                        newPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else{
                        newPassword.setUnFocusColor(Paint.valueOf("#009688"));
                    }*/

                }
            }
        };
        editUserName.focusedProperty().addListener(fieldsFocus);
        //oldPassword.focusedProperty().addListener(fieldsFocus);
        //newPassword.focusedProperty().addListener(fieldsFocus);
        editUserMail.focusedProperty().addListener(fieldsFocus);
        editUserGender.focusedProperty().addListener(fieldsFocus);
        editUserDOB.focusedProperty().addListener(fieldsFocus);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        editUserGender.getItems().addAll("Male","Female");
        editUserName.setText(AuthUser.currentUser.getName());
        editUserMail.setText(AuthUser.currentUser.getEmail());

         editUserGender.getSelectionModel().select(AuthUser.currentUser.getGender());
         editUserDOB.setValue(AuthUser.currentUser.getDateOfBirth().toLocalDate());


    } }