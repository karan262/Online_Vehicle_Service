package com.example.vehicle_try;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    Button book, payment, history;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        book = findViewById(R.id.BookService);
        payment = findViewById(R.id.MakePayment);
        history = findViewById(R.id.HistoryService);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Dashboard.this, MainActivity.class);
                startActivity(intent);
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Dashboard.this, Payments.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Dashboard.this, HistoryBook.class);
                startActivity(intent);
            }
        });
    }
}