package com.example.cinepedia;

public class Order {
    String title, date;
    int tickets, posterRes;

    public Order(String title, String date, int tickets, int posterRes) {
        this.title = title;
        this.date = date;
        this.tickets = tickets;
        this.posterRes = posterRes;
    }
}
