package com.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flight_ad {
    int id;
    String source,dest;
    LocalDate date;
    LocalTime time;
    double price;
    String gate;
    String AType;
    LocalDate MYear;

    public Flight_ad(int id, String source, String dest, LocalDate date, LocalTime time, double price,String gate,String AType,LocalDate MYear) {
        this.id = id;
        this.source = source;
        this.dest = dest;
        this.date = date;
        this.time = time;
        this.price = price;
        this.AType = AType;
        this.gate = gate;
        this.MYear = MYear;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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

    public String getAType() {
        return AType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAType(String AType) {
        this.AType = AType;
    }

    public String getGate() {
        return gate;
    }

    public LocalDate getMYear() {
        return MYear;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public void setMYear(LocalDate MYear) {
        this.MYear = MYear;
    }
}
