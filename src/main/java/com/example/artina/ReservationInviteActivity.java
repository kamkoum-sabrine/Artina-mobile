package com.example.artina;

import android.annotation.SuppressLint;
import android.content.Intent;
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
            // Collecte des informations utilisateur
            String nom = etNom.getText().toString();
            String prenom = etPrenom.getText().toString();
            String email = etEmail.getText().toString();
            String nombreBillets = etNombreBillets.getText().toString();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || nombreBillets.isEmpty() || billetSelectionne == null) {
                Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }



            Reservation reservation = new Reservation();
            reservation.setSpectacle(new Spectacle(spectacleId)); // Ajoute l'ID du spectacle
            //reservation.setClient(client);
            reservation.setBillet(billetSelectionne);
           // reservation.setDateReservation(LocalDate.now()); // Utilise la date actuelle pour la réservation
            reservation.setQuantiteBillet(Integer.parseInt(nombreBillets));

            // Appeler l'API pour créer la réservation
            createReservation(reservation);
        });
    }

    private void createReservation(Reservation reservation) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<ReservationResponse> call = apiService.createReservation(reservation);

        call.enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                if (response.isSuccessful()) {
                    // La réservation a été créée avec succès
                    ReservationResponse reservationResponse = response.body();
                    Toast.makeText(getApplicationContext(), "Réservation réussie !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PaiementActivity.class);
                    // Tu peux passer les infos si besoin avec intent.putExtra(...)
                    startActivity(intent);
                } else {
                    // Gérer l'échec de la réponse de l'API
                    System.out.println("Erreeeeyuuuuuu "+response.message());
                    Toast.makeText(getApplicationContext(), "Erreur de réservation : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                // Gérer l'échec de la requête réseau
                System.out.println("Erreeeeyuuuuuu "+t.getMessage());

                Toast.makeText(getApplicationContext(), "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
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
                    System.out.println("Speecttaacleeee "+billetsDisponibles.get(0).toString());
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