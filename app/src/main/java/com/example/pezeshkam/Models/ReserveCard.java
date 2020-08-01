package com.example.pezeshkam.Models;

import androidx.annotation.Nullable;

public class ReserveCard {
    private String name;
    private String occupation;
    private String doctorID;
    private @Nullable String patientID;
    private boolean isCatched;
    private String date;
    public ReserveCard(String name, String occupation, String doctorID,
                       @Nullable String patientID, boolean isCatched, String date) {
        this.name = name;
        this.occupation = occupation;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.isCatched = isCatched;
        this.date = date;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getDate() {
        return date;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getPatientID() {
        return patientID;
    }
}
