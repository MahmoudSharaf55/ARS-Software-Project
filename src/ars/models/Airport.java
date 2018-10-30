package ars.models;

public class Airport {
    /**
     * TODO: Salma
     * Create data model for each class in model package
     * all variables from the db columns ex: int userID, String name, Date dob ...
     * all of these variables must be private
     * provide constructor, setter and getter
     * complete all remaining classes
     */
    private int airportID;
    private String name;
    private float latitude;
    private float longitude;

    public Airport(int airportID, String name, float latitude, float longitude){
        this.airportID=airportID;
        this.name=name;
        this.latitude = latitude;
        this.longitude=longitude;

    }

    public int getAirportID() {return airportID;}

    public void setAirportID(int airportID) {this.airportID = airportID;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public float getLatitude() {return latitude;}

    public void setLatitude(float latitude) {this.latitude = latitude;}

    public float getLongitude() {return longitude;}

    public void setLongitude(float longitude) {this.longitude = longitude;}
}
