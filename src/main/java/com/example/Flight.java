package com.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flight {
    int flightID;
    String source,dest;
    LocalDate date;
    LocalTime time;
    double price;

    public Flight(int flightID, String source, String dest, LocalDate date, LocalTime time,double price) {
        this.flightID = flightID;
        this.source = source;
        this.dest = dest;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public int getFlightID() {
        return flightID;
    }

    public String getSource() {
        return source;
    }

    public String getDest() {
        return dest;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
