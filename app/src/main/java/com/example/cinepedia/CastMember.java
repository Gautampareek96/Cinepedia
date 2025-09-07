package com.example.cinepedia;

public class CastMember {
    private int id;
    private String name;
    private String role;
    private String photoUrl;

    public CastMember(int id, String name, String role, String photoUrl) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.photoUrl = photoUrl;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getPhotoUrl() { return photoUrl; }
}