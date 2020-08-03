package com.example.pezeshkam.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pezeshkam.Activities.Homepage;
import com.example.pezeshkam.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private EditText usernameEditText, passwordEditText;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameEditText = view.findViewById(R.id.login_username_text);
        passwordEditText = view.findViewById(R.id.login_password_text);
        progressBar = view.findViewById(R.id.login_progress_bar);
        requestQueue = Volley.newRequestQueue(getActivity());
        handler = new Handler();

        usernameEditText.setText(getArguments() != null ? getArguments().getString("username") : null);

        view.findViewById(R.id.sing_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("username", usernameEditText.getText().toString());
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_login_to_signup, bundle);
            }
        });

        view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInputCorrect = true;
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                if (username.isEmpty()) {
                    usernameEditText.setError(getString(R.string.empty_username_error));
                    isInputCorrect = false;
                }
                if (password.isEmpty()) {
                    passwordEditText.setError(getString(R.string.empty_password_error));
                    isInputCorrect = false;
                }
                if (isInputCorrect) {
                    progressBar.setVisibility(View.VISIBLE);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            sendLoginRequest(username, password);
                        }
                    });
                }
            }
        });
    }

    private void sendLoginRequest(final String username, final String password) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                getString(R.string.login_api_url), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        System.out.println("RRR" + response);
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(getActivity(), Homepage.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        System.out.println("EEE" + error);
                        progressBar.setVisibility(View.INVISIBLE);
                        if (error instanceof TimeoutError) {
                            showToast(getString(R.string.server_down_error));
                        } else {
                            showToast(getString(R.string.unsuccessful_login_error));
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}