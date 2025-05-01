package com.example.artina;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCinema;
    Button btnVoirTousSpectacles;

    private LinearLayout horizontalScrollContent;
    private List<Spectacle> spectacleList = new ArrayList<>();

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
        setContentView(R.layout.activity_main);

        horizontalScrollContent = findViewById(R.id.horizontalScrollContent);

        fetchSpectacles();

        //spinnerCinema = findViewById(R.id.spinnerCinema);
        btnVoirTousSpectacles = findViewById(R.id.btnVoirTousSpectacles);
        // Définir les options du Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.cinemas,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerCinema.setAdapter(adapter);
        setupBottomNavBar("home"); // ou "search", "reservation", "account" selon l'activity

        btnVoirTousSpectacles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListeSpectaclesActivity.class);
                startActivity(intent);
            }
        });
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

        for (int i = 0; i < spectacleList.size(); i++) {
            Spectacle spectacle = spectacleList.get(i);
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(120),
                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, dpToPx(10), 0);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Ajouter un tag avec la position du spectacle
            imageView.setTag(i);

            // Gestion du clic
            imageView.setOnClickListener(v -> {
               // int position = (int) v.getTag();
                //Spectacle selectedSpectacle = spectacleList.get(position);
                //openSpectacleDetail(selectedSpectacle);
                Intent intent = new Intent(MainActivity.this, DetailSpectacleActivity.class);
                intent.putExtra("SPECTACLE_ID", spectacle.getId());
                intent.putExtra("TITRE", spectacle.getTitre());

                intent.putExtra("HEURE_DEBUT", spectacle.getHeureDebut());
                intent.putExtra("ID", spectacle.getId());

                intent.putExtra("DATES", spectacle.getDate());
                intent.putExtra("LIEU", spectacle.getIdLieu().getNom());
                intent.putExtra("ADRESSE", spectacle.getIdLieu().getAdresse());
                intent.putExtra("VILLE", spectacle.getIdLieu().getVille());

                intent.putExtra("SPECTACLE", spectacle);
                //   intent.putExtra("DESCRIPTION", spectacle.getDescription());
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

    private void openSpectacleDetail(Spectacle spectacle) {
        Intent intent = new Intent(this, DetailSpectacleActivity.class);
        intent.putExtra("SPECTACLE_ID", spectacle.getId());
        intent.putExtra("TITRE", spectacle.getTitre());
        intent.putExtra("IMAGE_PATH", spectacle.getImagePath());
        intent.putExtra("DATE", spectacle.getDate());
        // Ajoutez d'autres données si nécessaire
        startActivity(intent);
    }


    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}