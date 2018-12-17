package ars.controllers;

import ars.utils.AuthMaster;
import ars.utils.FlightDatabaseAPI;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.chart.ChartDataBuilder;
import eu.hansolo.tilesfx.tools.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private BarChart<String, Number> priceChart;

    @FXML
    private CategoryAxis xAxisPrice;

    @FXML
    private NumberAxis yAxisPrice;
    @FXML
    private PieChart piechartDestination;
    @FXML
    private TilePane tilePane;

    @FXML
    private TilePane tilePane2;
    @FXML
    private TilePane tilePane3;
    @FXML
    private TilePane tilePane4;

    private Tile worldTile;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupPriceBarChart();
        setupPieChart();
        setupTileMap();
        setupClock();
        setupSmoothAreaChart();
        setupCalender();
    }

    private void setupPriceBarChart() {
        ResultSet resultSet = FlightDatabaseAPI.searchUsingMasterID(AuthMaster.currentMaster.getMasterID());

        yAxisPrice.setLabel("Price");
        try {
            while (resultSet.next()) {
                XYChart.Series<String, Number> series1 = new XYChart.Series();
                series1.setName(resultSet.getString("src"));
                series1.getData().add(new XYChart.Data<String, Number>(resultSet.getString("src") + " to " + resultSet.getString("dest"), resultSet.getInt("price")));
                priceChart.getData().add(series1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupPieChart() {
        ResultSet resultSet = FlightDatabaseAPI.getFlightsAssociatedWithAllTickets(AuthMaster.currentMaster.getMasterID());
        try {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("dest"));
                pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data(resultSet.getString("dest"), resultSet.getInt("count")));


            }
            piechartDestination.setData(pieChartData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupTileMap() {
        List<Location> locations = new ArrayList<>();
        ResultSet resultSet = FlightDatabaseAPI.searchUsingMasterID(AuthMaster.currentMaster.getMasterID());
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Location locationsrc = new Location(resultSet.getDouble("srclat"), resultSet.getDouble("srclon"), resultSet.getString("src"));
                    Location locationdest = new Location(resultSet.getDouble("destlat"), resultSet.getDouble("destlon"), resultSet.getString("dest"));
                    locations.add(locationsrc);
                    locations.add(locationdest);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        worldTile = TileBuilder.create()
                .skinType(Tile.SkinType.WORLDMAP)
                .title("Flights Areas")
                .textVisible(false)
                .backgroundColor(Color.rgb(96, 125, 139))
                .prefSize(340, 330)
                .pointsOfInterest(locations)
                .build();
        tilePane.getChildren().addAll(worldTile);
    }

    private void setupClock() {
        Tile clockTile = TileBuilder.create()
                .prefSize(276, 235)
                .skinType(Tile.SkinType.CLOCK)
                .backgroundColor(Color.rgb(96, 125, 139))
                .title("The Time Now In Your Area")
                .dateVisible(true)
                .locale(Locale.US)
                .running(true)
                .build();
        tilePane2.getChildren().addAll(clockTile);
    }

    private void setupSmoothAreaChart() {
        ResultSet resultSet = FlightDatabaseAPI.getFlightsCount(AuthMaster.currentMaster.getMasterID());
       int tickets = 0;
        try {
            if (resultSet != null) {
                if (resultSet.next()) {
                   tickets = resultSet.getInt("count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Tile numberTile = TileBuilder.create()
                .prefSize(340, 235)
                .skinType(Tile.SkinType.NUMBER)
                .title("Number of Tickets")
                .backgroundColor(Color.rgb(96, 125, 139))
                .text("It always seems impossible until it's done.")
                .value(tickets)
                .unit("Tickets")
                .description("Reserved")
                .textVisible(true)
                .build();
        tilePane3.getChildren().setAll(numberTile);
    }

    private void setupCalender() {
        ZonedDateTime now = ZonedDateTime.now();
        Tile calendarTile = TileBuilder.create().skinType(Tile.SkinType.CALENDAR)
                .prefSize(380, 235)
                .title("Calendar")
                .text("")
                .backgroundColor(Color.rgb(96, 125, 139))
                .chartData(new ChartData(now.toInstant().toString()))
                .build();
        tilePane4.getChildren().setAll(calendarTile);
    }

}
