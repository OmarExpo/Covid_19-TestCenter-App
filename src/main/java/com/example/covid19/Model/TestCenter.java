package com.example.covid19.Model;

public class TestCenter {
    private int tcID;
    private String cname;

    public TestCenter() {
    }

    public TestCenter(int tcID,String cname) {
        this.tcID = tcID;
        this.cname = cname;

    }

    public int getTcID() {
        return tcID;
    }

    public void setTcID(int tcID) {
        this.tcID = tcID;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
