package com.example.pezeshkam.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pezeshkam.Adapters.ProfileAdapter;
import com.example.pezeshkam.R;
import com.google.android.material.textfield.TextInputEditText;

public class Profile extends AppCompatActivity {


    int userID = 3, profID = 3;
    boolean isDoctor = false;
    ListView list1, list2;
    TextView list1_l, list2_l;
    Button submit, reserve;
    TextView user_t, occup_t, phone_t, pass_l, email_t;
    TextInputEditText user_i, occup_i, phone_i, pass_i, email_i;
    CardView card_submit, card_reserve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("پروفایل");
        getComponents();
        if (userID == profID)
            seenByOwner();
        else
            seenByOthers();
        Log.i("length: ", Integer.toString(TmpArrays.getInstance().getReserveCards().size()));
        ArrayAdapter adapter1 = new ProfileAdapter(this, 0,
                TmpArrays.getInstance().getReserveCards());
        list1.setAdapter(adapter1);
    }

    private void seenByOthers() {
        user_i.setVisibility(View.INVISIBLE);
        occup_i.setVisibility(View.INVISIBLE);
        phone_i.setVisibility(View.INVISIBLE);
        email_i.setVisibility(View.INVISIBLE);
        pass_i.setVisibility(View.INVISIBLE);
        pass_l.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
        card_submit.setVisibility(View.INVISIBLE);
        reserve.setVisibility(View.INVISIBLE);
        card_reserve.setVisibility(View.INVISIBLE);
        list2.setVisibility(View.INVISIBLE);
        list2_l.setVisibility(View.INVISIBLE);
        list1_l.setText("وقت های خالی");
    }

    private void seenByOwner() {
        user_t.setVisibility(View.INVISIBLE);
        occup_t.setVisibility(View.INVISIBLE);
        phone_t.setVisibility(View.INVISIBLE);
        email_t.setVisibility(View.INVISIBLE);
        if (!isDoctor) {
            reserve.setVisibility(View.INVISIBLE);
            card_reserve.setVisibility(View.INVISIBLE);
            list2.setVisibility(View.INVISIBLE);
            list2_l.setVisibility(View.INVISIBLE);
            list1_l.setText("وقت های گرفته شده");
        } else {
            list1_l.setText("وقت های خالی");
            list2_l.setText("وقت های پر");
        }
    }

    private void getComponents() {
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
        list2 = findViewById(R.id.prof_list2);
        list1_l = findViewById(R.id.prof_list1_label);
        list2_l = findViewById(R.id.prof_list2_label);
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

//    public void getInitDatas() {
//        input.setVisibility(View.INVISIBLE);
//        searchView.setVisibility(View.INVISIBLE);
//        listView.setVisibility(View.INVISIBLE);
//        timer = new CountDownTimer(2000, 1000) {
//            @Override
//            public void onTick(long l) {}
//            @Override
//            public void onFinish() {
//                HDatasThread thread = new HDatasThread(2, handler);
//                thread.start();
//            }
//        }.start();
//    }

//    public void homepageHandler() {
//        handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                homepageMessage(msg);
//            }
//        };
//    }

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
//            listView.setVisibility(View.INVISIBLE);
//            bar.setVisibility(View.VISIBLE);
//            notFoundOrError.setVisibility(View.INVISIBLE);
//        }
//    }

//    public void homepageMessage(@NonNull Message msg) {
//        bar.setVisibility(View.INVISIBLE);
//        if (msg.what == EMPTY_RESULT) {
//             notFoundOrError.setVisibility(View.VISIBLE);
//             notFoundOrError.setText("نتیجه‌ای یافت نشد :((");
//        } else if (msg.what == NON_EMPTY_RESULT) {
//            listView.setVisibility(View.VISIBLE);
//        } else if (msg.what == RESQUEST_FAILED) {
//            notFoundOrError.setText("درخواست با خطا مواجه شد :((");
//            notFoundOrError.setVisibility(View.VISIBLE);
//        } else {
//            if (msg.what == PROFILE_PICTURE_NOT_RECEIVED) {
//                notFoundOrError.setText("عدم ارتباط با سرور :((");
//                notFoundOrError.setVisibility(View.VISIBLE);
//            } else if (msg.what == PROFILE_PICTURE_FETCHED) {
//                listView.setVisibility(View.VISIBLE);
//            }
//            searchView.setVisibility(View.VISIBLE);
//            input.setVisibility(View.VISIBLE);
//        }
//    }
}

