package com.example.vehicle_try;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    Button book, payment, history, current;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        book = findViewById(R.id.BookService);
        payment = findViewById(R.id.MakePayment);
        history = findViewById(R.id.HistoryService);
        current = findViewById(R.id.CurrentServiceStatus);
        BottomNavigationView navigationView = findViewById(R.id.navibottom);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        home home1 = new home();
        FragmentTransaction f1 = getSupportFragmentManager().beginTransaction();
        f1.replace(R.id.content, home1, "");
        f1.commit();
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
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Dashboard.this, CurrentStatus.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.editProfile:
                Intent intent = new Intent(Dashboard.this, UpdateProfile.class);
                startActivity(intent);
                break;
            case R.id.Logout:
                SaveSharedPreference.clear(getApplicationContext());
                Intent intent1 = new Intent(Dashboard.this, Login.class);
                startActivity(intent1);
                finish();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_location:
                            home home2 = new home();
                            FragmentTransaction f1 = getSupportFragmentManager().beginTransaction();
                            f1.replace(R.id.content, home2, "");
                            f1.commit();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                            return true;
                        case R.id.nav_history:
                            Intent intenth = new Intent(getApplicationContext(), HistoryBook.class);
                            startActivity(intenth);

                            return true;
                        case R.id.nav_profile:
                            Intent intentp = new Intent(getApplicationContext(), UpdateProfile.class);
                            startActivity(intentp);

                            return true;

                    }
                    return false;
                }
            };
}
