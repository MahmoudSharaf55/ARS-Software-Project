package ars.controllers;

import ars.models.Flight;
import ars.utils.AuthMaster;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.events.JFXDrawerEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MasterController implements Initializable {

    @FXML
    private   Label toolbarTitle;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Pane container;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawer.setVisible(false);
        try {
            Node dashboard = FXMLLoader.load(getClass().getResource("../fxml/master_dashboard.fxml"));
            container.getChildren().setAll(dashboard);
            VBox sidePane = (VBox) FXMLLoader.load(getClass().getResource("../fxml/master_sidepane.fxml"));
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


    /**
     * @param vBox
     */

    private void setAnchorPaneListeners(VBox vBox) {
        for (Node node : vBox.getChildren()) {
            if (node.getAccessibleText() != null) {
                //This Will get the childes  inside the HBox at the bottom of the Pane
                if (node instanceof HBox) {
                    for (Node hBoxChild : ((HBox) node).getChildren()) {
                        if (hBoxChild.getAccessibleText() != null) {
                            hBoxChild.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                switch (hBoxChild.getAccessibleText()) {
                                    case "exit":
                                        drawer.close();
                                        Platform.exit();
                                        break;
                                    case "logout":
                                        drawer.close();
                                        LoginController.loginStage.show();
                                        ((Stage) node.getScene().getWindow()).close();

                                    default:
                                        System.out.println("Error Master");
                                }
                            });
                        }
                    }
                } else if (node instanceof BorderPane) {
                    for (Node borderPaneChild : ((BorderPane) node).getChildren()) {
                        if (borderPaneChild.getAccessibleText() != null) {
                            switch (borderPaneChild.getAccessibleText()) {
                                case "name":
                                    System.out.println(AuthMaster.currentMaster.getOfficeName());
                                    ((Label) borderPaneChild).setText(AuthMaster.currentMaster.getOfficeName().toUpperCase());
                                    break;
                                default:
                                    System.out.println("Error Master");
                            }
                        }
                    }
                } else {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getAccessibleText()) {
                            case "dashboard":
                                node.requestFocus();
                                node.setFocusTraversable(false);
                                openScene("../fxml/master_dashboard.fxml", "Dashboard");
                                break;
                            case "search":
                                node.requestFocus();
                                node.setFocusTraversable(false);
                                openScene("../fxml/master_search.fxml", "Search Engine");
                                break;
                            case "ADDFLIGHTS":
                                node.requestFocus();
                                node.setFocusTraversable(false);
                                openScene("../fxml/master_flight_add.fxml", "Add Flight");
                                break;
                            case "PASSENGERS":
                                node.requestFocus();
                                node.setFocusTraversable(false);
                                openScene("../fxml/master_passengers.fxml", "Passengers");
                                break;
                            case "Manage_Flights":
                                node.requestFocus();
                                node.setFocusTraversable(false);
                                openScene("../fxml/master_manage_flights.fxml", "Manage Flights");
                                break;
                            case "EDIT_PROFILE":
                                node.requestFocus();
                                node.setFocusTraversable(false);
                                openScene("../fxml/master_edit_profile.fxml", "Edit Profile");
                                break;
                            default:
                                System.out.println("Error Master");
                        }

                    });
                }
            }
        }
    }


    /**
     * Function for loading nodes into the container :)
     *
     * @param path  the path of the fxml file
     * @param title the title for the toolbar
     */

      public void openScene(String path, String title) {
        try {
            Node node = FXMLLoader.load(getClass().getResource(path));
            container.getChildren().setAll(node);
            toolbarTitle.setText(title);
            drawer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
