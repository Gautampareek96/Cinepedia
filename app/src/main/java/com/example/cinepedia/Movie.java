package com.example.cinepedia;

import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String description;
    private String posterUrl;
    private double rating;
    private int year;
    private List<CastMember> cast;
    private String bookingUrl;
    private String category;

    public Movie(int id, String title, String description, String posterUrl,
                 double rating, int year, List<CastMember> cast, String bookingUrl, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.year = year;
        this.cast = cast;
        this.bookingUrl = bookingUrl;
        this.category = category;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPosterUrl() { return posterUrl; }
    public double getRating() { return rating; }
    public int getYear() { return year; }
    public List<CastMember> getCast() { return cast; }
    public String getBookingUrl() { return bookingUrl; }
    public String getCategory() { return category; }
}
