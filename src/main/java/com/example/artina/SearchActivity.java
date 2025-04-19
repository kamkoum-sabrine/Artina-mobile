package com.example.artina;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.artina.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySearchBinding binding;

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

        setContentView(R.layout.activity_search);
        setupBottomNavBar("search"); // ou "search", "reservation", "account" selon l'activity

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_search);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}