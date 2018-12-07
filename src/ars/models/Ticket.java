package ars.models;



public class Ticket {


    private int ticketID;
    private int userID;
    private String flightID;


    public Ticket(int ticketID,int userID,String flightID){
        this.ticketID=ticketID;
        this.userID=userID;
        this.flightID=flightID;
    }

    public int getTicketID() {return ticketID;}

    public void setTicketID(int ticketID) {this.ticketID = ticketID;}

    public int getUserID() {return userID;}

    public void setUserID(int userID) {this.userID = userID;}

    public String getFlightID() { return flightID;}

    public void setFlightID(String flightID) { this.flightID = flightID;}

}
