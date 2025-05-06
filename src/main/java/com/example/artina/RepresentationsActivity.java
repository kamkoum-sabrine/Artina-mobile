package com.example.artina;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
            //representations = groupeSpectacle.getRepresentations();
            representations = groupeSpectacle.getRepresentations();
            // Mettre à jour l'image du spectacle
            ImageView imageSpectacle = findViewById(R.id.imageSpectacle);
            String imagePath = groupeSpectacle.getImagePrincipale();
            if (imagePath != null && !imagePath.isEmpty()) {
                String imageUrl = "http://192.168.1.187:8081/api/images/" + groupeSpectacle.getImagePrincipale();

//                String imageUrl = "http://localhost:8081/api/images/spectacles/" + spectacle.getImagePath();
                /**    Picasso.get()
                 .load(imageUrl)
                 .placeholder(R.drawable.artina)
                 .error(R.drawable.artina)
                 .into(imageSpectacle);**/
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.artina)
                        .error(R.drawable.artina)
                        .into(imageSpectacle, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("Picasso", "Image loaded successfully: " + imageUrl);
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("Picasso", "Error loading image: " + imageUrl, e);
                            }
                        });

            }
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
