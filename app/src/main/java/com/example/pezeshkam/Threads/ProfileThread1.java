package com.example.pezeshkam.Threads;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pezeshkam.Models.Profile;
import com.example.pezeshkam.Models.ReserveCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.pezeshkam.Activities.Homepage.NON_EMPTY_RESULT;
import static com.example.pezeshkam.Activities.Homepage.RESQUEST_FAILED;
import static com.example.pezeshkam.Activities.Homepage.USER_ID;
import static com.example.pezeshkam.Threads.HomepageThread.params;

public class ProfileThread1 extends Thread {
    Handler handler;
    int pid;
    Context context;

    public ProfileThread1(Handler handler, int pid, Context context) {
        this.handler = handler;
        this.pid = pid;
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        final Message msg = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);

        String getUID = "http://10.0.2.2:8000/user_profile/?user_id=" + this.pid;
        JsonArrayRequest requestProf = new JsonArrayRequest(Request.Method.GET, getUID,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                msg.what = NON_EMPTY_RESULT;
                try {
                    Log.i("patient", response.toString());
                    JSONObject object = response.getJSONObject(0);
                    JSONArray visits = response.getJSONArray(1);
                    String phone = object.getString("phone_number");
                    String username = object.getString("username");
                    String occupation = "";
                    boolean isDoctor = object.getBoolean("is_doctor");
                    if (object.has("field"))
                        occupation = object.getString("field");
                    String avatar = "http://10.0.2.2:8000" + object.getString("avatar");
                    String email = object.getString("email");
                    Profile profile = new Profile(username, phone, occupation, email,
                            avatar, isDoctor,createCards(visits));
                    msg.obj = profile;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                msg.what = RESQUEST_FAILED;
                handler.sendMessage(msg);
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return params;
            }
        };
        requestQueue.add(requestProf);
    }

    private ArrayList<ReserveCard> createCards(JSONArray visits) throws JSONException {
        ArrayList<ReserveCard> cards = new ArrayList<>();
        for (int i = 0; i < visits.length(); i++) {
            JSONObject visit = visits.getJSONObject(i);
            String year = visit.getString("year");
            String month = visit.getString("month");
            String day = visit.getString("day");
            String start_hour = visit.getString("start_hour");
            String start_minute = visit.getString("start_minute");
            String end_hour = visit.getString("end_hour");
            String end_minute = visit.getString("end_minute");
            String cardId = visit.getString("id");
            String  patientID = null, patientName = null, patientMobile = null;
            if (!visit.get("patient").toString().equals("null")) {
                JSONObject patient = visit.getJSONObject("patient");
                visit.getJSONObject("patient");
                patientID = patient.getString("id");
                patientName = patient.getString("username");
                patientMobile = patient.getString("phone_number");
            }
            JSONObject doctor = visit.getJSONObject("doctor");
            String doctorID = doctor.getString("id");
            String username = doctor.getString("username");
            String occupation = doctor.getString("field");
            String date = year + "/" + month + "/" + day;
            String start_time = start_hour + ":" + start_minute;
            String end_time = end_hour + ":" + end_minute;
            ReserveCard reserveCard = new ReserveCard(cardId, username, occupation,
                    doctorID, patientID, patientName,
                    patientMobile, date, start_time, end_time);
            cards.add(reserveCard);
        }
        return cards;
    }
}
