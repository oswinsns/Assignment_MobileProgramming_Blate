package com.example.aol_blate_mobprog.models; // Sesuaikan package

public class Chat {
    private String name;
    private String message;
    private int imageRes;

    public Chat(String name, String message, int imageRes) {
        this.name = name;
        this.message = message;
        this.imageRes = imageRes;
    }

    public String getName() { return name; }
    public String getMessage() { return message; }
    public int getImageRes() { return imageRes; }
}