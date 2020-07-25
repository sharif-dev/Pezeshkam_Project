package com.example.pezeshkam;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.pezeshkam.Adapters.HomepageAdapter;
import com.example.pezeshkam.Models.DoctorCard;
import com.example.pezeshkam.Threads.HDatasThread;
import com.example.pezeshkam.Threads.SearchThread;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    public static final int EMPTY_RESULT = 0;
    public static final int NON_EMPTY_RESULT = 1;
    public static final int RESQUEST_FAILED = 2;
    public static final int PROFILE_PICTURE_FETCHED = 3;
    public static final int PROFILE_PICTURE_NOT_RECEIVED = 4;
    ListView listView;
    CountDownTimer timer;
    ProgressBar bar;
    TextView notFoundOrError;
    TextInputEditText input;
    SearchView searchView;
    ImageView profile;
    static Handler handler;
    boolean requestAllowed = false;
    boolean typing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        homepageHandler();

        ArrayList<DoctorCard> doctorCards = TmpArrays.getInstance().getDoctorCards();
        listView = findViewById(R.id.list1);
        bar = findViewById(R.id.home_progress);
        input = findViewById(R.id.home_input);
        notFoundOrError = findViewById(R.id.not_found);
        searchView = findViewById(R.id.searchicon);
        profile = findViewById(R.id.doctor_img);
        input.addTextChangedListener(searchTextWatcher());

        getInitDatas();

        ArrayAdapter<DoctorCard> adapter = new HomepageAdapter(this, 0, doctorCards);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        input.setVisibility(View.INVISIBLE);
        searchView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);
        timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                HDatasThread thread = new HDatasThread(2, handler);
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
        SearchThread doctorsThread = new SearchThread(text, handler);
        doctorsThread.start();
    }

    public void checkWaitingForTyping() {
        if (typing)
            timer.cancel();
        else {
            typing = true;
            listView.setVisibility(View.INVISIBLE);
            bar.setVisibility(View.VISIBLE);
            notFoundOrError.setVisibility(View.INVISIBLE);
        }
    }

    public void homepageMessage(@NonNull Message msg) {
        bar.setVisibility(View.INVISIBLE);
        if (msg.what == EMPTY_RESULT) {
             notFoundOrError.setVisibility(View.VISIBLE);
             notFoundOrError.setText("نتیجه‌ای یافت نشد :((");
        } else if (msg.what == NON_EMPTY_RESULT) {
            listView.setVisibility(View.VISIBLE);
        } else if (msg.what == RESQUEST_FAILED) {
            notFoundOrError.setText("درخواست با خطا مواجه شد :((");
            notFoundOrError.setVisibility(View.VISIBLE);
        } else {
            if (msg.what == PROFILE_PICTURE_NOT_RECEIVED) {
                notFoundOrError.setText("عدم ارتباط با سرور :((");
                notFoundOrError.setVisibility(View.VISIBLE);
            } else if (msg.what == PROFILE_PICTURE_FETCHED) {
                listView.setVisibility(View.VISIBLE);
            }
            searchView.setVisibility(View.VISIBLE);
            input.setVisibility(View.VISIBLE);
        }
    }
}

