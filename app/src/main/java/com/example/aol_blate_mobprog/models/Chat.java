package com.example.aol_blate_mobprog.models;

public class Chat {
    private String name;
    private String message; // message ini diisi sama kolom about
    private String imageStr; // jujur ini masi error gatau ngapa

    public Chat(String name, String message, String imageStr) {
        this.name = name;
        this.message = message;
        this.imageStr = imageStr;
    }

    public String getName() { return name; }
    public String getMessage() { return message; }
    public String getImageStr() { return imageStr; }
}