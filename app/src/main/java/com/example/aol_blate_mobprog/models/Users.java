package com.example.aol_blate_mobprog.models;

public class Users extends People {
    private String email, password;
    private int[] connected_to;

    public Users(int id, String profile, String name, String dob, String domicile, String current_job, String religion, String about, boolean gender, String[] hobbies, String email, String password, int[] connected_to) {
        super(id, profile, name, dob, domicile, current_job, religion, about, gender, hobbies);
        this.email = email;
        this.password = password;
        this.connected_to = connected_to;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int[] getConnected_to() {
        return connected_to;
    }
    public void setConnected_to(int[] connected_to) {
        this.connected_to = connected_to;
    }
}
