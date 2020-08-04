package com.example.pezeshkam.Models;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Profile {
    String username;
    String phone;
    String occupation;
    String avatar;
    String password;
    String email;
    boolean isDoctor;
    @Nullable ArrayList<ReserveCard> cards;
    public Profile(String username, String phone, String occupation,String email,
                   String avatar, boolean isDoctor, @Nullable ArrayList<ReserveCard> cards
    ) {
        this.username = username;
        this.phone = phone;
        this.occupation = occupation;
        this.avatar = avatar;
        this.email = email;
        this.isDoctor = isDoctor;
        this.cards = cards;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    @Nullable
    public ArrayList<ReserveCard> getCards() {
        return cards;
    }
}
