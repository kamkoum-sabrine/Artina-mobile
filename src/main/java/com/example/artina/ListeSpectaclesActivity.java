package com.example.artina;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListeSpectaclesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SpectacleAdapter adapter;
    List<Spectacle> spectacleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_spectacles);

        recyclerView = findViewById(R.id.recyclerViewSpectacles);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 colonnes
       // recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        spectacleList = new ArrayList<>();
        spectacleList.add(new Spectacle("Startup", "Comédie romantique", R.drawable.film1));
        spectacleList.add(new Spectacle("Minecraft", "Aventure basée sur le jeu", R.drawable.film2));
        spectacleList.add(new Spectacle("The Amateur", "Film d’espionnage", R.drawable.prochaine1));
        spectacleList.add(new Spectacle("Piège", "Thriller psychologique", R.drawable.prochaine2));
        spectacleList.add(new Spectacle("Piège", "Thriller psychologique", R.drawable.prochaine2));
        spectacleList.add(new Spectacle("Piège", "Thriller psychologique", R.drawable.prochaine2));
        spectacleList.add(new Spectacle("Piège", "Thriller psychologique", R.drawable.prochaine2));
        spectacleList.add(new Spectacle("Piège", "Thriller psychologique", R.drawable.prochaine2));
        spectacleList.add(new Spectacle("Piège", "Thriller psychologique", R.drawable.prochaine2));
        spectacleList.add(new Spectacle("Piège", "Thriller psychologique", R.drawable.prochaine2));
        spectacleList.add(new Spectacle("Piège", "Thriller psychologique", R.drawable.prochaine2));

      //  adapter = new SpectacleAdapter(spectacleList);
        adapter = new SpectacleAdapter(spectacleList, spectacle -> {
            Intent intent = new Intent(ListeSpectaclesActivity.this, DetailSpectacleActivity.class);
            intent.putExtra("titre", spectacle.getTitre());
            intent.putExtra("description", spectacle.getDescription());
            intent.putExtra("image", spectacle.getImageResource());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}
