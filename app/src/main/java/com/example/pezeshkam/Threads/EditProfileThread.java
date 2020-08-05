package com.example.pezeshkam.Threads;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.pezeshkam.Activities.Homepage.NON_EMPTY_RESULT;
import static com.example.pezeshkam.Activities.Homepage.REQUEST_SUCCEED;
import static com.example.pezeshkam.Activities.Homepage.RESQUEST_FAILED;
import static com.example.pezeshkam.Activities.Homepage.USER_ID;
import static com.example.pezeshkam.Threads.HomepageThread.params;

public class EditProfileThread extends Thread {
    Context context;
    Profile profile;
    @Nullable Bitmap image;
    Handler handler;
    int uID;
    public EditProfileThread(Context context, Profile profile, Handler handler, int uID,
                             @Nullable Bitmap image) {
        this.context = context;
        this.profile = profile;
        this.image = image;
        this.handler = handler;
        this.uID = uID;
    }

    @Override
    public void run() {
        super.run();
        HttpClient httpClient = new DefaultHttpClient();
        Message msg = new Message();
        HttpPost postRequest = new HttpPost("http://10.0.2.2:8000/edit_profile/");
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            Log.i("tag1", profile.getEmail());
            reqEntity.addPart("username", new StringBody(profile.getUsername()));
            reqEntity.addPart("field", new StringBody(profile.getOccupation()));
            reqEntity.addPart("phone_number", new StringBody(profile.getPhone()));
            reqEntity.addPart("user_id", new StringBody(String.valueOf(uID)));
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
//            byte[] data = bos.toByteArray();
//            ByteArrayBody bab = new ByteArrayBody(data, "forest.jpg");
//            reqEntity.addPart("picture", bab);
            byte[] data = null;
            if(image!=null){
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                data = bos.toByteArray();
                reqEntity.addPart("avatar", new ByteArrayBody(data,
                        "image/jpeg", "test2.jpg"));
            }

            postRequest.setHeader("Authorization", params.get("Authorization"));
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();
            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            msg.what = REQUEST_SUCCEED;
        } catch (Exception e) {
            msg.what = RESQUEST_FAILED;
        }
        handler.sendMessage(msg);
    }
}
