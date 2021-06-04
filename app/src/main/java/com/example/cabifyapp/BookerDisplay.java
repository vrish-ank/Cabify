package com.example.cabifyapp;

public class BookerDisplay {
    private String booker_email;
    private String requester_email;
    private String requester_phone;
    private int Seats_booked;

    private BookerDisplay(){}

    private BookerDisplay(String booker_email, String requester_email, String requester_phone,int Seats_booked)
    {
        this.booker_email=booker_email;
        this.requester_email=requester_email;
        this.requester_phone=requester_phone;
        this.Seats_booked=Seats_booked;
    }

    public String getBooker_email() {
        return booker_email;
    }

    public void setBooker_email(String booker_email) {
        this.booker_email = booker_email;
    }

    public String getRequester_email() {
        return requester_email;
    }

    public void setRequester_email(String requester_email) {
        this.requester_email = requester_email;
    }

    public String getRequester_phone() {
        return requester_phone;
    }

    public void setRequester_phone(String requester_phone) {
        this.requester_phone = requester_phone;
    }


    public int getSeats_booked() {
        return Seats_booked;
    }

    public void setSeats_booked(int seats_booked) {
        Seats_booked = seats_booked;
    }
}

