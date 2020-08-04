package com.example.pezeshkam.Models;

public class DoctorCard {
    private String username;
    private String phone;
    private String image;
    private String occupation;
    private String pk;
    public DoctorCard(String username, String phone, String image, String occupation, String pk) {
        this.username = username;
        this.phone = phone;
        this.image = image;
        this.occupation = occupation;
        this.pk = pk;
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getPk() {
        return pk;
    }
}
