package com.example.covid19.Model;

public class Vmessage {
    private String cpr;
    private String messageV;

    public Vmessage() {
    }

    public Vmessage(String cpr, String messageV) {
        this.cpr = cpr;
        this.messageV = messageV;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getMessageV() {
        return messageV;
    }

    public void setMessageV(String messageV) {
        this.messageV = messageV;
    }
}
