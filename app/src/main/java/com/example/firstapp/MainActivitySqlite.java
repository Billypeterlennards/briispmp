package com.example.firstapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

public class MainActivitySqlite extends ComponentActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signUpTextView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.button2);
        signUpTextView = findViewById(R.id.signup);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Set click listener for Login button
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivitySqlite.this, "Please fill in the email and password fields", Toast.LENGTH_SHORT).show();
            } else {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(
                        "users",
                        new String[]{"id"},
                        "email=? AND password=?",
                        new String[]{email, password},
                        null, null, null
                );

                if (cursor != null && cursor.moveToFirst()) {
                    Toast.makeText(MainActivitySqlite.this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Open HomeActivity
                    Intent intent = new Intent(MainActivitySqlite.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Optional: close MainActivity so the user can't go back to it with the back button
                } else {
                    Toast.makeText(MainActivitySqlite.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }

                if (cursor != null) {
                    cursor.close();
                }
                db.close();
            }
        });

        // Set click listener for Sign Up text view
        signUpTextView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivitySqlite.this, SignUp.class);
            startActivity(intent);
        });
    }
}
