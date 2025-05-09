package com.example;

public class Ticket_ad {
int ticket_id;
String user;
int flight_id;
int seat_id;
double total_price;

    public Ticket_ad(int ticket_id, String user, int flight_id, int seat_id, double total_price) {
        this.ticket_id = ticket_id;
        this.user = user;
        this.flight_id = flight_id;
        this.seat_id = seat_id;
        this.total_price = total_price;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public String getUser() {
        return user;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}
