package com.example.artina;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReservationActivity extends AppCompatActivity {
    private void setupBottomNavBar(String currentPage) {
        LinearLayout navHome = findViewById(R.id.nav_home);
        LinearLayout navSearch = findViewById(R.id.nav_search);
        LinearLayout navReservation = findViewById(R.id.nav_reservation);

        ImageView iconHome = findViewById(R.id.icon_home);
        ImageView iconSearch = findViewById(R.id.icon_search);
        ImageView iconReservation = findViewById(R.id.icon_reservation);

        // Highlight current
        int activeColor = Color.parseColor("#e50914"); // Couleur sélectionnée
        int inactiveColor = Color.WHITE;

        iconHome.setColorFilter(currentPage.equals("home") ? activeColor : inactiveColor);
        iconSearch.setColorFilter(currentPage.equals("search") ? activeColor : inactiveColor);
        iconReservation.setColorFilter(currentPage.equals("reservation") ? activeColor : inactiveColor);

        // Redirections
        navHome.setOnClickListener(v -> {
            if (!currentPage.equals("home")) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });

        navSearch.setOnClickListener(v -> {
            if (!currentPage.equals("search")) {
                startActivity(new Intent(this, SearchActivity.class));
                finish();
            }
        });

        navReservation.setOnClickListener(v -> {
            if (!currentPage.equals("reservation")) {
                startActivity(new Intent(this, ReservationActivity.class));
                finish();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation);

        setupBottomNavBar("reservation"); // ou "search", "reservation", "account" selon l'activity


    }
}