package com.example.artina;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Callback;

public class ReservationInviteActivity extends AppCompatActivity {

    private TextView tvTitreSpectacle, tvLieuSpectacle,tvDateSpectacle,tvDateLieu;
    private EditText etNom, etPrenom, etEmail, etNombreBillets;
    private Spinner spinnerCategorieBillet;
    private Button btnConfirmerReservation;

    private List<Billet> billetsDisponibles;
    private Billet billetSelectionne;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation_invite);
        Long spectacleId = getIntent().getLongExtra("spectacle_id", -1);

        tvTitreSpectacle = findViewById(R.id.tvTitreSpectacle);
        tvDateLieu = findViewById(R.id.tvDateLieu);
       // tvDateSpectacle = findViewById(R.id.dateSpectacleReservation);
        etNom = findViewById(R.id.nomReservation);
        etPrenom = findViewById(R.id.prenomReservation);
        etEmail = findViewById(R.id.emailReservation);
        etNombreBillets = findViewById(R.id.nombreBillets);
        spinnerCategorieBillet = findViewById(R.id.spinnerCategorieBillet);
        btnConfirmerReservation = findViewById(R.id.btnConfirmerReservation);

        Long idSpectacle = getIntent().getLongExtra("spectacle_id", -1);
        if (idSpectacle != -1) {
            fetchBillets(idSpectacle);
        }

        btnConfirmerReservation.setOnClickListener(v -> {
            // ici tu peux collecter les données et appeler ton API de réservation
        });
    }
    private void fetchBillets(Long idSpectacle) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<List<Billet>> call = apiService.getBuilletsBySpectacleId(idSpectacle);
        call.enqueue(new Callback<List<Billet>>() {
            @Override
            public void onResponse(Call<List<Billet>> call, Response<List<Billet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    billetsDisponibles = response.body();
                    setupBilletSpinner();
                    System.out.println("Speecttaacleeee "+billetsDisponibles.get(0).getSpectacle().toString());
                    afficherDetailsSpectacle(billetsDisponibles.get(0).getSpectacle());
                } else {
                    Toast.makeText(getApplicationContext(), "Erreur lors du chargement des billets", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Billet>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Échec : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setupBilletSpinner() {
        List<String> categories = new ArrayList<>();
        for (Billet billet : billetsDisponibles) {
            categories.add(billet.getCategorie() + " - " + billet.getPrix() + " DT (" + billet.getNbrerestant() + " restants)");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorieBillet.setAdapter(adapter);

        spinnerCategorieBillet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billetSelectionne = billetsDisponibles.get(position);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void afficherDetailsSpectacle(Spectacle spectacle) {
        tvTitreSpectacle.setText(spectacle.getTitre());
        tvDateLieu.setText("Le " + spectacle.getDate() + " à " + spectacle.getIdLieu().getNom());
    }
}