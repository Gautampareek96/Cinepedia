package com.example.cinepedia;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private TextView seatSummary;
    private EditText upiIdInput;
    private Button payNowBtn;
    private ArrayList<String> selectedSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        seatSummary = findViewById(R.id.seatSummary);
        upiIdInput = findViewById(R.id.upiIdInput);
        payNowBtn = findViewById(R.id.payNowBtn);

        // Get selected seats from intent
        selectedSeats = getIntent().getStringArrayListExtra("selected_seats");
        String movieName = getIntent().getStringExtra("movie_name");

        if (selectedSeats != null && !selectedSeats.isEmpty()) {
            seatSummary.setText("Selected Seats: " + TextUtils.join(", ", selectedSeats));
        } else {
            Toast.makeText(this, "No seats received", Toast.LENGTH_SHORT).show();
        }

        // Handle mock payment
        payNowBtn.setOnClickListener(v -> {
            String upiId = upiIdInput.getText().toString().trim();

            if (upiId.isEmpty() || !upiId.contains("@")) {
                Toast.makeText(this, "Please enter a valid UPI ID", Toast.LENGTH_SHORT).show();
                return;
            }

            // Move to Booking Confirmed screen
            Intent intent = new Intent(PaymentActivity.this, BookingConfirmedActivity.class);
            intent.putExtra("booked_seats", TextUtils.join(", ", selectedSeats));
            intent.putExtra("movie_name", movieName);
            startActivity(intent);
            finish();
        });
    }
}