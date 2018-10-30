package ars.models;



public class Ticket {
    /**
     * TODO: Salma
     * Create data model for each class in model package
     * all variables from the db columns ex: int userID, String name, Date dob ...
     * all of these variables must be private
     * provide constructor, setter and getter
     * complete all remaining classes
     *
     */
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
