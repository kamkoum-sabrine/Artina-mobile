package com.example.artina;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter adapter;

    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        // Initialisation
        recyclerView = findViewById(R.id.rvReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  btnLogout = findViewById(R.id.btnLogout);
        // Récupérer l'ID de l'utilisateur connecté
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        long userId = sharedPref.getLong("user_id", -1);

        if (userId != -1) {
            fetchUserReservations(userId);


        } else {
            Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
            finish();
        }



    }



    private void fetchUserReservations(long userId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Reservation>> call = apiService.getReservationsByUser(userId);

        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setupRecyclerView(response.body());
                } else {
                    Toast.makeText(ReservationsActivity.this, "Aucune réservation trouvée", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Toast.makeText(ReservationsActivity.this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView(List<Reservation> reservations) {
        adapter = new ReservationAdapter(reservations);
        recyclerView.setAdapter(adapter);
    }
}