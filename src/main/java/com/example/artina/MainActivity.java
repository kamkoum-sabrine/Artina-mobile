package com.example.artina;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private LinearLayout horizontalScrollContent;
    private List<Spectacle> spectacleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalScrollContent = findViewById(R.id.horizontalScrollContent);

        fetchSpectacles();
        setupBottomNavBar("home");



        Button btnVoirTousSpectacles = findViewById(R.id.btnVoirTousSpectacles);
        btnVoirTousSpectacles.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListeSpectaclesActivity.class);
            startActivity(intent);
        });
    }

    private void setupBottomNavBar(String currentPage) {
        LinearLayout navHome = findViewById(R.id.nav_home);
        LinearLayout navSearch = findViewById(R.id.nav_search);
        LinearLayout navReservation = findViewById(R.id.nav_reservation);

        ImageView iconHome = findViewById(R.id.icon_home);
        ImageView iconReservation = findViewById(R.id.icon_reservation);

        int activeColor = Color.parseColor("#e50914");
        int inactiveColor = Color.WHITE;

        iconHome.setColorFilter(currentPage.equals("home") ? activeColor : inactiveColor);
        iconReservation.setColorFilter(currentPage.equals("reservation") ? activeColor : inactiveColor);

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

        navReservation.setOnClickListener(v -> showReservationDialog(null));
    }

    private void fetchSpectacles() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.187:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Spectacle>> call = apiService.getAllSpectacles();

        call.enqueue(new Callback<List<Spectacle>>() {
            @Override
            public void onResponse(Call<List<Spectacle>> call, Response<List<Spectacle>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    spectacleList = response.body();
                    displaySpectacleImages();
                }
            }

            @Override
            public void onFailure(Call<List<Spectacle>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displaySpectacleImages() {
        horizontalScrollContent.removeAllViews();

        for (Spectacle spectacle : spectacleList) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(120),
                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, dpToPx(10), 0);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, DetailSpectacleActivity.class);
                intent.putExtra("SPECTACLE_ID", spectacle.getId());
                intent.putExtra("TITRE", spectacle.getTitre());
                intent.putExtra("HEURE_DEBUT", spectacle.getHeureDebut());
                intent.putExtra("DATES", spectacle.getDate());
                intent.putExtra("LIEU", spectacle.getIdLieu().getNom());
                intent.putExtra("ADRESSE", spectacle.getIdLieu().getAdresse());
                intent.putExtra("VILLE", spectacle.getIdLieu().getVille());
                intent.putExtra("IMAGE_PATH", spectacle.getImagePath());
                startActivity(intent);
            });

            if (spectacle.getImagePath() != null && !spectacle.getImagePathVertical().isEmpty()) {
                String imageUrl = "http://192.168.1.187:8081/api/images/" + spectacle.getImagePathVertical();
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.artina)
                        .error(R.drawable.artina)
                        .into(imageView);
            }

            horizontalScrollContent.addView(imageView);
        }
    }

    private void showReservationDialog(Long idSpec) {
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getLong("user_id", -1) != -1;

        if (isLoggedIn) {
            showUserReservationsDialog(sharedPref, idSpec);
        } else {
            showAuthDialog(idSpec);
        }
    }

    private void showAuthDialog(Long idSpec) {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetStyle);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_auth, null);
        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnSeConnecter = dialog.findViewById(R.id.btnLogin);
        Button btnSInscrire = dialog.findViewById(R.id.btnRegister);

        btnSeConnecter.setOnClickListener(v -> {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("spectacle_id", idSpec);
            startActivity(loginIntent);
            dialog.dismiss();
        });

        btnSInscrire.setOnClickListener(v -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            registerIntent.putExtra("spectacle_id", idSpec);
            startActivity(registerIntent);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showUserReservationsDialog(SharedPreferences sharedPref, Long idSpec) {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetStyle);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_user_reservations, null);
        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvUserName = dialogView.findViewById(R.id.tvUserName);
        Button btnViewReservations = dialogView.findViewById(R.id.btnViewReservations);
        Button btnLogout = dialogView.findViewById(R.id.btnLogout); // Référence au bouton dans le dialog

        String userName = sharedPref.getString("user_nom", "") + " " +
                sharedPref.getString("user_prenom", "");
        tvUserName.setText(userName);

        btnLogout.setOnClickListener(v -> {
            dialog.dismiss();
            performLogout();
        });

        btnViewReservations.setOnClickListener(v -> {
            Intent reservationsIntent = new Intent(this, ReservationsActivity.class);
            startActivity(reservationsIntent);
            dialog.dismiss();
        });

        dialog.show();
    }
    private void performLogout() {
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        sharedPref.edit().clear().apply();
        ApiClient.clearCache();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}