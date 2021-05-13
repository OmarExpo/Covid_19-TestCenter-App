package com.example.covid19.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DateAndTime {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date mydate;
    private String time;
    private String cpr;

    public DateAndTime() {
    }

    public DateAndTime(Date mydate, String time, String cpr) {
        this.mydate = mydate;
        this.time = time;
        this.cpr = cpr;
    }

    public Date getMydate() {
        return mydate;
    }

    public void setMydate(Date mydate) {
        this.mydate = mydate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }
}
