package ars.models;

public class Master {
    /**
     * TODO: Salma
     * Create data model for each class in model package
     * all variables from the db columns ex: int userID, String name, Date dob ...
     * all of these variables must be private
     * provide constructor, setter and getter
     * complete all remaining classes
     */
    private int masterID;
    private String officeName;
    private String gender;
    private String email;
    private String password;

    public Master(int masterID,String officeName,String gender,String email,String password){
        this.masterID=masterID;
        this.officeName=officeName;
        this.gender=gender;
        this.email=email;
        this.password=password;
    }

    public int getMasterID() {return masterID;}

    public void setMasterID(int masterID) {this.masterID = masterID;}

    public String getOfficeName() {return officeName;}

    public void setOfficeName(String officeName) {this.officeName = officeName;}

    public String getGender() {return gender;}

    public void setGender(String gender) {this.gender = gender;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
