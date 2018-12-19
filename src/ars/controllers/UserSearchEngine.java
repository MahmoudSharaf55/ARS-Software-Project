package ars.controllers;

import ars.models.Flight;
import ars.models.Master;
import ars.models.Ticket;
import ars.utils.DBConnection;
import ars.utils.FlightDatabaseAPI;
import ars.utils.UtilityServices;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class UserSearchEngine implements Initializable {
    ArrayList<Flight> flightArrayList = new ArrayList<>();
    @FXML
    private JFXComboBox<String> searchBy;
    @FXML
    private JFXComboBox<String> departure;
    @FXML
    private JFXComboBox<String> destination;
    @FXML
    private JFXTextField flightNumberField;
    @FXML
    private JFXButton displayFlightsBtn;
    @FXML
    private TableView<Flight> flightTable;
    @FXML
    private TableColumn<Flight, String> departureCol;
    @FXML
    private TableColumn<Flight, String> flightNumber;
    @FXML
    private TableColumn<Flight, String> destinationCol;
    @FXML
    private TableColumn<Flight, Date> dateCol;
    @FXML
    private TableColumn<Flight, Integer> seatsCol;
    @FXML
    private TableColumn<Flight, Integer> priceCol;
    @FXML
    private TableColumn<Flight, String> companyCol;
    @FXML
    private StackPane stackpane;

    public void displayFlights(ActionEvent e) {
        switch (searchBy.getSelectionModel().getSelectedIndex()) {
            case 0:
                if (departure.getSelectionModel().getSelectedItem() != null && destination.getSelectionModel().getSelectedItem() != null) {
                    getFlightsByDepartDest(departure.getSelectionModel().getSelectedItem(), destination.getSelectionModel().getSelectedItem());
                } else {
                    UtilityServices.displayDialog(new Text("Check Your Inputs"), new Text("Make sure that you selected a valid departure and destination type!"), stackpane);
                }
                break;
            case 1:
                if (!flightNumberField.getText().isEmpty()) {
                    getFlightsByFlightNumber(flightNumberField.getText());
                } else {
                    UtilityServices.displayDialog(new Text("Check Your Inputs"), new Text("Make sure that you typed a valid flight number!"), stackpane);
                }
                break;
            case 2:
                getAllFlights();
                break;
            default:
                UtilityServices.displayDialog(new Text("Check Your Inputs"), new Text("Make sure that you selected a valid search type !"), stackpane);
        }
    }

    public void getFlightsByDepartDest(String src, String dest) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("select * from flight f join master m on f.master_id = m.id where f.src = ? and f.dest = ?");
            preparedStatement.setString(1, src);
            preparedStatement.setString(2, dest);
            ResultSet resultSet = preparedStatement.executeQuery();
            addRecords(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getFlightsByFlightNumber(String number) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("select * from flight f join master m on f.master_id = m.id where f.flightNumber = ?");
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            addRecords(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllFlights() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("select * from flight f join master m on f.master_id = m.id");
            ResultSet resultSet = preparedStatement.executeQuery();
            addRecords(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRecords(ResultSet resultSet) {
        flightArrayList.clear();
        try {
            while (resultSet.next()) {
                Flight flight = new Flight(resultSet.getString("flightNumber"), resultSet.getString("src"),
                        resultSet.getString("dest"), resultSet.getTimestamp("dateAndTime"),
                        resultSet.getInt("price"), resultSet.getInt("seats"),
                        resultSet.getInt("delay"), resultSet.getInt("master_id"));
                Master master = new Master(resultSet.getString("officeName"), resultSet.getString("phone"),
                        resultSet.getString("email"), resultSet.getString("password"));
                flight.setMaster(master);
                flightArrayList.add(flight);
                flightTable.setItems(FXCollections.observableArrayList(flightArrayList));
            }
            flightTable.setItems(FXCollections.observableArrayList(flightArrayList));
        } catch (SQLException e) {

        }
    }

    @FXML
    public void reserveTicket(ActionEvent e) {
        if (flightTable.getSelectionModel().getSelectedItem() != null) {
            if (flightTable.getSelectionModel().getSelectedItem().getSeats() > 0) {
                Flight.currentFlight = flightTable.getSelectionModel().getSelectedItem();
                if (Ticket.currentTicket != null) {
                    Ticket.currentTicket.setTicketID(-1);
                }
                UtilityServices.displayDialog(new Text("Notify"), new Text("Please .. Move To Booking Iicket To Complete Your Ticket Info"), stackpane);
                UtilityServices.button.setText("GO");
                UtilityServices.button.setOnAction(event1 -> {
                    UserController.openBookingTicket.fire();
                });
            } else
                UtilityServices.displayDialog(new Text("Warning"), new Text("No Availble Seats In This Flight. Please Choose Another One!"), stackpane);
        } else {
            UtilityServices.displayDialog(new Text("Check Your Choose"), new Text("Please Select A Flight !"), stackpane);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBy.getItems().addAll("Departure & Destination", "Flight Number", "All Flights");
        ArrayList<String> ArrayList = new ArrayList<>();
        ResultSet resultSet = FlightDatabaseAPI.getAllAirports();
        try {
            while (resultSet.next()) {
                ArrayList.add(resultSet.getString(1));
            }
        } catch (SQLException s) {
            System.out.println();
        }
        departure.getItems().setAll(FXCollections.observableList(ArrayList));
        destination.getItems().setAll(FXCollections.observableList(ArrayList));
        searchBy.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.intValue()) {
                case 0:
                    departure.setDisable(false);
                    destination.setDisable(false);
                    flightNumberField.setDisable(true);
                    break;
                case 1:
                    departure.setDisable(true);
                    destination.setDisable(true);
                    flightNumberField.setDisable(false);
                    break;
                case 2:
                    departure.setDisable(true);
                    destination.setDisable(true);
                    flightNumberField.setDisable(true);
                    break;
            }
        });
        flightNumber.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("seats"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        departureCol.setCellValueFactory(new PropertyValueFactory<>("src"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("dest"));
        companyCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Flight, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Flight, String> param) {
                ObservableValue<String> value = new SimpleStringProperty(param.getValue().getMaster().getOfficeName());
                return value;
            }
        });
    }

}
