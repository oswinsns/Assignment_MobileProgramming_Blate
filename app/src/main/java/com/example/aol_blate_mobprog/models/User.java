package com.example.aol_blate_mobprog.models;

import java.util.List;

public class User extends Person {
    private String email, password;

    // FIX: Renamed to match Firestore fields "accepted" and "rejected"
    private List<String> accepted, rejected;

    public User() {};

    public User(int id, String profile, String name, String dob, String domicile, String current_job, String religion, String about, boolean gender, List<String> hobbies, String email, String password) {
        super(id, profile, name, dob, domicile, current_job, religion, about, gender, hobbies);
        this.email = email;
        this.password = password;
    }

    // ... Email and Password getters/setters (keep them as is) ...
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // FIX: Rename Getters and Setters to match variable names
    public List<String> getAccepted() {
        return accepted;
    }
    public void setAccepted(List<String> accepted) {
        this.accepted = accepted;
    }

    public List<String> getRejected() {
        return rejected;
    }
    public void setRejected(List<String> rejected) {
        this.rejected = rejected;
    }
}