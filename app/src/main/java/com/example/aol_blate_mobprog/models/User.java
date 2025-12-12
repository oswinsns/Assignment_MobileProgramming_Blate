package com.example.aol_blate_mobprog.models;

public class User extends Person {
    private String email, password;
    private int[] accept, reject;

    public User(int id, String profile, String name, String dob, String domicile, String current_job, String religion, String about, boolean gender, String[] hobbies, String email, String password) {
        super(id, profile, name, dob, domicile, current_job, religion, about, gender, hobbies);
        this.email = email;
        this.password = password;
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

    public int[] getAccept() {
        return accept;
    }
    public void setAccept(int[] accept) {
        this.accept = accept;
    }

    public int[] getReject() {
        return reject;
    }
    public void setReject(int[] reject) {
        this.reject = reject;
    }
}
