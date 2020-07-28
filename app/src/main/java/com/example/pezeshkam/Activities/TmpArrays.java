package com.example.pezeshkam.Activities;

import com.example.pezeshkam.Models.DoctorCard;

import java.util.ArrayList;

class TmpArrays {
    private static TmpArrays tmpArrays = null;
    private TmpArrays() {

    }
    public static TmpArrays getInstance()
    {
        if (tmpArrays == null)
            tmpArrays = new TmpArrays();

        return tmpArrays;
    }
    public ArrayList<DoctorCard> getDoctorCards() {
        ArrayList<DoctorCard> doctorCards = new ArrayList<>();
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        return doctorCards;
    }
}
