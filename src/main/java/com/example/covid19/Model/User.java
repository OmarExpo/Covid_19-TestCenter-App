package com.example.covid19.Model;

public class User {

    private int userID;
    private String name;
    private String userName;
    private String password;
    private String cpr;
    private String phone;
    private String testStatus;

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public User() {
    }

    public User(int userID, String name,String userName, String password, String cpr, String phone,String testStatus) {
        this.userID = userID;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.cpr = cpr;
        this.phone = phone;
        this.testStatus = testStatus;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
