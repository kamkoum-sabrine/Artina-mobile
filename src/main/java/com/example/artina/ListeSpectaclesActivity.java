package com.example.artina;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

    EditText etTitre, etDate, etHeure, etNomLieu, etVille;
    Button btnRechercher;


    private EditText etSearch;
    private Spinner spinnerCriteria;

    //  RecyclerView recyclerView;
    //SpectacleAdapter adapterSpectacles;
   // List<Spectacle> listeSpectacles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_spectacles);

        etSearch = findViewById(R.id.etSearch);
        spinnerCriteria = findViewById(R.id.spinnerCriteria);
        btnRechercher = findViewById(R.id.btnRechercher);

        recyclerView = findViewById(R.id.recyclerViewSpectacles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        spectaclesGroupes = new ArrayList<>();
        adapter = new SpectacleAdapter(this, spectaclesGroupes);
        recyclerView.setAdapter(adapter);

        // Initialiser le spinner avec des critères
        ArrayAdapter<String> criteriaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Titre", "Date", "Heure", "Lieu", "Ville"});
        criteriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCriteria.setAdapter(criteriaAdapter);

        loadSpectaclesFromApi();

        btnRechercher.setOnClickListener(v -> {
            String searchValue = etSearch.getText().toString().trim().toLowerCase();
            String selectedCriteria = spinnerCriteria.getSelectedItem().toString();

            List<GroupeSpectacle> filteredList = new ArrayList<>();
            for (GroupeSpectacle groupe : spectaclesGroupes) {
                for (Representation s : groupe.getRepresentations()) {
                    boolean match = false;
                    switch (selectedCriteria) {
                        case "Titre":
                            match = s.getTitre().toLowerCase().contains(searchValue);
                            break;
                        case "Date":
                            match = s.getDates().equalsIgnoreCase(searchValue) ||
                                    DateUtil.compareDates(s.getDates(), searchValue);
                            break;
                        case "Heure":
                            try {
                                double heure = Double.parseDouble(searchValue);
                                match = s.gethDebut() == heure;
                            } catch (NumberFormatException ignored) {}
                            break;
                        case "Lieu":
                            match = s.getLieu().getNom().toLowerCase().contains(searchValue);
                            break;
                        case "Ville":
                            match = s.getLieu().getVille().toLowerCase().contains(searchValue);
                            break;
                    }
                    if (match) {
                        filteredList.add(groupe);
                        break; // une seule correspondance suffit
                    }
                }
            }

            adapter.updateData(filteredList);
        });
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