package ars.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        } else {
            //TODO: Sharaf Load user scene here.

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
