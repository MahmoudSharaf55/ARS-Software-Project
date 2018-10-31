package ars.models;



public class Ticket {


    private int ticketID;
    private int userID;
    private int flightID;


    public Ticket(int ticketID,int userID,int flightID){
        this.ticketID=ticketID;
        this.userID=userID;
        this.flightID=flightID;
    }

    public int getTicketID() {return ticketID;}

    public void setTicketID(int ticketID) {this.ticketID = ticketID;}

    public int getUserID() {return userID;}

    public void setUserID(int userID) {this.userID = userID;}

    public int getFlightID() { return flightID;}

    public void setFlightID(int flightID) { this.flightID = flightID;}

}
