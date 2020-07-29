package com.example.pezeshkam.Activities;

import com.example.pezeshkam.Models.DoctorCard;
import com.example.pezeshkam.Models.ReserveCard;
import com.example.pezeshkam.R;

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

    public ArrayList<ReserveCard> getReserveCards() {
        ArrayList<ReserveCard> reserveCards = new ArrayList<>();
        reserveCards.add(new ReserveCard("محسن سیب", "گاو","23",
                null, false, "۱۳۹۸/۱۲/۲۶ ۲۳:۵۵"));
        reserveCards.add(new ReserveCard("محسن سنایی", "گاو","23",
                null, false, "۱۳۹۸/۱۲/۲۶ ۲۳:۵۵"));
        reserveCards.add(new ReserveCard("محسن سنایی", "گاو","23",
                null, false, "۱۳۹۸/۱۲/۲۶ ۲۳:۵۵"));
        reserveCards.add(new ReserveCard("محسن سنایی", "گاو","23",
                null, false, "۱۳۹۸/۱۲/۲۶ ۲۳:۵۵"));
        reserveCards.add(new ReserveCard("محسن سنایی", "۰۹۳۰۲۳۷۷۶۵۱","23",
                null, false, "۱۳۹۸/۱۲/۲۶ ۲۳:۵۵"));
        reserveCards.add(new ReserveCard("محسن سنایی", "۰۹۳۰۲۳۷۷۶۵۱","23",
                null, false, "۱۳۹۸/۱۲/۲۶ ۲۳:۵۵"));
        reserveCards.add(new ReserveCard("محسن سنایی", "۰۹۳۰۲۳۷۷۶۵۱","23",
                null, false, "۱۳۹۸/۱۲/۲۶ ۲۳:۵۵"));
        reserveCards.add(new ReserveCard("محسن سنایی", "۰۹۳۰۲۳۷۷۶۵۱","23",
                null, false, "۱۳۹۸/۱۲/۲۶ ۲۳:۵۵"));
        return reserveCards;
    }
}
