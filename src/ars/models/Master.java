package ars.models;

public class Master {
    
    private int masterID;
    private String officeName;
    private String phone;
    private String email;
    private String password;

    public Master(String officeName, String phone, String email, String password) {
        this.officeName = officeName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public int getMasterID() {
        return masterID;
    }

    public void setMasterID(int masterID) {
        this.masterID = masterID;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
