package ars.controllers;

import ars.models.Flight;
import ars.models.Ticket;
import ars.utils.AuthUser;
import ars.utils.DBConnection;
import ars.utils.UtilityServices;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.events.JFXDialogEvent;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.TimeSection;
import eu.hansolo.tilesfx.tools.Helper;
import eu.hansolo.tilesfx.tools.Location;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class UserEditingTicket implements Initializable {
    /**
     * TODO: Sedky load flight info from flight like dashboard and active cancel button (Delete)
     **/

    @FXML
    private StackPane spTicket;

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
    private JFXButton cancel;

    @FXML
    private TilePane clockTiles;
    Tile tile;

    public long getDiffrenceHours() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date firstDate = dateFormat.parse(dateFormat.format(Flight.currentFlight.getDateAndTime()));
            Date secondDate = dateFormat.parse(dateFormat.format(new Date()));
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return diff;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void clockTiles(double h){
        tile = TileBuilder.create()
                .skinType(Tile.SkinType.NUMBER)
                .prefSize(480, 437)
                .backgroundColor(Color.valueOf("#4c6876"))
                .title("The Time Before Take Off")
                .text("Can't Cancel Before 72H From Take Off")
                .value(h)
                .unit("H")
                .description("Remaining Time")
                .textVisible(true)
                .build();
        clockTiles.getChildren().add(tile);
    }
    @FXML
    void delayTicket(ActionEvent event) {
        UtilityServices.displayDialog(new Text("Notify"), new Text("Please Choose Another Flight From Search Engine"), spTicket);
        UtilityServices.button.setText("GO");
        UtilityServices.button.setOnAction(event1 -> {
            UserController.openSearchEngine.fire();
        });
    }
    @FXML
    void deleteTicket(ActionEvent event) {
        if (Flight.currentFlight != null){
            if (getDiffrenceHours() >= 72) {
                try {
                    PreparedStatement statement = DBConnection.getConnection().prepareStatement("update flight set seats = seats + 1 where flightNumber = ?");
                    statement.setString(1,Ticket.currentTicket.getFlightID());
                    statement.executeUpdate();
                    PreparedStatement deleteStatement = DBConnection.getConnection().prepareStatement("delete from ticket where user_id = ?");
                    deleteStatement.setInt(1, AuthUser.currentUser.getUserID());
                    deleteStatement.executeUpdate();
                    Ticket.currentTicket = null;
                    Flight.currentFlight = null;
                    setLabelData("N/A", "N/A", "N/A", "N/A", "N/A", "N/A");
                    UtilityServices.displayDialog(new Text("Confirm Cancel"), new Text("You are cancel Successfully...  "), spTicket);
                    tile.setValue(0);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                UtilityServices.displayDialog(new Text("Failed"), new Text("You Cannot Cancel This Flight Because The Take Off Time is Less Than 72 H"), spTicket);
            }
        }
    }

    public void setLabelData(String tn, String fn, String src, String dest, String date, String price) {
        ticketNumberLbl.setText(tn);
        flightNumberLbl.setText(fn);
        srcLbl.setText(src);
        destLbl.setText(dest);
        flightDateLbl.setText(date);
        flightPriceLbl.setText(price);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("select * from ticket where user_id = ?");
            PreparedStatement statement1 = c.prepareStatement("select * from flight where flightNumber = ?");
            statement.setInt(1, AuthUser.currentUser.getUserID());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String flightNumber = resultSet.getString("flightNumber");
                statement1.setString(1, flightNumber);
                ResultSet flightResultSet = statement1.executeQuery();
                if (flightResultSet.next()) {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:MM");
                    setLabelData(String.valueOf(resultSet.getInt("ticket_number")),flightNumber,flightResultSet.getString("src"),
                            flightResultSet.getString("dest"),dateFormat.format(flightResultSet.getDate("dateAndTime")),
                            String.valueOf(flightResultSet.getInt("price")));
                    clockTiles((double)getDiffrenceHours());
                }
            } else {
                setLabelData("N/A", "N/A", "N/A", "N/A", "N/A", "N/A");
                clockTiles(0);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
