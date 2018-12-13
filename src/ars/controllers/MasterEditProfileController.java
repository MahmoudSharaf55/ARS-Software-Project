package ars.controllers;


import ars.utils.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MasterEditProfileController implements Initializable {
    @FXML
    JFXTextField editMasterName, editMasterPhone, editMasterEmail;
    @FXML
    JFXPasswordField oldMasterPassword, newMasterPassword;
    @FXML
    JFXButton submitMaster;

    @FXML
    StackPane spMaster;
    @FXML
    public void onMasterEditProfile() {
        //System.out.println("moomdfnj");
        if (editMasterName.getText().isEmpty()) {
            UtilityServices.displayDialog(new Text("Error Check your inputs"), new Text("Please inter your name.. "), spMaster);
            editMasterName.setUnFocusColor(Paint.valueOf("#ab0529"));
        }


        if (editMasterEmail.getText().isEmpty() || !editMasterEmail.getText().contains("@")) {
            UtilityServices.displayDialog(new Text("Error Check your inputs"), new Text("Please inter your email.. "), spMaster);
            editMasterEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (editMasterPhone.getText().isEmpty()) {
            UtilityServices.displayDialog(new Text("Error Check your inputs"), new Text("Please inter your phone.. "), spMaster);
            editMasterPhone.setUnFocusColor(Paint.valueOf("#ab0529"));
        }





        //System.out.println("oooooooo");
        if (!editMasterName.getText().isEmpty() &&!editMasterPhone.getText().isEmpty()&& editMasterEmail.getText().contains("@")) {
            //System.out.println("ffgggffffffrr");

            int passFlag = 0;
            if (editMasterEmail.getText().equals(AuthMaster.currentMaster.getEmail())) {
                editMasterEmail.setUnFocusColor(Paint.valueOf("#009688"));
                Connection connection = DBConnection.getConnection();
                try {

                    PreparedStatement preparedStatement = connection.prepareStatement("update master set  officeName=?, phone=?, email=? where id=?");
                    preparedStatement.setString(1, editMasterName.getText());
                    preparedStatement.setString(2, editMasterPhone.getText());
                    preparedStatement.setString(3,editMasterEmail.getText());
                    preparedStatement.setInt(4, AuthMaster.currentMaster.getMasterID());
                    preparedStatement.executeUpdate();
                    AuthMaster.currentMaster.setOfficeName(editMasterName.getText());
                    AuthMaster.currentMaster.setPhone(editMasterPhone.getText());
                    AuthMaster.currentMaster.setEmail(editMasterEmail.getText());

                    UtilityServices.displayDialog(new Text("Confirm Update"), new Text("You are update Successfully...  "), spMaster);


                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                if (!oldMasterPassword.getText().isEmpty()) {
                    passFlag = 1;
                }
            } else if (!editMasterEmail.getText().equals(AuthMaster.currentMaster.getEmail())) {
               // System.out.println("emali equal mali");
                int flag = 0;
                try {
                    Connection connection = DBConnection.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select email from master;");
                    while (resultSet.next()) {
                        if (resultSet.getString("email").equals(editMasterEmail.getText()) && !editMasterEmail.getText().contains(("@"))) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        editMasterEmail.setUnFocusColor(Paint.valueOf("#009688"));
                        try {
                            PreparedStatement preparedStatement = connection.prepareStatement("update master set  officeName=?, phone=?, email=? where id=?");
                            preparedStatement.setString(1, editMasterName.getText());
                            preparedStatement.setString(2, editMasterPhone.getText());
                            preparedStatement.setString(3,editMasterEmail.getText());
                            preparedStatement.setInt(4, AuthMaster.currentMaster.getMasterID());
                            preparedStatement.executeUpdate();
                            AuthMaster.currentMaster.setOfficeName(editMasterName.getText());
                            AuthMaster.currentMaster.setPhone(editMasterPhone.getText());
                            AuthMaster.currentMaster.setEmail(editMasterEmail.getText());

                            UtilityServices.displayDialog(new Text("Confirm Update"), new Text("You are update Successfully...  "), spMaster);


                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        editMasterEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                    if (!oldMasterPassword.getText().isEmpty()) {
                        passFlag = 1;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (passFlag == 1 && CipherEncryptionAndDecryption.encrypt(oldMasterPassword.getText(), "team").equals(AuthMaster.currentMaster.getPassword())) {
                oldMasterPassword.setUnFocusColor(Paint.valueOf("#009688"));
                if (!newMasterPassword.getText().isEmpty()) {
                    newMasterPassword.setUnFocusColor(Paint.valueOf("#009688"));
                    try {
                        Connection connection = DBConnection.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("update master set password = ? where id=?");
                        preparedStatement.setString(1, CipherEncryptionAndDecryption.encrypt(newMasterPassword.getText(), "team"));
                        preparedStatement.setInt(2, AuthMaster.currentMaster.getMasterID());
                        preparedStatement.executeUpdate();
                        AuthMaster.currentMaster.setPassword(CipherEncryptionAndDecryption.encrypt(newMasterPassword.getText(), "team"));
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    newMasterPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
                }
            } else {
                oldMasterPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
            }

        }
    }

    @FXML
    public void editmasterFieldFocusColorChanged() {
        ChangeListener<Boolean> fieldsFocus = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false) {
                    if (editMasterName.getText().isEmpty()) {
                        editMasterName.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        editMasterName.setUnFocusColor(Paint.valueOf("#009688"));
                    }

                    if (editMasterEmail.getText().isEmpty()) {
                        editMasterEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        editMasterEmail.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (editMasterPhone.getText().isEmpty()) {
                        editMasterPhone.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        editMasterPhone.setUnFocusColor(Paint.valueOf("#009688"));
                    }


                }
            }
        };
        editMasterName.focusedProperty().addListener(fieldsFocus);

        editMasterEmail.focusedProperty().addListener(fieldsFocus);
        editMasterPhone.focusedProperty().addListener(fieldsFocus);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editMasterName.setText(AuthMaster.currentMaster.getOfficeName());
        editMasterPhone.setText(AuthMaster.currentMaster.getPhone());
        editMasterEmail.setText(AuthMaster.currentMaster.getEmail());

    }

}

