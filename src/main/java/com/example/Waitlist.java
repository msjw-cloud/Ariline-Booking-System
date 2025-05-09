package com.example;

public class Waitlist {
    int seat_id;
    int flight_id;
    String seat_type;
    String w_status;

    public Waitlist(int seat_id,int flight_id, String seat_type, String w_status) {
        this.seat_id = seat_id;
        this.flight_id = flight_id;
        this.seat_type = seat_type;
        this.w_status = w_status;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public String getW_status() {
        return w_status;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public void setW_status(String w_status) {
        this.w_status = w_status;
    }
}
