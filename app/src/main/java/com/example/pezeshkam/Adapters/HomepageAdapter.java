package com.example.pezeshkam.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.pezeshkam.Models.DoctorCard;
import com.example.pezeshkam.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomepageAdapter extends ArrayAdapter<DoctorCard> {
    private ArrayList<DoctorCard> doctors;
    private Context context;
    private int resource;



    public HomepageAdapter(@NonNull Context context, int resource, @NonNull List<DoctorCard> objects) {
        super(context, resource, objects);
        this.doctors = (ArrayList<DoctorCard>) objects;
        this.context = context;
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DoctorCard doctorCard = doctors.get(position);
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.doctor_card, parent, false);
        TextView username = view.findViewById(R.id.doctor_name);
        TextView occupation = view.findViewById(R.id.doctor_occupation);
        ImageView image = view.findViewById(R.id.doctor_img);
        String imageURL = "http://10.0.2.2:8000" + doctorCard.getImage();
        Log.i("image URL", imageURL);
        Picasso.get().load(imageURL).into(image);
        username.setText("نام کاربری:       " + doctorCard.getUsername());
        TextView phone = view.findViewById(R.id.doctor_phone);
        phone.setText("شماره تماس:‌    " + doctorCard.getPhone());
        occupation.setText("حرفه:   " + doctorCard.getOccupation());
        return view;
    }

}
