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

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pezeshkam.Fragments.TimePickerFragment;
import com.example.pezeshkam.R;
import com.example.pezeshkam.Threads.HomepageThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CreateReserveActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Button createBtn;
    private Button setBeginTimeBtn;
    private Button setEndTimeBtn;
    private String whichTimePickerIsClicked;
    private Handler handler;
    private RequestQueue requestQueue;
    private int year, month, day, begin_hour, begin_minute, end_hour, end_minute, period, doctor_id = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_reserve);

        createBtn = findViewById(R.id.create_reserve_btn);
        setBeginTimeBtn = findViewById(R.id.set_begin_time_btn);
        setEndTimeBtn = findViewById(R.id.set_end_time_btn);
        requestQueue = Volley.newRequestQueue(this);
        System.out.println("AAA " + getIntent().getIntExtra("id", 1));
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
                String y_str = ((EditText) findViewById(R.id.reserve_year_text)).getText().toString();
                String m_str = ((EditText) findViewById(R.id.reserve_month_text)).getText().toString();
                String d_str = ((EditText) findViewById(R.id.reserve_day_text)).getText().toString();
                if (y_str.isEmpty() || m_str.isEmpty() || d_str.isEmpty()) {
                    showToast(getString(R.string.empty_date_error));
                    return;
                } else {
                    year = Integer.parseInt(y_str);
                    month = Integer.parseInt(m_str);
                    day = Integer.parseInt(d_str);
                    boolean incorrect_date = false;
                    if (year < 1399 || year > 1440) {
                        incorrect_date = true;
                        ((EditText) findViewById(R.id.reserve_year_text)).setText(null);
                    }
                    if (month > 12) {
                        incorrect_date = true;
                        ((EditText) findViewById(R.id.reserve_month_text)).setText(null);
                    }
                    if (day > 31 || (month > 6 && day > 30)) {
                        incorrect_date = true;
                        ((EditText) findViewById(R.id.reserve_day_text)).setText(null);
                    }
                    if (incorrect_date) {
                        showToast(getString(R.string.incorrect_date_error));
                        return;
                    }
                }
                String beginTime = ((TextView) findViewById(R.id.begin_time_text)).getText().toString();
                String endTime = ((TextView) findViewById(R.id.end_time_text)).getText().toString();
                if (beginTime.isEmpty() || endTime.isEmpty() || beginTime.compareTo(endTime) >= 0) {
                    showToast(getString(R.string.incorrect_reserve_time_error));
                    return;
                }
                String timePerReserve = ((EditText) findViewById(R.id.time_per_reserve)).getText().toString();
                if (timePerReserve.isEmpty()) {
                    showToast(getString(R.string.empty_reserve_time_error));
                    return;
                }
                period = Integer.parseInt(timePerReserve);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendCreateReserveRequest();
                    }
                });
            }
        });
    }

    private void sendCreateReserveRequest() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("day", day);
            jsonObject.put("month", month);
            jsonObject.put("year", year);
            jsonObject.put("start_hour", begin_hour);
            jsonObject.put("start_minute", begin_minute);
            jsonObject.put("period", period);
            jsonObject.put("doctor_id", doctor_id);
            int dif = (end_hour - begin_hour) * 60 + end_minute - begin_minute;
            if (dif < period) {
                showToast(getString(R.string.cant_create_reserve_error));
                return;
            }
            if (dif % period != 0) {
                dif -= (dif % period);
                int m = begin_hour * 60 + begin_minute + dif;
                end_hour = (m - (m % 60)) / 60;
                end_minute = m % 60;
            }
            jsonObject.put("end_hour", end_hour);
            jsonObject.put("end_minute", end_minute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                getString(R.string.create_reserve_api_url), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showToast(getString(R.string.successful_create_reserve_msg));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            showToast(getString(R.string.server_down_error));
                        } else if (error instanceof ParseError){
                            showToast(getString(R.string.successful_create_reserve_msg));
                        } else {
                            showToast(getString(R.string.unsuccessful_create_reserve_error));
                        }
                    }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return HomepageThread.params;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        TextView textView;
        if (whichTimePickerIsClicked.equals("begin")) {
            begin_hour = hourOfDay;
            begin_minute = minute;
            textView = findViewById(R.id.begin_time_text);
        } else if (whichTimePickerIsClicked.equals("end")) {
            end_hour = hourOfDay;
            end_minute = minute;
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
