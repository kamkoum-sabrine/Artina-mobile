package com.example.artina;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

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

        // Récupérer les données utilisateur depuis SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userNom = sharedPref.getString("user_nom", "");
        String userPrenom = sharedPref.getString("user_prenom", "");
        String userEmail = sharedPref.getString("user_email", "");

        // Vérifier si l'utilisateur est connecté
        if (sharedPref.getLong("user_id", -1) != -1) {
            // Utilisateur connecté - remplir les champs
            etNom.setText(sharedPref.getString("user_nom", ""));
            etPrenom.setText(sharedPref.getString("user_prenom", ""));
            etEmail.setText(sharedPref.getString("user_email", ""));

            // Optionnel : rendre les champs non modifiables
            etNom.setEnabled(false);
            etPrenom.setEnabled(false);
            etEmail.setEnabled(false);

            // Optionnel : changer le style visuel
            setDisabledFieldStyle(etNom);
            setDisabledFieldStyle(etPrenom);
            setDisabledFieldStyle(etEmail);
        }

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
            reservation.setBillet(billetSelectionne);
            reservation.setQuantiteBillet(Integer.parseInt(nombreBillets));
            if (sharedPref.getLong("user_id", -1) != -1) {
                Client client = new Client();
                client.setId(sharedPref.getLong("user_id", -1));
                client.setNom(sharedPref.getString("user_nom", ""));
                client.setPrenom(sharedPref.getString("user_prenom", ""));
                client.setEmail(sharedPref.getString("user_email", ""));
                reservation.setClient(client);
            } else {
                reservation.setClient(null);
            }

            // Appeler l'API pour créer la réservation
            createReservation(reservation);

            // Envoie les détails du spectacle à l'activité de paiement
            Intent intent = new Intent(getApplicationContext(), PaiementActivity.class);
            intent.putExtra("titre_spectacle", tvTitreSpectacle.getText());
            intent.putExtra("lieu_spectacle", tvDateLieu.getText());

            startActivity(intent);
        });
    }

    private void setDisabledFieldStyle(EditText editText) {
        editText.setTextColor(ContextCompat.getColor(this, R.color.gray_500));

        // Vérifiez d'abord si le parent est un TextInputLayout
        if (editText.getParent() instanceof TextInputLayout) {
            TextInputLayout layout = (TextInputLayout) editText.getParent();
            layout.setBoxStrokeColor(ContextCompat.getColor(this, R.color.gray_300));
            layout.setHintTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray_500)));
        }
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
                    // Plus besoin de rediriger ici vers PaiementActivity
                } else {
                    // Gérer l'échec de la réponse de l'API
                    Toast.makeText(getApplicationContext(), "Erreur de réservation : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                // Gérer l'échec de la requête réseau
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