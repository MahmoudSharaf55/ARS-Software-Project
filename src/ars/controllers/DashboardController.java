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
import java.util.Locale;
import java.util.ResourceBundle;

import static sun.dc.pr.Rasterizer.TILE_SIZE;

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
    private Location SanFranciso;
    private Location NewYork;
    private Location Chicago;
    private Location Home;
    private Location Moscow;
    private Location Singapore;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupPriceBarChart();
        setupPieChart();
        setupTileMap();
        setupClock();
        setupSmothAreaChart();
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
        SanFranciso = new Location(37.7576171, -122.5776844, "San Francisco", Color.MAGENTA);
        NewYork = new Location(40.7157216, -74.3036411, "New York", Color.MAGENTA);
        Chicago = new Location(41.8333908, -88.0128341, "Chicago", Color.MAGENTA);
        Home = new Location(51.9065938, 7.6352688, "Hause", Color.CRIMSON);
        Moscow = new Location(55.751042, 37.619060, "Moscow", Color.MAGENTA);
        Singapore = new Location(1.3346428, 103.8415972, "Singapore", Color.MAGENTA);
        worldTile = TileBuilder.create()
                .skinType(Tile.SkinType.WORLDMAP)
                .title("Flights Areas")
                .textVisible(false)
                .backgroundColor(Color.rgb(96, 125, 139))
                .prefSize(340, 330)
                .pointsOfInterest(SanFranciso, Chicago, NewYork, Moscow)
                .chartData(ChartDataBuilder.create()
                        .name("Home")
                        .fillColor(Color.RED)
                        .value(20)
                        .location(Home)
                        .build())
                .build();
        tilePane.getChildren().addAll(worldTile);
    }

    private void setupClock() {
        Tile clockTile = TileBuilder.create()
                .prefSize(290, 235)
                .skinType(Tile.SkinType.CLOCK)
                .backgroundColor(Color.rgb(96, 125, 139))
                .title("The Time Now In Your Area")
                .dateVisible(true)
                .locale(Locale.US)
                .running(true)
                .build();
        tilePane2.getChildren().addAll(clockTile);
    }

    private void setupSmothAreaChart() {
        ResultSet resultSet = FlightDatabaseAPI.getFlightsCount(AuthMaster.currentMaster.getMasterID());
        try {
            if (resultSet != null){
            if (resultSet.next()) {
                Tile numberTile = TileBuilder.create()
                        .prefSize(374, 235)
                        .skinType(Tile.SkinType.NUMBER)
                        .title("Number of Tickets")
                        .backgroundColor(Color.rgb(96, 125, 139))
                        .text("It always seems impossible until it's done." )
                        .value(resultSet.getInt("count"))
                        .unit("Tickets")
                        .description("Reserved")
                        .textVisible(true)
                        .build();
                tilePane3.getChildren().setAll(numberTile);
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupCalender(){
        ZonedDateTime now = ZonedDateTime.now();
        Tile calendarTile  = TileBuilder.create().skinType(Tile.SkinType.CALENDAR)
                .prefSize(330, 235)
                .title("Calendar")
                .text("")
                .backgroundColor(Color.rgb(96, 125, 139))
                .chartData(new ChartData(now.toInstant().toString()))
                .build();
        tilePane4.getChildren().setAll(calendarTile);
    }

}
