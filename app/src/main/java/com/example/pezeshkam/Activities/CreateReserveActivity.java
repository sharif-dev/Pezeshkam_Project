package com.example.pezeshkam.Activities;

import android.app.TimePickerDialog;
import android.content.Intent;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pezeshkam.Fragments.TimePickerFragment;
import com.example.pezeshkam.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_reserve);

        createBtn = findViewById(R.id.create_reserve_btn);
        setBeginTimeBtn = findViewById(R.id.set_begin_time_btn);
        setEndTimeBtn = findViewById(R.id.set_end_time_btn);
        requestQueue = Volley.newRequestQueue(this);
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
                Dictionary dict = new Hashtable();

//                int year, month, day;
//                String y_str = ((EditText) findViewById(R.id.reserve_year_text)).getText().toString();
//                String m_str = ((EditText) findViewById(R.id.reserve_month_text)).getText().toString();
//                String d_str = ((EditText) findViewById(R.id.reserve_day_text)).getText().toString();
//                if (y_str.isEmpty() || m_str.isEmpty() || d_str.isEmpty()) {
//                    isInputCorrect = false;
//                    showToast(getString(R.string.empty_date_error));
//                } else {
//                    year = Integer.parseInt(y_str);
//                    month = Integer.parseInt(m_str);
//                    day = Integer.parseInt(d_str);
//                    if (year < 1399 || year > 1440 || month > 12 || day > 31) {
//                        isInputCorrect = false;
//                        showToast(getString(R.string.incorrect_date_error));
//                    }
//                }
//                String beginTime = ((TextView) findViewById(R.id.begin_time_text)).getText().toString();
//                String endTime = ((TextView) findViewById(R.id.end_time_text)).getText().toString();
//                if (beginTime.isEmpty() || endTime.isEmpty() || beginTime.compareTo(endTime) >= 0) {
//                    isInputCorrect = false;
//                    showToast(getString(R.string.incorrect_reserve_time_error));
//                }
//                String timePerReserve = ((EditText) findViewById(R.id.time_per_reserve)).getText().toString();
//                if (timePerReserve.isEmpty()) {
//                    isInputCorrect = false;
//                    showToast(getString(R.string.empty_reserve_time_error));
//                }

//                if (isInputCorrect) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            // TODO: 8/4/2020 send request here
//                        }
//                    });
//                }

                dict.put("year", 1399);
                dict.put("month", 6);
                dict.put("day", 8);
                dict.put("start_hour", 7);
                dict.put("start_minute", 30);
                dict.put("end_hour", 14);
                dict.put("end_minute", 30);
                dict.put("period", 30);
                dict.put("doctor_id", 1);
                sendCreateReserveRequest(dict);

            }
        });
    }

    private void sendGetDoctorIDRequest() {
        String url = getString(R.string.getid_api_url);
//        StringRequest request = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println("yes response - " + response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println("oh error - " + error);
//                    }
//        });

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("yes - " + response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("no - " + error);
                    }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void sendCreateReserveRequest(Dictionary dict) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("day", dict.get("day"));
            jsonObject.put("month", dict.get("month"));
            jsonObject.put("year", dict.get("year"));
            jsonObject.put("start_hour", dict.get("start_hour"));
            jsonObject.put("start_minute", dict.get("start_minute"));
            jsonObject.put("end_hour", dict.get("end_hour"));
            jsonObject.put("end_minute", dict.get("end_minute"));
            jsonObject.put("period", dict.get("period"));
            jsonObject.put("doctor_id", dict.get("doctor_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                getString(R.string.create_reserve_api_url), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("yes2 - " + response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("no2 - " + error);
                    }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(request);
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
