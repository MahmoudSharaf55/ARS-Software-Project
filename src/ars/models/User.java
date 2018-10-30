package ars.models;

import java.sql.Date;

public class User {
    /**
     * TODO: Salma
     * Create data model for each class in model package
     * all variables from the db columns ex: int userID, String name, Date dob ...
     * all of these variables must be private
     * provide constructor, setter and getter
     * this class is example
     * complete all remaining classes
     */
    private int userID;
    private Date dateOfBirth;
    private String gender;
    private String email;
    private String password;
    private int rating;

    public User(int userID, Date dateOfBirth, String gender, String email, String password, int rating) {
        this.userID = userID;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.rating = rating;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
