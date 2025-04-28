package com.example.firstapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    private EditText name, email, username, password;
    private Button signupButton;
    private String URL = "https://ce899b937511088b6bf5e339b3d17ad0.serveo.net/mobileApp/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String userName = name.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userUsername = username.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", userName);
            jsonBody.put("email", userEmail);
            jsonBody.put("username", userUsername);
            jsonBody.put("password", userPassword);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonBody,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        String message = response.getString("message");
                        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
                        if (success) finish(); // Go back to login
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SignupActivity.this, "JSON Error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(SignupActivity.this, "Volley Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("SignupActivity", "Volley Error: ", error);
                });

        RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
        queue.add(request);
    }
}
