package com.example.pezeshkam.Threads;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pezeshkam.Adapters.ProfileAdapter;
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

public class DelCanThread extends Thread {
    int rid;
    Context context;
    String type;
    ArrayList<ReserveCard> cards;
    ProfileAdapter adapter;
    public DelCanThread(int rid, Context context, String type,
                        ArrayList<ReserveCard> cards, ProfileAdapter adapter) {
        this.rid = rid;
        this.context = context;
        this.type = type;
        this.cards = cards;
        this.adapter = adapter;
    }

    @Override
    public void run() {
        super.run();
        final Message msg = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = "http://10.0.2.2:8000/" + this.type + "/";
        final Toast toast = Toast.makeText(context, "message", Toast.LENGTH_LONG);
        JsonArrayRequest requestDelCan = new JsonArrayRequest(Request.Method.POST, URL, null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ReserveCard card = null;
                for(ReserveCard cardd: cards) {
                    if (cardd.getId().equals(Integer.toString(rid)))
                        card = cardd;
                }
                if (type.equals("delete_reservation/"))
                    cards.remove(card);
                else if (type.equals("delete_patient_reservation/")) {
                    card.setPatientID(null);
                }
                toast.setText("درخواست با موفقیت انجام شد");
                toast.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast.setText("درخواست با خطا مواجه شد");
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("reservation_id",Integer.toString(rid));
                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                return params;
            }
        } ;
        requestQueue.add(requestDelCan);
    }
}
