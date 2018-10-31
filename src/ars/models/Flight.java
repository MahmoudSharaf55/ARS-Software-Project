package ars.models;

import java.sql.Date;
public class Flight {
    private int flightNumber;
    private String src;
    private String dest;
    private Date dateAndTime;
    private int price;
    private int seats;
    private int delay;
    private int masterID;

    public Flight(int flightNumber,String src,String dest,Date dateAndTime,int price,int seats,int delay,int masterID){
        this.flightNumber=flightNumber;
        this.src=src;
        this.dest=dest;
        this.dateAndTime=dateAndTime;
        this.price=price;
        this.seats=seats;
        this.delay=delay;
        this.masterID=masterID;
    }

    public int getFlightNumber() {return flightNumber;}

    public void setFlightNumber(int flightNumber) {this.flightNumber = flightNumber;}

    public String getSrc() {return src;}

    public void setSrc(String src) {this.src = src;}

    public String getDest() {return dest;}

    public void setDest(String dest) {this.dest = dest;}

    public Date getDateAndTime() {return dateAndTime;}

    public void setDateAndTime(Date dateAndTime) {this.dateAndTime = dateAndTime;}

    public int getPrice() {return price;}

    public void setPrice(int price) {this.price = price;}

    public int getSeats() {return seats;}

    public void setSeats(int seats) {this.seats = seats;}

    public int getDelay() {return delay;}

    public void setDelay(int delay) {this.delay = delay;}

    public int getMasterID() {return masterID;}

    public void setMasterID(int masterID) {this.masterID = masterID;}
}
