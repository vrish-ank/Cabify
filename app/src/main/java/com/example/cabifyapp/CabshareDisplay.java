package com.example.cabifyapp;

public class CabshareDisplay {
    private String name;
    private String from;
    private String to;
    private String tDate;
    private String tTime;
    private String Avail;
    private String email;
    private String phone;
    private CabshareDisplay() {}

    private CabshareDisplay(String name, String from,String to, String tDate,String tTime, String Avail,String email,String phone)
    {
        this.name= name;
        this.from= from;
        this.to= to;
        this.tDate= tDate;
        this.tTime= tTime;
        this.Avail= Avail;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }

    public String gettTime() {
        return tTime;
    }

    public void settTime(String tTime) {
        this.tTime = tTime;
    }

    public String getAvail() {
        return Avail;
    }

    public void setAvail(String avail) {
        this.Avail = Avail;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
