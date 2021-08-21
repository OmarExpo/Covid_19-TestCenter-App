package com.example.covid19.Model;

public class Imessage {
    private String cpr;
    private String messageI;


    public Imessage() {
    }

    public Imessage(String cpr, String messageI) {
        this.cpr = cpr;
        this.messageI = messageI;

    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getMessageI() {
        return messageI;
    }

    public void setMessageI(String messageI) {
        this.messageI = messageI;
    }

}
