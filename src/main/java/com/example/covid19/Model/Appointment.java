package com.example.covid19.Model;

public class Appointment {

    private String cpr;
    private int tcID;
    private String day;
    private String month;
    private String year;
    private String hour;
    private String minute;

    public int getTcID() {
        return tcID;
    }

    public void setTcID(int tcID) {
        this.tcID = tcID;
    }

    public Appointment() {
    }

    public Appointment(String cpr, int tcID,String day, String month, String year, String hour, String minute) {
        this.cpr = cpr;
        this.tcID = tcID;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
}

