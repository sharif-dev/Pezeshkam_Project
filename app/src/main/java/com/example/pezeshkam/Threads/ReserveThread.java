package com.example.pezeshkam.Threads;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

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
import com.example.pezeshkam.Adapters.ProfileAdapter;
import com.example.pezeshkam.Models.Profile;
import com.example.pezeshkam.Models.ReserveCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.pezeshkam.Activities.Homepage.NON_EMPTY_RESULT;
import static com.example.pezeshkam.Activities.Homepage.REQUEST_SUCCEED;
import static com.example.pezeshkam.Activities.Homepage.RESQUEST_FAILED;
import static com.example.pezeshkam.Activities.Homepage.USER_ID;
import static com.example.pezeshkam.Threads.HomepageThread.params;

public class ReserveThread extends Thread {
    int rid, pid;
    Context context;
    String type;
    ArrayList<ReserveCard> cards;
    Switch sw;
    ProfileAdapter adapter;
    Handler handler;
    public ReserveThread(int rid, int pid, Context context, ArrayList<ReserveCard> cards,
                         Switch sw, ProfileAdapter adapter, Handler handler) {
        this.rid = rid;
        this.pid = pid;
        this.context = context;
        this.cards = cards;
        this.sw = sw;
        this.adapter = adapter;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        final Message msg = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = "http://10.0.2.2:8000/reserve/";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("reservation_id", this.rid);
            jsonBody.put("patient_id", this.pid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = jsonBody.toString();
        StringRequest requestDelCan = new StringRequest(Request.Method.POST, URL
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ReserveCard card = null;
                for(ReserveCard cardd: cards) {
                    if (cardd.getId().equals(Integer.toString(rid)))
                        card = cardd;
                }
                card.toggleCached();
                sw.setChecked(card.isCatched());
                adapter.notifyDataSetChanged();
                msg.what = REQUEST_SUCCEED;
                handler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                msg.what = RESQUEST_FAILED;
                handler.sendMessage(msg);
            }
        }){
            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
            @Override
            public Map<String, String> getHeaders() {
                return params;
            }
        } ;
        requestQueue.add(requestDelCan);
    }
}
