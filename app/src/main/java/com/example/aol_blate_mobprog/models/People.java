package com.example.aol_blate_mobprog.models;

public class People {
    private int id;
    private String profile, name, dob, domicile,
                    current_job, religion, about;
    private boolean gender;
    private String[] hobbies;

    public People(int id, String profile, String name, String dob, String domicile, String current_job, String religion, String about, boolean gender, String[] hobbies) {
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.dob = dob;
        this.domicile = domicile;
        this.current_job = current_job;
        this.religion = religion;
        this.about = about;
        this.gender = gender;
        this.hobbies = hobbies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getCurrent_job() {
        return current_job;
    }

    public void setCurrent_job(String current_job) {
        this.current_job = current_job;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }
}
