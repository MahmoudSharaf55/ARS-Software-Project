package ars.controllers;

import ars.models.Master;
import ars.models.Ticket;
import ars.models.User;
import ars.utils.AuthMaster;
import ars.utils.FlightDatabaseAPI;
import ars.utils.UtilityServices;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MasterPassengersController implements Initializable {
    /**
     * TODO: Mohaned
     */
    @FXML
    StackPane sp;
    @FXML
    private JFXTextField flightNumTextField;

    @FXML
    private JFXListView<Label> passengersListView;

    @FXML
    private JFXTextField fullNameTextField;

    @FXML
    private JFXTextField emailTextField;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXComboBox<String> userGender;

    @FXML
    private JFXTextField ticketNumberTF;

    @FXML
    private JFXSpinner spinner;
    private ArrayList<Ticket> ticketArrayList;
    private ArrayList<User> userArrayList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userArrayList = new ArrayList<>();
        ticketArrayList = new ArrayList<>();
        userGender.getItems().addAll("Male", "Female");
        spinner.setVisible(false);
        passengersListView.setVerticalGap(10.0);
        passengersListView.setExpanded(true);


        passengersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
            @Override
            public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
                if (newValue != null) {
                    User user = userArrayList.get(Integer.parseInt(newValue.getId()));
                    Ticket ticket = ticketArrayList.get(Integer.parseInt(newValue.getId()));
                    emailTextField.setText(user.getEmail());
                    datePicker.setValue(user.getDateOfBirth().toLocalDate());
                    userGender.getSelectionModel().select(user.getGender());
                    fullNameTextField.setText(user.getName());
                    ticketNumberTF.setText(ticket.getTicketID() + "");
                } else {
                    emailTextField.setText("");
                    datePicker.setValue(null);
                    userGender.getSelectionModel().clearSelection();
                    fullNameTextField.setText("");
                    ticketNumberTF.setText("");
                }

            }
        });

    }


    @FXML
    void edit(ActionEvent event) {
        if (!passengersListView.getSelectionModel().isEmpty()) {
            User user = userArrayList.get(Integer.parseInt(passengersListView.getSelectionModel().getSelectedItem().getId()));
            user.setName(fullNameTextField.getText());
            user.setDateOfBirth(Date.valueOf(datePicker.getValue()));
            user.setGender(userGender.getSelectionModel().getSelectedItem());
            user.setEmail(emailTextField.getText());
           if (FlightDatabaseAPI.updateUser(user)>0){
               UtilityServices.displayDialog(new Text("Successfully Updated"), new Text("User Successfully Updated"), sp);
               searchPressed(new ActionEvent());
           }else {
               UtilityServices.displayDialog(new Text("Check Your inputs !"), new Text("No Changes !"), sp);
           }


        } else {
            UtilityServices.displayDialog(new Text("Check Your inputs !"), new Text("Please Select Passenger to be able to edit"), sp);
        }

    }

    @FXML
    void searchPressed(ActionEvent event) {
        spinner.setVisible(true);
        passengersListView.getSelectionModel().clearSelection();
        passengersListView.getItems().clear();
        ticketArrayList.clear();
        userArrayList.clear();
        if (!flightNumTextField.getText().isEmpty()) {
            ResultSet resultSet = FlightDatabaseAPI.getPassengersUsingFlightNumber(flightNumTextField.getText());
            try {
                int i = 0;
                while (resultSet.next()) {
                    if (resultSet.getInt("master_id") == AuthMaster.currentMaster.getMasterID()) {
                        Ticket ticket = new Ticket(resultSet.getInt("ticket_number"), resultSet.getInt("id"), resultSet.getString("flightNumber"));
                        User user = new User(resultSet.getString("name"), resultSet.getDate("date"), resultSet.getString("gender"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getInt("rating"));
                        user.setUserID(resultSet.getInt("id"));
                        userArrayList.add(user);
                        ticketArrayList.add(ticket);
                        try {
                            Node graphic = new ImageView(new Image(new FileInputStream("src/ars/Resources/user5.png")));
                            Label label = new Label(user.getName(), graphic);
                            label.setId(i + "");
                            label.setStyle("-fx-text-fill: white;");
                            passengersListView.getItems().add(label);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        i++;
                    } else {
                        UtilityServices.displayDialog(new Text("You Don't Have The Required Permissions !"), new Text("You Don't Own This Flight !"), sp);
                        break;
                    }
                }
            } catch (SQLException e) {
                UtilityServices.displayDialog(new Text("Check Your inputs !"), new Text("Please enter a valid flight number and try again"), sp);

            }

        } else {
            UtilityServices.displayDialog(new Text("Check Your inputs !"), new Text("Please enter a valid flight number and try again"), sp);

        }

        spinner.setVisible(false);
    }
}
