package com.example.pezeshkam.Threads;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pezeshkam.Models.DoctorCard;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.pezeshkam.Activities.Homepage.EMPTY_RESULT;
import static com.example.pezeshkam.Activities.Homepage.NON_EMPTY_RESULT;
import static com.example.pezeshkam.Activities.Homepage.RESQUEST_FAILED;

public class HomepageThread extends Thread {
    private Handler handler;
    Context context;
    String URL;
    String token;

    public HomepageThread(Handler handler, Context context, String URL, String token) {
        this.handler = handler;
        this.context = context;
        this.URL = URL;
        this.token = token;
    }

    @Override
    public void run() {
        super.run();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, this.URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("response is", response.toString());
                        try {
                            parsingResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error is", error.toString());
                Message msg = new Message();
                msg.what = RESQUEST_FAILED;
                handler.sendMessage(msg);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Token " + token);
                params.put("Content-type", "application/json");
                Log.i("volley header", params.toString());
                return params;
            }
        };
        requestQueue.add(request);

    }

    private void parsingResponse(JSONArray response) throws JSONException {
        ArrayList<DoctorCard> doctors = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            JSONObject object = response.getJSONObject(i);
            String username = (String) object.get("username");
            String occupation = (String) object.get("field");
            String phone = (String) object.get("phone_number");
//            String image = (String) object.get("image");
            DoctorCard doctorCard = new DoctorCard(username, phone, "", occupation);
            doctors.add(doctorCard);
        }
        Message message = new Message();
        if (response.length() != 0)
            message.what = NON_EMPTY_RESULT;
        else
            message.what = EMPTY_RESULT;
        message.obj = doctors;
        handler.sendMessage(message);
    }
}
