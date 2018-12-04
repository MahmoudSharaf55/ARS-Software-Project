/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ars.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import ars.models.Master;
import ars.models.User;
import ars.utils.*;
import com.jfoenix.controls.*;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    Pane signupUserPane, signupMasterPane;
    @FXML
    JFXToggleButton toggleSwitch;
    @FXML
    JFXTextField userName, userEmail, masterName, masterEmail, masterPhone;
    @FXML
    JFXPasswordField userPassword, masterPassword;
    @FXML
    JFXDatePicker userDate;
    @FXML
    JFXButton userSignupBtn, masterSignupBtn;

    private FadeTransition ft;
    private double xOffset1 = 0;
    private double yOffset1 = 0;

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
    public void onBackClicked() {
        LoginController.signUpStage.close();
        LoginController.loginStage.show();
    }

    @FXML
    public void onSignupMasterClicked() {
        if (masterName.getText().isEmpty()) {
            masterName.setUnFocusColor(Paint.valueOf("#ab0529"));
        }

        if (masterPhone.getText().isEmpty()) {
            masterPhone.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        String mailMuster=masterEmail.getText();
        if (masterEmail.getText().isEmpty()|| !mailMuster.contains("@")) {
            masterEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (masterPassword.getText().isEmpty()) {
            masterPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
        }

        if (!masterName.getText().isEmpty() && !masterPhone.getText().isEmpty() && mailMuster.contains("@") && !masterPassword.getText().isEmpty()) {
            int flag = 0;
            try {
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select email from master;");
                while (resultSet.next()) {
                    if (resultSet.getString("email").equals(masterEmail.getText())) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    masterEmail.setUnFocusColor(Paint.valueOf("#009688"));
                    String encrypted = CipherEncryptionAndDecryption.encrypt(masterPassword.getText(), "team");
                    Master master = new Master(masterName.getText(), masterPhone.getText(), masterEmail.getText(), encrypted);
                    AuthMaster.signupMaster(master);
                    openUserOrMaster("../fxml/master.fxml","Master");

                } else {
                    masterEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        masterFieldFocusColorChanged();
    }

    @FXML
    public void onSignupUserClicked() {
        if (userName.getText().isEmpty()) {
            userName.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (userDate.getValue() == null) {
            userDate.setDefaultColor(Paint.valueOf("#ab0529"));
        }
        if (userGender.getSelectionModel().getSelectedItem() == null) {
            userGender.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        String emailUser =userEmail.getText();
        if (userEmail.getText().isEmpty()||!emailUser.contains("@")) {
            userEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
        }
        if (userPassword.getText().isEmpty()) {
            userPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
        }



        if (!userName.getText().isEmpty() && userDate.getValue() != null && userGender.getSelectionModel().getSelectedItem() != null && emailUser.contains("@") && !userPassword.getText().isEmpty()) {
            int flag = 0;
            try {
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select email from user;");
                while (resultSet.next()) {
                    if (resultSet.getString("email").equals(userEmail.getText())) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    userEmail.setUnFocusColor(Paint.valueOf("#009688"));
                    String encrypted = CipherEncryptionAndDecryption.encrypt(userPassword.getText(), "team");
                    User user = new User(userName.getText(), Date.valueOf(userDate.getValue()), userGender.getSelectionModel().getSelectedItem().toString(), userEmail.getText(), encrypted, 0);
                    AuthUser.signupUser(user);
                    openUserOrMaster("../fxml/user.fxml","User");
                } else {
                    userEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
        userFieldFocusColorChanged();
    }

    /**
     * Starts user or master scene
     *
     * @param path the path of the fxml file to load
     */
    public void openUserOrMaster(String path ,String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root;
            root = (Parent) fxmlLoader.load();
            Stage userStage = new Stage();
            userStage.setScene(new Scene(root, 1044, 662));
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset1 = event.getSceneX();
                    yOffset1 = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    userStage.setX(event.getScreenX() - xOffset1);
                    userStage.setY(event.getScreenY() - yOffset1);
                }
            });
            userStage.initStyle(StageStyle.UNDECORATED);
            userStage.setTitle(title);
            userStage.getIcons().add(new Image("ars/Resources/iFlyIcon.png"));
            userStage.setResizable(false);
            userStage.show();
            LoginController.signUpStage.close();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void masterFieldFocusColorChanged() {
        ChangeListener<Boolean> fieldsFocus = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false) {
                    if (masterName.getText().isEmpty()) {
                        masterName.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        masterName.setUnFocusColor(Paint.valueOf("#009688"));
                    }

                    if (masterPhone.getText().isEmpty()) {
                        masterPhone.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        masterPhone.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (masterEmail.getText().isEmpty()) {
                        masterEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        masterEmail.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (masterPassword.getText().isEmpty()) {
                        masterPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
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
    public void userFieldFocusColorChanged() {
        ChangeListener<Boolean> fieldsFocus = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false) {
                    if (userName.getText().isEmpty()) {
                        userName.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        userName.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (userDate.getValue() == null) {
                        userDate.setDefaultColor(Paint.valueOf("#ab0529"));
                    } else {
                        userDate.setDefaultColor(Paint.valueOf("#009688"));
                    }
                    if (userGender.getSelectionModel().getSelectedItem() == null) {
                        userGender.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        userGender.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (userEmail.getText().isEmpty()) {
                        userEmail.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
                        userEmail.setUnFocusColor(Paint.valueOf("#009688"));
                    }
                    if (userPassword.getText().isEmpty()) {
                        userPassword.setUnFocusColor(Paint.valueOf("#ab0529"));
                    } else {
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
        userGender.getItems().addAll("Male", "Female");
        toggleSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                fadeTransitionOut(signupUserPane);
                signupUserPane.setVisible(false);
                fadeTransitionIn(signupMasterPane);
                signupMasterPane.setVisible(true);
            } else {
                fadeTransitionOut(signupMasterPane);
                signupMasterPane.setVisible(false);
                fadeTransitionIn(signupUserPane);
                signupUserPane.setVisible(true);
            }
        });
    }

}
