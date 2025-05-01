package com.example.artina;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSpectacleActivity extends AppCompatActivity {

    ImageView imageView;
    TextView titreText, hdebutText;

    Button btnReserver;
    private BilletAdapter adapter;
    private RecyclerView recyclerView;


    private List<Billet> billetsList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spectacle);
        recyclerView = findViewById(R.id.recyclerViewBillets);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 2 colonnes
        recyclerView.setAdapter(adapter);
        imageView = findViewById(R.id.detail_image);
        titreText = findViewById(R.id.detail_titre);
        btnReserver = findViewById(R.id.btnReserver);

        TextView lieuNom = findViewById(R.id.lieuNom);
        TextView lieuAdresse = findViewById(R.id.lieuAdresse);
        TextView lieuVille = findViewById(R.id.lieuVille);
        TextView dateHeureSpectacle = findViewById(R.id.dateHeureSpectacleDetail);
        Intent intent = getIntent();

        Spectacle spectacle = (Spectacle) getIntent().getSerializableExtra("SPECTACLE");
        TextView lieuSpectacle = findViewById(R.id.lieuSpectacle);
        String titre = intent.getStringExtra("TITRE");
        String description = intent.getStringExtra("DESCRIPTION"); // active si tu veux
        String imagePath = intent.getStringExtra("IMAGE_PATH");
        String lieu = intent.getStringExtra("LIEU");
        String ville = intent.getStringExtra("VILLE");
        String adresse = intent.getStringExtra("ADRESSE");

        Double hdebut = getIntent().getDoubleExtra("HEURE_DEBUT", 0.0); // 0.0 est la valeur par défaut
        String dates = intent.getStringExtra("DATES");
        Long idSpec = getIntent().getLongExtra("ID", 0); // 0.0 est la valeur par défaut

        titreText.setText(titre);
        lieuNom.setText(lieu);
        lieuAdresse.setText(adresse);
        lieuVille.setText(ville);

       try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(dates);
            String dateFormatted = dateFormat.format(date);
            String heureFormatted = String.format(Locale.getDefault(), "%dh%02d",
                    hdebut.intValue(), 0);
            dateHeureSpectacle.setText(String.format("%s • %s", dateFormatted, heureFormatted));
        } catch (Exception e) {
            dateHeureSpectacle.setText("Date non disponible");
            Log.e("DateFormat", "Erreur formatage date", e);
        }

        if (imagePath != null && !imagePath.isEmpty()) {
            String imageUrl = "http://192.168.1.187:8081/api/images/" + imagePath;
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.artina)
                    .error(R.drawable.artina)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.artina); // fallback local
        }
        recyclerView.setAdapter(adapter);

        // Chargement des données depuis l'API
        System.out.println("id  "+idSpec);
        loadBilletsFromApi(idSpec);
        btnReserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReservationDialog(idSpec);
            }
        });
    }

    private void loadBilletsFromApi(Long idSpectacles) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Billet>> call = apiService.getBuilletsBySpectacleId(idSpectacles);

        call.enqueue(new Callback<List<Billet>>() {
            @Override
            public void onResponse(Call<List<Billet>> call, Response<List<Billet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Mettez à jour les données de l'adaptateur
                    adapter = new BilletAdapter(DetailSpectacleActivity.this,
                            response.body(),
                            billet -> {
                                // Gestion du clic
                            });
                    recyclerView.setAdapter(adapter);

                    // Ou alternative plus efficace :
                    // adapter.updateData(response.body());
                } else {
                    Toast.makeText(DetailSpectacleActivity.this,
                            "Erreur de chargement des billets",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Billet>> call, Throwable t) {
                Toast.makeText(DetailSpectacleActivity.this,
                        "Échec de la connexion: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showReservationDialog(Long idSpec) {
        // 1. Utilisez le contexte de l'activité (THIS est crucial)
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetStyle);

        // 2. Inflatez le layout SANS le rattacher à une racine
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_reservation, null);
        dialog.setContentView(dialogView);

        // 3. Empêchez le fond blanc parasite
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Configuration des boutons
        Button btnSeConnecter = dialog.findViewById(R.id.btnSeConnecter);
        Button btnSInscrire = dialog.findViewById(R.id.btnSInscrire);
        Button btnContinuerInvite = dialog.findViewById(R.id.btnContinuerInvite);

        btnSeConnecter.setOnClickListener(v -> {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("spectacle_id", idSpec); // Passer l'ID de spectacle
            startActivity(loginIntent);
            dialog.dismiss();
        });

        btnSInscrire.setOnClickListener(v -> {
            Intent loginIntent = new Intent(this, RegisterActivity.class);
            loginIntent.putExtra("spectacle_id", idSpec); // Passer l'ID de spectacle
            startActivity(loginIntent);
            dialog.dismiss();
        });

        btnContinuerInvite.setOnClickListener(v -> {
            Intent loginIntent = new Intent(this, ReservationInviteActivity.class);
            loginIntent.putExtra("spectacle_id", idSpec); // Passer l'ID de spectacle
            startActivity(loginIntent);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void continuerEnInvite() {
        // Ici, tu peux lancer l'activité de réservation directement
        Intent intent = new Intent(getApplicationContext(), ReservationInviteActivity.class);
        startActivity(intent);
    }
}
