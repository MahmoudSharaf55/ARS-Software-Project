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
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.tools.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.swing.event.TreeModelEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserBookingTicket implements Initializable {

    int flightTicketNumber = -1;
    ArrayList<Location> locations = new ArrayList<>();
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
    private TilePane mapTiles;

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
                        statement.setString(1, Ticket.currentTicket.getFlightID());
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
                        preparedStatement.setString(1, Flight.currentFlight.getFlightNumber());
                        preparedStatement.executeUpdate();
                        Flight.currentFlight.setSeats(Flight.currentFlight.getSeats() - 1);
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
                        preparedStatement.setString(1, Flight.currentFlight.getFlightNumber());
                        preparedStatement.executeUpdate();
                        Flight.currentFlight.setSeats(Flight.currentFlight.getSeats() - 1);
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

    public void setupMapTiles(Location src, Location dest) {
        Tile tile = TileBuilder.create()
                .prefSize(500, 437)
                .backgroundColor(Color.valueOf("#4c6876"))
                .title("Departure & Destination Airport")
                .skinType(Tile.SkinType.MAP)
                .currentLocation(src)
                .pointsOfInterest(dest)
                .mapProvider(Tile.MapProvider.STREET)
                .dateVisible(true)
                .build();
        mapTiles.getChildren().add(tile);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Flight.currentFlight != null) {
            flightNumberLbl.setText(Flight.currentFlight.getFlightNumber());
            srcLbl.setText(Flight.currentFlight.getSrc());
            destLbl.setText(Flight.currentFlight.getDest());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:MM");
            flightDateLbl.setText(dateFormat.format(Flight.currentFlight.getDateAndTime()));
            flightPriceLbl.setText(String.valueOf(Flight.currentFlight.getPrice()));
            try {
                PreparedStatement ps = DBConnection.getConnection().prepareStatement("select latidude,longitude,name from airports where name = ? || name = ?");
                ps.setString(1,Flight.currentFlight.getSrc());
                ps.setString(2,Flight.currentFlight.getDest());
                ResultSet rs =  ps.executeQuery();
                rs.next();
                Location src = new Location(Double.parseDouble(rs.getString("latidude")),Double.parseDouble(rs.getString("longitude")),rs.getString("name"));
                System.out.println(src.getName());
                rs.next();
                Location dest = new Location(Double.parseDouble(rs.getString("latidude")),Double.parseDouble(rs.getString("longitude")),rs.getString("name"));
                System.out.println(dest.getName());
                setupMapTiles(src,dest);
            }catch (SQLException e){

            }

        } else {
            setupMapTiles(new Location(),new Location());
            flightNumberLbl.setText("N / A");
            srcLbl.setText("N / A");
            destLbl.setText("N / A");
            flightDateLbl.setText("N / A");
            flightPriceLbl.setText("N / A");
        }
        if (Ticket.currentTicket != null) {
            if (Ticket.currentTicket.getTicketID() != -1)
                ticketNumberLbl.setText(String.valueOf(Ticket.currentTicket.getTicketID()));
            else
                ticketNumberLbl.setText("N / A");
        } else {
            ticketNumberLbl.setText("N / A");
        }
    }


}
