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

public class FirebaseSignup extends AppCompatActivity {

    private EditText inputFirstName, inputLastName, inputEmail, inputPassword;
    private Button btnSignup, btnGoLogin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebasesignup);

        FirebaseApp.initializeApp(this); // Important for Firebase setup
        auth = FirebaseAuth.getInstance();

        inputFirstName = findViewById(R.id.signupFirstName);
        inputLastName = findViewById(R.id.signupLastName);
        inputEmail = findViewById(R.id.signupEmail);
        inputPassword = findViewById(R.id.signupPassword);
        progressBar = findViewById(R.id.signupProgressBar);
        btnSignup = findViewById(R.id.btnSignup);
        btnGoLogin = findViewById(R.id.btnGoLogin);

        btnSignup.setOnClickListener(v -> {
            String firstName = inputFirstName.getText().toString().trim();
            String lastName = inputLastName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(firstName)) {
                inputFirstName.setError("Enter first name!");
                return;
            }

            if (TextUtils.isEmpty(lastName)) {
                inputLastName.setError("Enter last name!");
                return;
            }

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

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(FirebaseSignup.this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(FirebaseSignup.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                            // Optionally save first and last name in Firestore or Realtime DB here
                            startActivity(new Intent(FirebaseSignup.this, FirebaseLogin.class));
                            finish();
                        } else {
                            Toast.makeText(FirebaseSignup.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        btnGoLogin.setOnClickListener(v -> startActivity(new Intent(FirebaseSignup.this, FirebaseLogin.class)));
    }
}
