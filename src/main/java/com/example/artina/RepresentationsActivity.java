package com.example.artina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RepresentationsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewRepresentations;
    private RepresentationAdapter adapter;
    private List<Representation> representations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representations);

        // Récupérer l'objet GroupeSpectacle depuis l'intent
        GroupeSpectacle groupeSpectacle = getIntent().getParcelableExtra("spectacle");

        if (groupeSpectacle != null) {
            representations = groupeSpectacle.getRepresentations();
        }


        // Initialiser le RecyclerView
        recyclerViewRepresentations = findViewById(R.id.recyclerRepresentations);

        // Configurer le RecyclerView avec un LinearLayoutManager
        recyclerViewRepresentations.setLayoutManager(new LinearLayoutManager(this));

        // Créer l'adaptateur en passant le Context et la liste des représentations
        adapter = new RepresentationAdapter(this, representations);  // Passer le Context et la liste

        // Configurer l'adaptateur pour le RecyclerView
        recyclerViewRepresentations.setAdapter(adapter);
    }
}
