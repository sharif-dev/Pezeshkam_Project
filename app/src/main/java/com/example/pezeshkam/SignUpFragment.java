package com.example.pezeshkam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SignUpFragment extends Fragment {

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Switch isDoctorSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameEditText = view.findViewById(R.id.name_text);
        phoneEditText = view.findViewById(R.id.phone_text);
        emailEditText = view.findViewById(R.id.email_text);
        passwordEditText = view.findViewById(R.id.password_text);
        confirmPasswordEditText = view.findViewById(R.id.confirm_password_text);
        isDoctorSwitch = view.findViewById(R.id.doctor_switch);

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
                if (nameEditText.getText().toString().isEmpty()) {
                    nameEditText.setError(getString(R.string.empty_name_error));
                    isInputCorrect = false;
                }
                if (phoneEditText.getText().toString().isEmpty()) {
                    phoneEditText.setError(getString(R.string.empty_phone_error));
                    isInputCorrect = false;
                }
                if (emailEditText.getText().toString().isEmpty()) {
                    emailEditText.setError(getString(R.string.empty_email_error));
                    isInputCorrect = false;
                }
                if (passwordEditText.getText().toString().isEmpty()) {
                    passwordEditText.setError(getString(R.string.empty_password_error));
                    isInputCorrect = false;
                }
                if (confirmPasswordEditText.getText().toString().isEmpty()
                        | !confirmPasswordEditText.getText().toString().equals(emailEditText.getText().toString())) {
                    confirmPasswordEditText.setError(getString(R.string.empty_confirm_password_error));
                    isInputCorrect = false;
                }
                if (isInputCorrect) {
                    // TODO: 7/31/2020 signup function here
                }
            }
        });
    }
}