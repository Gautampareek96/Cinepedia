package com.example.cinepedia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    RecyclerView recyclerViewOrders;
    List<Order> orderList = new ArrayList<>();
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        loadDummyOrders(); // Replace with DB logic later
        orderAdapter = new OrderAdapter(orderList, this);
        recyclerViewOrders.setAdapter(orderAdapter);
    }

    private void loadDummyOrders() {
        orderList.add(new Order("The Dark Knight", "5 Apr, 7:30 PM", 2, R.drawable.dark_knight));
        orderList.add(new Order("Inception", "6 Apr, 9:00 PM", 1, R.drawable.inception));
    }
}
