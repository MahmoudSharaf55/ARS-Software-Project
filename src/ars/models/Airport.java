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
    private double latidude;
    private double longitude;

    public Airport(int airportID,String name,double latidude,double longitude){
        this.airportID=airportID;
        this.name=name;
        this.latidude=latidude;
        this.longitude=longitude;

    }

    public int getAirportID() {return airportID;}

    public void setAirportID(int airportID) {this.airportID = airportID;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public double getLatidude() {return latidude;}

    public void setLatidude(double latidude) {this.latidude = latidude;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}
}
