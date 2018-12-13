package ars.controllers;

import ars.models.Flight;
import ars.models.Master;
import ars.utils.AuthMaster;
import ars.utils.FlightDatabaseAPI;
import ars.utils.UtilityServices;
import com.jfoenix.controls.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class MasterSearchController implements Initializable {

    @FXML
    StackPane sp;
    @FXML
    private JFXComboBox<String> searchComboBox;
    @FXML
    private JFXTextField flightNum;
    @FXML
    private JFXComboBox<String> departComboBox;
    @FXML
    private JFXComboBox<String> destComboBox;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTimePicker timePicker;
    @FXML
    private TableView<Flight> dataTable;
    @FXML
    private JFXSpinner loadingSpinner;
    @FXML
    private TableColumn<Flight, String> flightNumber;

    @FXML
    private TableColumn<Flight, String> departure;

    @FXML
    private TableColumn<Flight, String> destination;

    @FXML
    private TableColumn<Flight, Integer> availableSeats;

    @FXML
    private TableColumn<Flight, Date> dateTimeRow;

    @FXML
    private TableColumn<Flight, Integer> price;

    @FXML
    private TableColumn<Flight, Integer> delayed;

    @FXML
    private TableColumn<Flight, String> officeName;
    private ArrayList<String> airportsList;

    private ArrayList<Flight> flightList;
    private final String MASTER_FLIGHTS = "Master Flights";
    private final String FLIGHT_NUMBER = "Flight Number";
    private final String FLIGHT_DEST_DEPART = "Flight Destination & Schedule";
    private final String FLIGHT_Date_Time = "Flight Date & Time";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchComboBox.getItems().setAll(MASTER_FLIGHTS, FLIGHT_NUMBER, FLIGHT_DEST_DEPART, FLIGHT_Date_Time);
        flightList = new ArrayList<>();
        airportsList = new ArrayList<>();


        ResultSet resultSet = FlightDatabaseAPI.getAllAirports();
        try {
            while (resultSet.next()) {
                airportsList.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        departComboBox.getItems().setAll(FXCollections.observableArrayList(airportsList));
        setupTableView();
    }

    @FXML
    void onTypeSelected(ActionEvent e) {
        switch (searchComboBox.getSelectionModel().getSelectedItem()) {
            case MASTER_FLIGHTS:
                destComboBox.setDisable(true);
                departComboBox.setDisable(true);
                timePicker.setDisable(true);
                datePicker.setDisable(true);
                flightNum.setDisable(true);
                break;
            case FLIGHT_NUMBER:
                destComboBox.setDisable(true);
                departComboBox.setDisable(true);
                timePicker.setDisable(true);
                datePicker.setDisable(true);
                flightNum.setDisable(false);
                break;
            case FLIGHT_DEST_DEPART:
                flightNum.setDisable(true);
                destComboBox.setDisable(false);
                departComboBox.setDisable(false);
                timePicker.setDisable(false);
                datePicker.setDisable(false);
                break;
            case FLIGHT_Date_Time:
                flightNum.setDisable(true);
                destComboBox.setDisable(true);
                departComboBox.setDisable(true);
                timePicker.setDisable(false);
                datePicker.setDisable(false);
                break;
            default:
        }
    }

    private void setupTableView() {
        dateTimeRow.setCellFactory(column -> {
            TableCell<Flight, Date> cell = new TableCell<Flight, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        flightNumber.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        availableSeats.setCellValueFactory(new PropertyValueFactory<>("seats"));
        dateTimeRow.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        departure.setCellValueFactory(new PropertyValueFactory<>("src"));
        destination.setCellValueFactory(new PropertyValueFactory<>("dest"));
        delayed.setCellValueFactory(new PropertyValueFactory<>("delay"));
        officeName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Flight, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Flight, String> param) {
                ObservableValue<String> observableValue = new SimpleStringProperty(param.getValue().getMaster().getOfficeName());
                return observableValue;
            }
        });


    }

    @FXML
    void manageFlight(ActionEvent event) {
        Parent container = sp.getParent();
        Object controller = null;
        do {
            controller = container.getProperties().get("foo");
            container = container.getParent();
        } while (controller == null && container != null);
        MasterController cMaster = (MasterController) controller;
        if (!dataTable.getSelectionModel().isEmpty()) {
            MasterManageFlights.selectedFlight = dataTable.getSelectionModel().getSelectedItem();
            cMaster.openScene("../fxml/master_manage_flights.fxml", "Manage Flights");
        } else {
            UtilityServices.displayDialog(new Text("Check Your Inputs !"), new Text("Make sure that you selected a valid flight from the table !"), sp);
        }
    }

    @FXML
    void searchClicked(ActionEvent event) {
        loadingSpinner.setVisible(true);
        if (!searchComboBox.getSelectionModel().isEmpty()) {
            switch (searchComboBox.getSelectionModel().getSelectedItem()) {
                case MASTER_FLIGHTS:
                    addDataToTable(FlightDatabaseAPI.searchUsingMasterID(AuthMaster.currentMaster.getMasterID()));
                    break;
                case FLIGHT_NUMBER:
                    if (!flightNum.getText().isEmpty())
                        addDataToTable(FlightDatabaseAPI.searchUsingFlightNumber(flightNum.getText()));
                    else

                        UtilityServices.displayDialog(new Text("Check Your Inputs !"), new Text("Make sure that you entered a correct flight number !"), sp);
                    break;
                case FLIGHT_DEST_DEPART:
                    if (!departComboBox.getSelectionModel().isEmpty() && !destComboBox.getSelectionModel().isEmpty() && datePicker.getValue() != null && timePicker != null) {
                        LocalDateTime localDateTime = LocalDateTime.of(datePicker.getValue(), timePicker.getValue());
                        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                        if (FlightDatabaseAPI.searchUsingSrcAndDestination(departComboBox.getSelectionModel().getSelectedItem(), destComboBox.getSelectionModel().getSelectedItem(), date) != null) {
                            addDataToTable(FlightDatabaseAPI.searchUsingSrcAndDestination(departComboBox.getSelectionModel().getSelectedItem(), destComboBox.getSelectionModel().getSelectedItem(), date));
                        }
                    } else {
                        UtilityServices.displayDialog(new Text("Check Your Inputs !"), new Text("Make sure that you selected a valid departure and destination airport !"), sp);
                    }
                    break;
                case FLIGHT_Date_Time:
                    if (datePicker.getValue() != null && timePicker != null) {
                        LocalDateTime localDateTime = LocalDateTime.of(datePicker.getValue(), timePicker.getValue());
                        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                        addDataToTable(FlightDatabaseAPI.searchUsingDateAndTime(date));
                    }
                    break;
                default:
            }
            loadingSpinner.setVisible(false);
        } else {
            UtilityServices.displayDialog(new Text("Check Your Inputs !"), new Text("Make sure that you selected a valid search type !"), sp);

        }
    }

    private void addDataToTable(ResultSet resultSet) {
        flightList.clear();
        try {
            while (resultSet.next()) {
                Flight flight = new Flight(resultSet.getString("flightNumber"), resultSet.getString("src"),
                        resultSet.getString("dest"), resultSet.getTimestamp("dateAndTime"),
                        resultSet.getInt("price"), resultSet.getInt("seats"),
                        resultSet.getInt("delay"), resultSet.getInt("master_id"));
                Master master = new Master(resultSet.getString("officeName"), resultSet.getString("phone"),
                        resultSet.getString("email"), resultSet.getString("password"));
                flight.setMaster(master);
                flightList.add(flight);

            }
            dataTable.setItems(FXCollections.observableList(flightList));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onDepartureAirportSelected(ActionEvent event) {
        destComboBox.getItems().setAll(FXCollections.observableArrayList(airportsList));
        destComboBox.getItems().remove(departComboBox.getSelectionModel().getSelectedIndex());

    }

}
