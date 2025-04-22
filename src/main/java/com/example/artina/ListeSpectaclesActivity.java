package com.example.artina;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artina.Spectacle;
import com.example.artina.ApiClient;
import com.example.artina.ApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeSpectaclesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SpectacleAdapter adapter;
    private List<Spectacle> spectacleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_spectacles);

        // Initialisation des vues
        recyclerView = findViewById(R.id.recyclerViewSpectacles);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 2 colonnes

        // Initialisation de l'adapter avec une liste vide
        adapter = new SpectacleAdapter(this, spectacleList, spectacle -> {
            Intent intent = new Intent(ListeSpectaclesActivity.this, DetailSpectacleActivity.class);
            intent.putExtra("SPECTACLE_ID", spectacle.getId());
            intent.putExtra("TITRE", spectacle.getTitre());

            intent.putExtra("HEURE_DEBUT", spectacle.getHeureDebut());
            intent.putExtra("ID", spectacle.getId());
            intent.putExtra("LIEU", spectacle.getIdLieu().getNom());
            intent.putExtra("ADRESSE", spectacle.getIdLieu().getAdresse());
            intent.putExtra("VILLE", spectacle.getIdLieu().getVille());
            intent.putExtra("DATES", spectacle.getDate());
            intent.putExtra("SPECTACLE", spectacle);
         //   intent.putExtra("DESCRIPTION", spectacle.getDescription());
            intent.putExtra("IMAGE_PATH", spectacle.getImagePath());


            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Chargement des données depuis l'API
        loadSpectaclesFromApi();
    }

    private void loadSpectaclesFromApi() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Spectacle>> call = apiService.getAllSpectacles();

        call.enqueue(new Callback<List<Spectacle>>() {
            @Override
            public void onResponse(Call<List<Spectacle>> call, Response<List<Spectacle>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    spectacleList.clear();
                    spectacleList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListeSpectaclesActivity.this,
                            "Erreur de chargement des spectacles",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Spectacle>> call, Throwable t) {
                Toast.makeText(ListeSpectaclesActivity.this,
                        "Échec de la connexion: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}