package com.example.pezeshkam.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pezeshkam.Adapters.HomepageAdapter;
import com.example.pezeshkam.Models.DoctorCard;
import com.example.pezeshkam.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Homepage extends Fragment {
    TextInputEditText editText;
    Context context;
    ListView listView;
    public Homepage(Context context) {
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        View view = inflater.inflate(R.layout.homepage, container, false);
        listView = view.findViewById(R.id.list1);
        ArrayAdapter<DoctorCard> adapter = new HomepageAdapter(this.context, 0, doctorCards);
        listView.setAdapter(adapter);
        return view;
    }
}
