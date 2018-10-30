package ars.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import ars.utils.DBConnection;
import ars.utils.cipherEncryptionAndDecryption;
import ars.utils.MasterAPI;
import ars.utils.UserAPI;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    private double xOffset1 = 0;
    private double yOffset1 = 0;
    public static Stage signUpStage;
    public static Stage loginStage;

    @FXML
    private JFXToggleButton toggleButton;
    @FXML
    JFXTextField emailTextField;
    @FXML
    JFXPasswordField passwordTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void exitAction() {
        Platform.exit();
    }

    @FXML
    public void onLoginButtonClicked(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        loginStage = (Stage) source.getScene().getWindow();
        if (toggleButton.isSelected()) {
            int flag = 0;
            try {
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select email,password from master;");
                while (resultSet.next()) {
                    if (emailTextField.getText().equals(resultSet.getString("email"))) {
                        emailTextField.setUnFocusColor(Paint.valueOf("#009688"));
                        if (cipherEncryptionAndDecryption.encrypt(passwordTextField.getText(), "team").equals(resultSet.getString("password"))) {
                            flag = 1;
                            passwordTextField.setUnFocusColor(Paint.valueOf("#009688"));
                            break;
                        } else {
                            System.out.println("password is not correct");
                            passwordTextField.setUnFocusColor(Paint.valueOf("#ab0529"));
                            break;
                        }
                    } else {
                        emailTextField.setUnFocusColor(Paint.valueOf("#ab0529"));
                        passwordTextField.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                }
                if (flag == 1) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/master.fxml"));
                        Parent root;
                        root = (Parent) fxmlLoader.load();
                        Stage masterStage = new Stage();
                        masterStage.setScene(new Scene(root, 1044, 662));
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
                                masterStage.setX(event.getScreenX() - xOffset1);
                                masterStage.setY(event.getScreenY() - yOffset1);
                            }
                        });
                        masterStage.initStyle(StageStyle.UNDECORATED);
                        masterStage.setTitle("Master");
                        masterStage.getIcons().add(new Image("ars/Resources/iFlyIcon.png"));
                        masterStage.setResizable(false);
                        masterStage.show();
                        loginStage.close();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } else {
            int flag = 0;
            try {
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select email,password from user;");
                while (resultSet.next()) {
                    if (emailTextField.getText().equals(resultSet.getString("email"))) {
                        emailTextField.setUnFocusColor(Paint.valueOf("#009688"));
                        if (cipherEncryptionAndDecryption.encrypt(passwordTextField.getText(), "team").equals(resultSet.getString("password"))) {
                            flag = 1;
                            passwordTextField.setUnFocusColor(Paint.valueOf("#009688"));
                            break;
                        } else {
                            System.out.println("password is not correct");
                            passwordTextField.setUnFocusColor(Paint.valueOf("#ab0529"));
                            break;
                        }
                    } else {
                        emailTextField.setUnFocusColor(Paint.valueOf("#ab0529"));
                        passwordTextField.setUnFocusColor(Paint.valueOf("#ab0529"));
                    }
                }
                if (flag == 1) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/user.fxml"));
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
                        userStage.setTitle("User");
                        userStage.getIcons().add(new Image("ars/Resources/iFlyIcon.png"));
                        userStage.setResizable(false);
                        userStage.show();
                        loginStage.close();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

    }

    @FXML
    public void onSignupClicked(ActionEvent ae) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/signup.fxml"));
            Parent root;
            root = (Parent) fxmlLoader.load();
            signUpStage = new Stage();
            signUpStage.setScene(new Scene(root, 418, 562));
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    signUpStage.setX(event.getScreenX() - xOffset);
                    signUpStage.setY(event.getScreenY() - yOffset);
                }
            });
            signUpStage.initStyle(StageStyle.UNDECORATED);
            signUpStage.setTitle("Sign Up");
            signUpStage.getIcons().add(new Image("ars/Resources/iFlyIcon.png"));
            signUpStage.setResizable(false);
            signUpStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Node source = (Node) ae.getSource();

        loginStage = (Stage) source.getScene().getWindow();

        loginStage.close();
    }
}
