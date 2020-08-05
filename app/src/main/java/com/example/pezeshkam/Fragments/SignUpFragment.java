package com.example.pezeshkam.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pezeshkam.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText nameEditText;
    private EditText phoneEditText;
    private Switch isDoctorSwitch;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameEditText = view.findViewById(R.id.signup_username_text);
        emailEditText = view.findViewById(R.id.signup_email_text);
        passwordEditText = view.findViewById(R.id.signup_password_text);
        confirmPasswordEditText = view.findViewById(R.id.confirm_password_text);
        isDoctorSwitch = view.findViewById(R.id.doctor_switch);
        nameEditText = view.findViewById(R.id.name_text);
        phoneEditText = view.findViewById(R.id.phone_text);
        progressBar = view.findViewById(R.id.signup_progress_bar);
        requestQueue = Volley.newRequestQueue(getActivity());
        handler = new Handler();

        usernameEditText.setText(getArguments().getString("username"));

        view.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignUpFragment.this)
                        .navigate(R.id.action_signup_to_login);
            }
        });

        view.findViewById(R.id.create_account_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInputCorrect = true;
                final String username = usernameEditText.getText().toString().trim();
                final String email = emailEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();
                final String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                if (username.isEmpty()) {
                    usernameEditText.setError(getString(R.string.empty_username_error));
                    isInputCorrect = false;
                }
                if (email.isEmpty()) {
                    emailEditText.setError(getString(R.string.empty_email_error));
                    isInputCorrect = false;
                }
                if (password.isEmpty()) {
                    passwordEditText.setError(getString(R.string.empty_password_error));
                    isInputCorrect = false;
                }
                if (confirmPassword.isEmpty() | !confirmPassword.equals(password)) {
                    confirmPasswordEditText.setError(getString(R.string.empty_confirm_password_error));
                    isInputCorrect = false;
                }
                if (isInputCorrect) {
                    progressBar.setVisibility(View.VISIBLE);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            sendSignupRequest(username, email, password, confirmPassword);
                        }
                    });
                }
            }
        });
    }

    private void sendSignupRequest(final String username, String email, final String password, String confirmPassword) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("email", email);
            jsonObject.put("password1", password);
            jsonObject.put("password2", confirmPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                getString(R.string.signup_api_url), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
//                        System.out.println("R_signup" + response);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    sendSetProfileRequest(response.get("key").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                        System.out.println("E_signup" + error);
                progressBar.setVisibility(View.INVISIBLE);
                if (error instanceof TimeoutError) {
                    showToast(getString(R.string.server_down_error));
                } else {
                    showToast(getString(R.string.unsuccessful_signup_error));
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
        requestQueue.add(request);
    }

    private void sendSetProfileRequest(final String key) {
        final String username = usernameEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("name", name);
            jsonObject.put("phone_number", phone);
            jsonObject.put("is_doctor", (isDoctorSwitch.isChecked() ? 1 : 0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                getString(R.string.setprofile_api_url), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onCreateAccountSuccessful(username);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    showToast(getString(R.string.server_down_error));
                } else {
                    onCreateAccountSuccessful(username);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Token " + key);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void onCreateAccountSuccessful(String username) {
        progressBar.setVisibility(View.INVISIBLE);
        showToast(getString(R.string.successful_signup_msg));
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        NavHostFragment.findNavController(SignUpFragment.this)
                .navigate(R.id.action_signup_to_login, bundle);
    }

    void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}