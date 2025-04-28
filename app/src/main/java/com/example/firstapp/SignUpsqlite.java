package com.example.firstapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;


public class SignUpsqlite extends ComponentActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize UI components
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        signUpButton = findViewById(R.id.signup_button);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Set click listener for Sign Up button
        signUpButton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpsqlite.this, "Please fill in all fields",
                        Toast.LENGTH_SHORT).show();
            } else {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("email", email);
                values.put("username", username);
                values.put("password", password);

                long newRowId = db.insert("users", null, values);

                if (newRowId == -1) {
                    Toast.makeText(SignUpsqlite.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpsqlite.this, "Sign up successful",
                            Toast.LENGTH_SHORT).show();
                }

                db.close();
            }
        });
    }
}