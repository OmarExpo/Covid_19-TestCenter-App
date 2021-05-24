package com.example.covid19.Model;

import java.util.Date;

public class TestStatusDate {
    String cpr;
    int tsID;
    Date testStatusDate;
    int vsID;
    Date vaccinStatusDate;

    public TestStatusDate() {
    }

    public TestStatusDate(String cpr, int tsID, Date testStatusDate, int vsID, Date vaccinStatusDate) {
        this.cpr = cpr;
        this.tsID = tsID;
        this.testStatusDate = testStatusDate;
        this.vsID = vsID;
        this.vaccinStatusDate = vaccinStatusDate;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public int getTsID() {
        return tsID;
    }

    public void setTsID(int tsID) {
        this.tsID = tsID;
    }

    public Date getTestStatusDate() {
        return testStatusDate;
    }

    public void setTestStatusDate(Date testStatusDate) {
        this.testStatusDate = testStatusDate;
    }

    public int getVsID() {
        return vsID;
    }

    public void setVsID(int vsID) {
        this.vsID = vsID;
    }

    public Date getVaccinStatusDate() {
        return vaccinStatusDate;
    }

    public void setVaccinStatusDate(Date vaccinStatusDate) {
        this.vaccinStatusDate = vaccinStatusDate;
    }
}