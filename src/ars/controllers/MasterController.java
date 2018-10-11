package ars.controllers;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MasterController implements Initializable {


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

        try {
            Node dashboard = FXMLLoader.load(getClass().getResource("../fxml/master_dashboard.fxml"));
            container.getChildren().setAll(dashboard);
            VBox sidePane = (VBox) FXMLLoader.load(getClass().getResource("../fxml/master_sidepane.fxml"));
            drawer.setSidePane(sidePane);
            setAnchorPaneListeners(sidePane);

        } catch (IOException e) {
            e.printStackTrace();
        }

        hamburger.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Class Master: Clicked ");
                hamburger.setVisible(false);

                drawer.open();
            }
        });
        drawer.setOnDrawerClosed(new EventHandler<JFXDrawerEvent>() {
            @Override
            public void handle(JFXDrawerEvent event) {
                hamburger.setVisible(true);

            }

        });
    }

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
                                    default:
                                        System.out.println("Error Master");
                                }
                            });
                        }
                    }
                } else {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getAccessibleText()) {
                            case "dashboard":
                                node.requestFocus();
                                node.setFocusTraversable(false);
                                try {
                                    Node dashboard = FXMLLoader.load(getClass().getResource("../fxml/master_dashboard.fxml"));
                                    container.getChildren().setAll(dashboard);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                drawer.close();

                                break;
                            default:
                                System.out.println("Error Master");
                        }
                    });
                }
            }
        }
    }


}
