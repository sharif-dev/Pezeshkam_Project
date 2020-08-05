package com.example.pezeshkam.Activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.pezeshkam.Fragments.TimePickerFragment;
import com.example.pezeshkam.R;

public class CreateReserveActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Button createBtn;
    private Button setBeginTimeBtn;
    private Button setEndTimeBtn;
    private String whichTimePickerIsClicked;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_reserve);

        createBtn = findViewById(R.id.create_reserve_btn);
        setBeginTimeBtn = findViewById(R.id.set_begin_time_btn);
        setEndTimeBtn = findViewById(R.id.set_end_time_btn);
        handler = new Handler();

        setBeginTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker start");
                whichTimePickerIsClicked = "begin";
            }
        });

        setEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker end");
                whichTimePickerIsClicked = "end";
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInputCorrect = true;
                int year, month, day;
                String y_str = ((EditText) findViewById(R.id.reserve_year_text)).getText().toString();
                String m_str = ((EditText) findViewById(R.id.reserve_month_text)).getText().toString();
                String d_str = ((EditText) findViewById(R.id.reserve_day_text)).getText().toString();
                if (y_str.isEmpty() || m_str.isEmpty() || d_str.isEmpty()) {
                    isInputCorrect = false;
                    showToast(getString(R.string.empty_date_error));
                } else {
                    year = Integer.parseInt(y_str);
                    month = Integer.parseInt(m_str);
                    day = Integer.parseInt(d_str);
                    if (year < 1399 || year > 1440 || month > 12 || day > 31) {
                        isInputCorrect = false;
                        showToast(getString(R.string.incorrect_date_error));
                    }
                }
                String beginTime = ((TextView) findViewById(R.id.begin_time_text)).getText().toString();
                String endTime = ((TextView) findViewById(R.id.end_time_text)).getText().toString();
                if (beginTime.isEmpty() || endTime.isEmpty() || beginTime.compareTo(endTime) >= 0) {
                    isInputCorrect = false;
                    showToast(getString(R.string.incorrect_reserve_time_error));
                }
                String timePerReserve = ((EditText) findViewById(R.id.time_per_reserve)).getText().toString();
                if (timePerReserve.isEmpty()) {
                    isInputCorrect = false;
                    showToast(getString(R.string.empty_reserve_time_error));
                }
                if (isInputCorrect) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: 8/4/2020 send request here
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        TextView textView;
        if (whichTimePickerIsClicked.equals("begin")) {
            textView = findViewById(R.id.begin_time_text);
        } else if (whichTimePickerIsClicked.equals("end")) {
            textView = findViewById(R.id.end_time_text);
        } else {
            return;
        }
        textView.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
