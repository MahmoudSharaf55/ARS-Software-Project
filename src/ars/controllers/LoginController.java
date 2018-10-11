package  ars.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class LoginController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    public static Stage signUpStage;
    public static Stage loginStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void exitAction() {
        Platform.exit();
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
