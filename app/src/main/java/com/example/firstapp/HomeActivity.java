package com.example.firstapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.ComponentActivity;

public class HomeActivity extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a TextView programmatically
        TextView welcomeTextView = new TextView(this);
        welcomeTextView.setText("Welcome to BRIISP ACADEMY");
        welcomeTextView.setTextSize(24); // Set text size if needed
        welcomeTextView.setPadding(16, 16, 16, 16); // Set padding if needed

        // Set the TextView as the content view of the activity
        setContentView(welcomeTextView);
    }
}
