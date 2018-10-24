package ars.controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserBookingTicket implements Initializable, MapComponentInitializedListener {

    @FXML
    GoogleMapView mapView;
    GoogleMap map;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
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

            map.addMarker( joeSmithMarker );

            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content("<h2>Fred Wilkie</h2>"
                    + "Current Location: Safeway<br>"
                    + "ETA: 45 minutes" );

            InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
            fredWilkeInfoWindow.open(map, joeSmithMarker);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
