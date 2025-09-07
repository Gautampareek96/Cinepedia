package com.example.cinepedia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName, tvProfileEmail;
    private Button btnLogout, btnOrders;
    private DatabaseHelper dbHelper;
    private SharedPreferences prefs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        btnLogout = findViewById(R.id.btnLogout);
        btnOrders = findViewById(R.id.btnOrders);
        dbHelper = new DatabaseHelper(this);
        prefs = getSharedPreferences("user_session", MODE_PRIVATE);

        // Get the current user's name from SharedPreferences
        String username = prefs.getString("username", null);
        if (username != null) {
            String[] userDetails = dbHelper.getUserDetailsByName(username);
            if (userDetails != null) {
                tvProfileName.setText(userDetails[0]);
                tvProfileEmail.setText(userDetails[1]);
            }
        }

        // Logout logic
        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });

        // Your Orders (Uncomment this once you implement OrdersActivity)
        btnOrders.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, OrdersActivity.class));
        });

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.navigation_profile); // Profile selected

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class)); // Switch to MainActivity
                overridePendingTransition(0, 0);
                finish(); // Optional: finish current activity
                return true;
            } else if (item.getItemId() == R.id.navigation_profile) {
                return true; // Already on profile
            }
            return false;
        });
    }
}