package com.example.pezeshkam.Models;

import androidx.annotation.Nullable;

public class ReserveCard {
    private String name;
    private String occupation;
    private String id;
    private String doctorID;
    private @Nullable String patientID, patientName, patientMobile;
    private boolean isCatched;
    private String date, start, end;
    public ReserveCard(String id, String name, String occupation, String doctorID,
                       @Nullable String patientID, @Nullable String patientName,
                       @Nullable String patientMobile, String date,
                       String start, String end) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.date = date;
        this.start = start;
        this.patientName = patientName;
        this.patientMobile = patientMobile;
        this.end = end;
        this.isCatched = patientID != null;
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

    @Nullable
    public String getPatientMobile() {
        return patientMobile;
    }

    @Nullable
    public String getPatientName() {
        return patientName;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public boolean isCatched() {
        return isCatched;
    }

    public String getId() {
        return id;
    }

    public void setPatientID(@Nullable String patientID) {
        this.patientID = patientID;
    }

}
