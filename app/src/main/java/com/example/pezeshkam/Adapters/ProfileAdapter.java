package com.example.pezeshkam.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.pezeshkam.Models.ReserveCard;
import com.example.pezeshkam.R;
import com.example.pezeshkam.Threads.DelCanThread;
import com.example.pezeshkam.Threads.ReserveThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.pezeshkam.Activities.Profile.profHandler;

public class ProfileAdapter extends ArrayAdapter<ReserveCard> {

    private String uID, pID;
    private boolean isDoctor;
    private ArrayList<ReserveCard> reserveCards;
    private Context context;

    public ProfileAdapter(@NonNull Context context, int resource, @NonNull List<ReserveCard>
            reserveCards, int uID, int pID, boolean isDoctor) {
        super(context, resource, reserveCards);
        this.uID = Integer.toString(uID);
        this.pID = Integer.toString(pID);
        this.isDoctor = isDoctor;
        this.context = context;
        this.reserveCards = (ArrayList<ReserveCard>) reserveCards;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ReserveCard reserveCard = reserveCards.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.reserve_card, parent, false);

        TextView un = view.findViewById(R.id.reserve_card_username);
        TextView op = view.findViewById(R.id.reserve_card_occupation);
        TextView date = view.findViewById(R.id.reserve_card_date);
        TextView rd = view.findViewById(R.id.reserve_card_reserved);
        TextView pn = view.findViewById(R.id.reserve_card_patient_name);
        TextView pm = view.findViewById(R.id.reserve_card_patient_mobile);
        final Switch sw = view.findViewById(R.id.reserve_card_switch);
        final Button cl = view.findViewById(R.id.reserve_card_cancel);

        @Nullable String patientID = reserveCard.getPatientID();
        String doctorID = reserveCard.getDoctorID();
        pm.setText("شماره تماس بیمار: " + reserveCard.getPatientMobile());
        pn.setText("نام بیمار: " + reserveCard.getPatientName());
        un.setText("نام دکتر:       " + reserveCard.getName());
        op.setText("شغل:‌    " + reserveCard.getOccupation());
        date.setText("تاریخ:   " + reserveCard.getDate() + "  "
                + reserveCard.getStart() + " الی " + reserveCard.getEnd());

        cl.setVisibility(View.GONE);
        rd.setVisibility(View.GONE);
        sw.setVisibility(View.GONE);
        pn.setVisibility(View.GONE);
        pm.setVisibility(View.GONE);
        sw.setChecked(reserveCard.isCatched());

        final ProfileAdapter adapter = this;
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rid = Integer.parseInt(reserveCard.getId());
                int pid = Integer.parseInt(uID);
                Thread thread;
                if (!reserveCard.isCatched())
                thread = new ReserveThread(rid, pid, context, reserveCards,
                        sw, adapter, profHandler);
                else
                    thread = new DelCanThread(rid, context, "delete_patient_reservation/",
                            reserveCards, adapter, profHandler);
                thread.start();
            }
        });
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rid = Integer.parseInt(reserveCard.getId());
                String type = "delete_reservation/";
                if (cl.getText().equals("کنسل"))
                    type = "delete_patient_reservation/";
                DelCanThread thread = new DelCanThread(rid, context, type, reserveCards,
                        adapter, profHandler);
                thread.start();
            }
        });
        if (!uID.equals(pID)) {
            if (uID.equals(patientID) || patientID == null)
                sw.setVisibility(View.VISIBLE);
            else
                rd.setVisibility(View.VISIBLE);
        } else if (uID.equals(doctorID)){
                pm.setVisibility(View.VISIBLE);
                pn.setVisibility(View.VISIBLE);
                cl.setVisibility(View.VISIBLE);
                if (patientID != null)
                    cl.setText("کنسل");
                 else cl.setText("حذف");
        } else {
            sw.setVisibility(View.VISIBLE);
        }
        return view;
    }

}
