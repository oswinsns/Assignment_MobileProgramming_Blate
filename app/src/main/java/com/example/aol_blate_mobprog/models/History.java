package com.example.aol_blate_mobprog.models;

public class History {
    private String name;
    private String date;     // Misal: "Today", "Yesterday"
    private String status;   // "Liked" atau "Passed"
    private int imageRes;

    public History(String name, String date, String status, int imageRes) {
        this.name = name;
        this.date = date;
        this.status = status;
        this.imageRes = imageRes;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public int getImageRes() { return imageRes; }
}