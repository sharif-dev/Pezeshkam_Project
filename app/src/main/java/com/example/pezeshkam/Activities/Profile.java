package com.example.pezeshkam.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pezeshkam.Adapters.ProfileAdapter;
import com.example.pezeshkam.R;
import com.example.pezeshkam.Threads.ProfileThread1;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.pezeshkam.Activities.Homepage.EMPTY_RESULT;
import static com.example.pezeshkam.Activities.Homepage.NON_EMPTY_RESULT;
import static com.example.pezeshkam.Activities.Homepage.REQUEST_SUCCEED;
import static com.example.pezeshkam.Activities.Homepage.RESQUEST_FAILED;

public class Profile extends AppCompatActivity {

    int uID, pID;
    boolean isDoctor = true;
    public static Handler profHandler;

    ListView list1;
    TextView list1_l;
    Button submit, reserve;
    TextView user_t, occup_t, phone_t, pass_l, email_t;
    TextInputEditText user_i, occup_i, phone_i, pass_i, email_i;
    CardView card_submit, card_reserve;
    ProgressBar progressBar;
    ScrollView scrollView;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        uID = bundle.getInt("uid");
        pID = Integer.parseInt(bundle.getString("pid"));
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("پروفایل");

        getComponents();
        getInitDatas();
        profileHandler();
    }

    private void seenByOthers() {
        user_i.setVisibility(View.GONE);
        occup_i.setVisibility(View.GONE);
        phone_i.setVisibility(View.GONE);
        email_i.setVisibility(View.GONE);
        pass_i.setVisibility(View.GONE);
        pass_l.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        card_submit.setVisibility(View.GONE);
        reserve.setVisibility(View.GONE);
        card_reserve.setVisibility(View.GONE);
        list1_l.setText("تمامی رزروها");
    }

    private void seenByOwner() {
        user_t.setVisibility(View.GONE);
        occup_t.setVisibility(View.GONE);
        phone_t.setVisibility(View.GONE);
        email_t.setVisibility(View.GONE);
        if (!isDoctor) {
            reserve.setVisibility(View.GONE);
            card_reserve.setVisibility(View.GONE);
            list1_l.setText("وقت های گرفته شده");
        } else {
            list1_l.setText("تمامی رزروها");
        }
    }

    private void getComponents() {
        scrollView = findViewById(R.id.prof_scroll);
        progressBar = findViewById(R.id.prof_progressbar);
        user_t = findViewById(R.id.prof_user_t);
        user_i = findViewById(R.id.prof_user_i);
        occup_t = findViewById(R.id.prof_occup_t);
        occup_i = findViewById(R.id.prof_occup_i);
        phone_t = findViewById(R.id.prof_phone_t);
        email_i = findViewById(R.id.prof_email_i);
        email_t = findViewById(R.id.prof_email_t);
        phone_i = findViewById(R.id.prof_phone_i);
        pass_l = findViewById(R.id.prof_pass_l);
        pass_i = findViewById(R.id.prof_pass_i);
        submit = findViewById(R.id.prof_button_submit);
        reserve = findViewById(R.id.prof_button_create_reserve);
        card_reserve = findViewById(R.id.prof_card_reserve);
        card_submit = findViewById(R.id.prof_card_submit);
        list1 = findViewById(R.id.prof_list1);
        list1_l = findViewById(R.id.prof_list1_label);
        image = findViewById(R.id.prof_image);
    }
//    public TextWatcher searchTextWatcher() {
//        return new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
//                checkWaitingForTyping();
//                timer = new CountDownTimer(1000, 500) {
//                    @Override
//                    public void onTick(long l) {
//                    }
//                    @Override
//                    public void onFinish() {
//                        typingFinished();
//                    }
//                };
//                timer.start();
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        };
//    }

    public void getInitDatas() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        final Context cx = this;
        CountDownTimer timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                ProfileThread1 thread = new ProfileThread1(profHandler, pID, cx);
                thread.start();
            }
        }.start();
    }

    public void profileHandler() {
        profHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                profileMessage(msg);
            }
        };
    }

//    public void typingFinished() {
//        requestAllowed = true;
//        typing = false;
//        String text = input.getText().toString();
//        SearchThread doctorsThread = new SearchThread(text, handler);
//        doctorsThread.start();
//    }

//    public void checkWaitingForTyping() {
//        if (typing)
//            timer.cancel();
//        else {
//            typing = true;
//            listView.setVisibility(View.GONE);
//            bar.setVisibility(View.VISIBLE);
//            notFoundOrError.setVisibility(View.GONE);
//        }
//    }

    public void profileMessage(@NonNull Message msg) {
        Toast toast = Toast.makeText(this, "message", Toast.LENGTH_LONG);
        if (msg.what == EMPTY_RESULT) {
            toast.setText("نتیجه ای یافت نشد");
            toast.show();
        } else if (msg.what == NON_EMPTY_RESULT) {
            scrollView.setVisibility(View.VISIBLE);
            setDatas((com.example.pezeshkam.Models.Profile) msg.obj);
            if (uID == pID)
                seenByOwner();
            else
                seenByOthers();
        } else if (msg.what == RESQUEST_FAILED) {
            toast.setText("درخواست با خطا مواجه شد");
            toast.show();
        } else if (msg.what == REQUEST_SUCCEED) {
            toast.setText("درخواست با موفقیت انجام شد");
            toast.show();
        }
        progressBar.setVisibility(View.GONE);
    }

    public void setDatas(com.example.pezeshkam.Models.Profile profile) {
        String username = profile.getUsername();
        String phone = profile.getPhone();
        String occupation = profile.getOccupation();
        String avatar = profile.getAvatar();
        isDoctor = profile.isDoctor();
        if (uID == pID) {
            user_i.setText(username);
            occup_i.setText(occupation);
            phone_i.setText(phone);
        } else {
            user_t.setText(username);
            occup_t.setText(occupation);
            phone_t.setText(phone);
        }

        ArrayAdapter adapter1 = new ProfileAdapter(this, 0, profile.getCards(),
                uID, pID, true);
        list1.setAdapter(adapter1);

        Glide.with(this).load(avatar).into(image);

    }
}

