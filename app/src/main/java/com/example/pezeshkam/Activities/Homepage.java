package com.example.pezeshkam.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.pezeshkam.Adapters.HomepageAdapter;
import com.example.pezeshkam.Models.DoctorCard;
import com.example.pezeshkam.R;
import com.example.pezeshkam.Threads.HomepageThread;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
public class Homepage extends AppCompatActivity {
    public static final int EMPTY_RESULT = 0;
    public static final int NON_EMPTY_RESULT = 1;
    public static final int RESQUEST_FAILED = 2;
    ListView listView;
    CountDownTimer timer;
    ProgressBar bar;
    TextInputEditText input;
    SearchView searchView;
    ImageView profile;
    Toast toast;
    static Handler handler;
    boolean requestAllowed = false;
    boolean typing = false;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        final Intent intent = new Intent(this, Profile.class);
        intent.putExtra("USERID", 4);
        intent.putExtra("PROFILEID", 6);

        final Intent intent1 = getIntent();
        token = intent1.getStringExtra("token");

        listView = findViewById(R.id.list1);
        bar = findViewById(R.id.home_progress);
        input = findViewById(R.id.home_input);
        searchView = findViewById(R.id.searchicon);
        profile = findViewById(R.id.home_prof);
        Log.i("profile image", profile.toString());
        input.addTextChangedListener(searchTextWatcher());
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        homepageHandler();
        getInitDatas();

    }

    public TextWatcher searchTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                checkWaitingForTyping();
                timer = new CountDownTimer(1000, 500) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        typingFinished();
                    }
                };
                timer.start();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public void getInitDatas() {
        listView.setVisibility(View.INVISIBLE);
        final Context cx = this;
        timer = new CountDownTimer(500, 500) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {

                String URL = "http://10.0.2.2:8000/all_doctors/";
                HomepageThread thread = new HomepageThread(handler, cx, URL, token);
                thread.start();
            }
        }.start();
    }

    public void homepageHandler() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                homepageMessage(msg);
            }
        };
    }

    public void typingFinished() {
        requestAllowed = true;
        typing = false;
        String text = input.getText().toString();
        String URL = "http://10.0.2.2:8000/search_doctor/?search=" + text;
        HomepageThread doctorsThread = new HomepageThread(handler, this, URL, token);
        doctorsThread.start();
    }

    public void checkWaitingForTyping() {
        if (typing)
            timer.cancel();
        else {
            typing = true;
            listView.setVisibility(View.INVISIBLE);
            bar.setVisibility(View.VISIBLE);
        }
    }

    public void homepageMessage(@NonNull Message msg) {
        Toast toast = Toast.makeText(this, "message", Toast.LENGTH_LONG);
        bar.setVisibility(View.INVISIBLE);
        if (msg.what == EMPTY_RESULT) {
            toast.setText("نتیجه ای یافت نشد");
            toast.show();
        } else if (msg.what == NON_EMPTY_RESULT) {
            listView.setVisibility(View.VISIBLE);
            ArrayList<DoctorCard> doctorCards = (ArrayList<DoctorCard>) msg.obj;
            ArrayAdapter<DoctorCard> adapter = new HomepageAdapter(this, 0, doctorCards);
            listView.setAdapter(adapter);
        } else if (msg.what == RESQUEST_FAILED) {
            toast.setText("درخواست با خطا مواجه شد");
            toast.show();
        }
        searchView.setVisibility(View.VISIBLE);
        input.setVisibility(View.VISIBLE);

    }
}

