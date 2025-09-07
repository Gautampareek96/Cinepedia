package com.example.cinepedia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingConfirmedActivity extends AppCompatActivity {

    private TextView tvSeats, tvMovieName;
    private Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmed);

        // Find views
        tvSeats = findViewById(R.id.tvSeats);
        tvMovieName = findViewById(R.id.tvMovieName);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Get data from intent
        String bookedSeats = getIntent().getStringExtra("booked_seats");
        String movieName = getIntent().getStringExtra("movie_name");

        // Set dynamic data
        if (movieName != null) {
            tvMovieName.setText("Movie: " + movieName);
        }
        if (bookedSeats != null) {
            tvSeats.setText("Seats: " + bookedSeats);
        }

        // Back to home
        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(BookingConfirmedActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}