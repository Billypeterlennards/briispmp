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

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseLogin extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnLogin, btnSignup;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebaselogin);

        // Initialize Firebase (fixes crash)
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        // Initialize UI components
        inputEmail = findViewById(R.id.loginEmail);
        inputPassword = findViewById(R.id.loginPassword);
        progressBar = findViewById(R.id.loginProgressBar);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnGoSignup);

        // Login button action
        btnLogin.setOnClickListener(view -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                inputEmail.setError("Enter email!");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                inputPassword.setError("Enter password!");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(FirebaseLogin.this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(FirebaseLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FirebaseLogin.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(FirebaseLogin.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Go to Signup
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(FirebaseLogin.this, FirebaseSignup.class);
            startActivity(intent);
        });
    }
}
