package com.example.cinepedia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionActivity extends AppCompatActivity {
    private GridLayout seatGrid;
    private TextView selectedSeatsText;
    private Button btnConfirmSeats;
    private List<String> selectedSeats = new ArrayList<>();
    private int rows = 6, cols = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        seatGrid = findViewById(R.id.seatGrid);
        selectedSeatsText = findViewById(R.id.selectedSeatsText);
        btnConfirmSeats = findViewById(R.id.btnConfirmSeats);

        generateSeats(rows, cols);

        btnConfirmSeats.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(SeatSelectionActivity.this, PaymentActivity.class);
            intent.putStringArrayListExtra("selected_seats", new ArrayList<>(selectedSeats));
            startActivity(intent);
        });
    }

    private void generateSeats(int rows, int cols) {
        seatGrid.removeAllViews(); // Clear previous seats if any
        seatGrid.setColumnCount(cols);

        for (int i = 0; i < rows * cols; i++) {
            TextView seat = new TextView(this);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = dpToPx(48);
            params.height = dpToPx(48);
            params.setMargins(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));
            seat.setLayoutParams(params);

            seat.setBackgroundResource(R.drawable.seat_available);
            seat.setText(String.valueOf(i + 1));
            seat.setGravity(Gravity.CENTER);
            seat.setTextColor(Color.BLACK);
            seat.setTextSize(14);
            seat.setTag("available");

            int seatNumber = i + 1;

            seat.setOnClickListener(v -> {
                if (seat.getTag().equals("available")) {
                    seat.setBackgroundResource(R.drawable.seat_selected);
                    seat.setTag("selected");
                    selectedSeats.add("Seat " + seatNumber);
                } else if (seat.getTag().equals("selected")) {
                    seat.setBackgroundResource(R.drawable.seat_available);
                    seat.setTag("available");
                    selectedSeats.remove("Seat " + seatNumber);
                }

                selectedSeatsText.setText("Selected Seats: " + selectedSeats.toString());
            });

            seatGrid.addView(seat);
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}