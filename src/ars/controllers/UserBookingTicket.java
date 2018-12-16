package ars.controllers;

import ars.models.Flight;
import ars.models.Ticket;
import ars.utils.AuthUser;
import ars.utils.DBConnection;
import ars.utils.UtilityServices;
import com.jfoenix.controls.JFXTextField;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class UserBookingTicket implements Initializable, MapComponentInitializedListener {

    @FXML
    GoogleMapView mapView;
    GoogleMap map;
    int flightTicketNumber = -1;
    @FXML
    private JFXTextField ticketNumber;
    @FXML
    private Label ticketNumberLbl;
    @FXML
    private Label flightNumberLbl;
    @FXML
    private Label srcLbl;
    @FXML
    private Label destLbl;
    @FXML
    private Label flightDateLbl;
    @FXML
    private Label flightPriceLbl;
    @FXML
    private StackPane stackpane;

    @FXML
    void findTicketNumber(ActionEvent event) {
        flightTicketNumber = getNumber();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("select ticket_number from ticket where ticket_number = ?");
            ps.setInt(1, flightTicketNumber);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                findTicketNumber(event);
            } else {
                ticketNumber.setText(String.valueOf(flightTicketNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNumber() {
        return (int) (Math.random() * 10000 + 1);
    }

    @FXML
    void generateTicket(ActionEvent event) {
        if (flightTicketNumber != -1) {
            if (Flight.currentFlight != null) {
                ticketNumberLbl.setText(String.valueOf(flightTicketNumber));
                if (Ticket.currentTicket != null) {
                    try {
                        PreparedStatement statement = DBConnection.getConnection().prepareStatement("update flight set seats = seats + 1 where flightNumber = ?");
                        statement.setString(1,Ticket.currentTicket.getFlightID());
                        statement.executeUpdate();
                        PreparedStatement ps = DBConnection.getConnection().prepareStatement("update ticket set ticket_number = ?,user_id = ?,flightNumber = ? where user_id = ?");
                        ps.setInt(1, flightTicketNumber);
                        ps.setInt(2, AuthUser.currentUser.getUserID());
                        ps.setString(3, Flight.currentFlight.getFlightNumber());
                        ps.setInt(4, AuthUser.currentUser.getUserID());
                        ps.executeUpdate();
                        Ticket.currentTicket = new Ticket(flightTicketNumber, AuthUser.currentUser.getUserID(), Flight.currentFlight.getFlightNumber());
                        UtilityServices.displayDialog(new Text("Message"), new Text("Your Flight is generated Successfully"), stackpane);
                        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update flight set seats = seats - 1 where flightNumber = ?");
                        preparedStatement.setString(1,Flight.currentFlight.getFlightNumber());
                        preparedStatement.executeUpdate();
                        Flight.currentFlight.setSeats(Flight.currentFlight.getSeats()-1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT into ticket values (?,?,?)");
                        ps.setInt(1, flightTicketNumber);
                        ps.setInt(2, AuthUser.currentUser.getUserID());
                        ps.setString(3, Flight.currentFlight.getFlightNumber());
                        ps.executeUpdate();
                        Ticket.currentTicket = new Ticket(flightTicketNumber, AuthUser.currentUser.getUserID(), Flight.currentFlight.getFlightNumber());
                        UtilityServices.displayDialog(new Text("Message"), new Text("Your Flight is generated Successfully"), stackpane);
                        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update flight set seats = seats - 1 where flightNumber = ?");
                        preparedStatement.setString(1,Flight.currentFlight.getFlightNumber());
                        preparedStatement.executeUpdate();
                        Flight.currentFlight.setSeats(Flight.currentFlight.getSeats()-1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                UtilityServices.displayDialog(new Text("Check Your Selection"), new Text("Select A Valid Flight Please"), stackpane);
            }
        } else {
            UtilityServices.displayDialog(new Text("Check Your Flight Number"), new Text("Generate A Ticket Number Please"), stackpane);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
        if (Flight.currentFlight != null) {
            flightNumberLbl.setText(Flight.currentFlight.getFlightNumber());
            srcLbl.setText(Flight.currentFlight.getSrc());
            destLbl.setText(Flight.currentFlight.getDest());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:MM");
            flightDateLbl.setText(dateFormat.format(Flight.currentFlight.getDateAndTime()));
            flightPriceLbl.setText(String.valueOf(Flight.currentFlight.getPrice()));
        } else {
            flightNumberLbl.setText("N / A");
            srcLbl.setText("N / A");
            destLbl.setText("N / A");
            flightDateLbl.setText("N / A");
            flightPriceLbl.setText("N / A");
        }
        if (Ticket.currentTicket != null ) {
            if (Ticket.currentTicket.getTicketID() != -1)
                ticketNumberLbl.setText(String.valueOf(Ticket.currentTicket.getTicketID()));
            else
                ticketNumberLbl.setText("N / A");
        } else {
            ticketNumberLbl.setText("N / A");
        }
    }

    @Override
    public void mapInitialized() {
        try {
            LatLong joeSmithLocation = new LatLong(47.6197, -122.3231);
            //Set the initial properties of the map.
            MapOptions mapOptions = new MapOptions();

            mapOptions.center(new LatLong(47.6097, -122.3331))
                    .mapType(MapTypeIdEnum.ROADMAP)
                    .overviewMapControl(false)
                    .panControl(false)
                    .rotateControl(false)
                    .scaleControl(false)
                    .streetViewControl(false)
                    .zoomControl(false)
                    .zoom(12);

            map = mapView.createMap(mapOptions);

            //Add markers to the map
            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(joeSmithLocation);

            Marker joeSmithMarker = new Marker(markerOptions1);

            map.addMarker(joeSmithMarker);

            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content("<h2>Fred Wilkie</h2>"
                    + "Current Location: Safeway<br>"
                    + "ETA: 45 minutes");

            InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
            fredWilkeInfoWindow.open(map, joeSmithMarker);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
