package ars.controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSpinner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MasterPassengersController implements Initializable {
    /**
     * TODO: Mohaned
     */
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXListView<Label> passengersListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinner.setVisible(false);
        passengersListView.setVerticalGap(10.0);
        passengersListView.setExpanded(true);
        for (int i = 0; i < 100; i++) {
            try {
                Node graphic = new ImageView(new Image(new FileInputStream("src/ars/Resources/user5.png")));
                Label label = new Label("Passenger " + i, graphic);
                label.setStyle("-fx-text-fill: white;");
                passengersListView.getItems().add(label);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
