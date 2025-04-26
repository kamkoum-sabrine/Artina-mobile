package com.example.artina;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artina.Spectacle;
import com.example.artina.ApiClient;
import com.example.artina.ApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeSpectaclesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SpectacleAdapter adapter;
    private List<GroupeSpectacle> spectaclesGroupes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_spectacles);

        recyclerView = findViewById(R.id.recyclerViewSpectacles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        spectaclesGroupes = new ArrayList<>();

        adapter = new SpectacleAdapter(this, spectaclesGroupes);
        recyclerView.setAdapter(adapter);

        loadSpectaclesFromApi(); // Appel au backend
    }

    private void loadSpectaclesFromApi() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<GroupeSpectacle>> call = apiService.getGroupedSpectacles();

        call.enqueue(new Callback<List<GroupeSpectacle>>() {
            @Override
            public void onResponse(Call<List<GroupeSpectacle>> call, Response<List<GroupeSpectacle>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    spectaclesGroupes.clear();
                    spectaclesGroupes.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListeSpectaclesActivity.this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GroupeSpectacle>> call, Throwable t) {
                Toast.makeText(ListeSpectaclesActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
                Log.e("API", "Erreur réseau", t);
            }
        });
    }
}