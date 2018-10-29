package ars.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.events.JFXDrawerEvent;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.NavigationFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class UserController implements Initializable {
    @FXML
    JFXDrawer drawer;
    @FXML
    JFXHamburger hamburger;
    @FXML
    Pane container;
    @FXML
    Label toolbarTitle;
    Node containerFXML;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawer.setVisible(false);
        try {
            Node containerFXML = FXMLLoader.load(getClass().getResource("../fxml/user_dashboard.fxml"));
            container.getChildren().setAll(containerFXML);
            VBox sidePane = (VBox) FXMLLoader.load(getClass().getResource("../fxml/user_sidepane.fxml"));
            drawer.setSidePane(sidePane);
            setAnchorPaneListeners(sidePane);

        } catch (IOException e) {
            e.printStackTrace();
        }

        hamburger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hamburger.setVisible(false);
                drawer.setVisible(true);
                drawer.open();
            }
        });
        drawer.setOnDrawerClosed(new EventHandler<JFXDrawerEvent>() {
            @Override
            public void handle(JFXDrawerEvent event) {
                hamburger.setVisible(true);
                drawer.setVisible(false);


            }

        });
    }
    private void setScene(String path, String title) {
        try {
            containerFXML = FXMLLoader.load(getClass().getResource(path));
            container.getChildren().setAll(containerFXML);
            toolbarTitle.setText(title);
            drawer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    private void setAnchorPaneListeners(VBox vBox) {
        vBox.getChildren().forEach(node -> {
            if (node.getAccessibleText() != null){
                node.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
                    switch (node.getAccessibleText()){
                        case "dashboard":
                            node.setFocusTraversable(false);
                            setScene("../fxml/user_dashboard.fxml","Dashboard");
                            break;
                        case "search":
                            node.setFocusTraversable(false);
                            setScene("../fxml/user_searchEngine.fxml","Search Engine");
                            break;
                        case "editing_profile":
                            node.setFocusTraversable(false);
                            setScene("../fxml/user_editprofile.fxml","Edit Profile");
                            break;
                        case "booking":
                            node.setFocusTraversable(false);
                            setScene("../fxml/user_bookingTicket.fxml","Booking Ticket");
                            break;
                        case "editing_ticket":
                            node.setFocusTraversable(false);
                            setScene("../fxml/user_editingTicket.fxml","Editing Ticket");
                            break;
                    }
                });
                if (node.getAccessibleText().equals("hbox")){
                    ((HBox)node).getChildren().forEach(node1 -> {
                        node1.addEventHandler(MouseEvent.MOUSE_CLICKED,event1 ->{
                            switch (node1.getAccessibleText()){
                                case "exit":
                                    drawer.close();
                                    Platform.exit();
                                    break;
                                case "logout":
                                    drawer.close();
                                    LoginController.loginStage.show();
                                    ((Stage)node.getScene().getWindow()).close();
                                    break;
                            }
                        } );
                    });
                }
            }
        });
    }
}
