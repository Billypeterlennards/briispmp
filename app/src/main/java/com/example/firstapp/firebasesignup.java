package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseSignup extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignup, btnGoLogin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebasesignup);

        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.signupEmail);
        inputPassword = findViewById(R.id.signupPassword);
        progressBar = findViewById(R.id.signupProgressBar);
        btnSignup = findViewById(R.id.btnSignup);
        btnGoLogin = findViewById(R.id.btnGoLogin);

        btnSignup.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                inputEmail.setError("Enter email address!");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                inputPassword.setError("Enter password!");
                return;
            }
            if (password.length() < 6) {
                inputPassword.setError("Password too short, minimum 6 characters!");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // Create user
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(FirebaseSignup.this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(FirebaseSignup.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FirebaseSignup.this, FirebaseLogin.class));
                            finish();
                        } else {
                            Toast.makeText(FirebaseSignup.this, "Signup failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        btnGoLogin.setOnClickListener(v -> startActivity(new Intent(FirebaseSignup.this, FirebaseLogin.class)));
    }
}