package com.example.pezeshkam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class LoginFragment extends Fragment {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailEditText = view.findViewById(R.id.email_text);
        passwordEditText = view.findViewById(R.id.password_text);

        view.findViewById(R.id.sing_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_login_to_signup);
            }
        });

        view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInputCorrect = true;
                if (emailEditText.getText().toString().isEmpty()) {
                    emailEditText.setError(getString(R.string.empty_email_error));
                    isInputCorrect = false;
                }
                if (passwordEditText.getText().toString().isEmpty()) {
                    passwordEditText.setError(getString(R.string.empty_password_error));
                    isInputCorrect = false;
                }
                if (isInputCorrect) {
                    // TODO: 7/31/2020 login function here
                }
            }
        });
    }
}