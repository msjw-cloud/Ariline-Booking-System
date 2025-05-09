package com.example;

public class Ticket {
    String username;
    int ticketID;

    public Ticket(String username, int ticketID) {
        this.username = username;
        this.ticketID = ticketID;
    }

    public String getUsername() {
        return username;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }
}
