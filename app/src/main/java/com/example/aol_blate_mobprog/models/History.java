package com.example.aol_blate_mobprog.models;

public class History {
    private String name;
    private String status;   // "Like" atau "Dislike"
    private String date;     // Contoh: "12 Dec 2025"
    private String imageStr; // Nama file gambar (misal: "user_martin")

    public History(String name, String status, String date, String imageStr) {
        this.name = name;
        this.status = status;
        this.date = date;
        this.imageStr = imageStr;
    }

    public String getName() { return name; }
    public String getStatus() { return status; }
    public String getDate() { return date; }
    public String getImageStr() { return imageStr; }
}