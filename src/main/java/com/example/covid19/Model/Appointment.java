package com.example.covid19.Model;

import java.time.LocalDateTime;

public class Appointment {


    private String cpr;
    private int tcID;
    private LocalDateTime localDateTime;

    public Appointment() {
    }

    public Appointment(String cpr, int tcID, LocalDateTime localDateTime) {
        this.cpr = cpr;
        this.tcID = tcID;
        this.localDateTime = localDateTime;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public int getTcID() {
        return tcID;
    }

    public void setTcID(int tcID) {
        this.tcID = tcID;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

}

