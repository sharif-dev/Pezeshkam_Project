package com.example.pezeshkam.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pezeshkam.Models.DoctorCard;
import com.example.pezeshkam.Models.ReserveCard;
import com.example.pezeshkam.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends ArrayAdapter<ReserveCard> {

    private int userID, profID;
    private boolean isDoctor;
    private ArrayList<ReserveCard> reserveCards;
    private Context context;
    public ProfileAdapter(@NonNull Context context, int resource,
                          @NonNull List<ReserveCard> reserveCards) {
        super(context, resource, reserveCards);
        this.userID = userID;
        this.profID = profID;
        this.isDoctor = isDoctor;
        this.context = context;
        this.reserveCards = (ArrayList<ReserveCard>) reserveCards;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReserveCard reserveCard = reserveCards.get(position);
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.reserve_card, parent, false);
        TextView un = view.findViewById(R.id.reserve_card_username);
        TextView op = view.findViewById(R.id.reserve_card_occupation);
        TextView date = view.findViewById(R.id.reserve_card_date);
        TextView rd = view.findViewById(R.id.reserve_card_reserved);
        Switch sw = view.findViewById(R.id.reserve_card_switch);
        Button cl = view.findViewById(R.id.reserve_card_cancel);

        un.setText("نام دکتر:       " + reserveCard.getName());
        op.setText("شغل:‌    " + reserveCard.getOccupation());
        date.setText("تاریخ:   " + reserveCard.getDate());

        if (this.userID != this.profID) {
            cl.setVisibility(View.INVISIBLE);
            rd.setVisibility(View.INVISIBLE);
        } else {
            if (isDoctor) {
                sw.setVisibility(View.INVISIBLE);
                rd.setVisibility(View.INVISIBLE);
            } else {
                cl.setVisibility(View.INVISIBLE);
                rd.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }
}
