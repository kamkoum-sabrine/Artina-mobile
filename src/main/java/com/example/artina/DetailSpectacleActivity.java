package com.example.artina;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class DetailSpectacleActivity extends AppCompatActivity {

    ImageView imageView;
    TextView titreText, descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spectacle);

        imageView = findViewById(R.id.detail_image);
        titreText = findViewById(R.id.detail_titre);
        descriptionText = findViewById(R.id.detail_description);

        Intent intent = getIntent();
       // String titre = intent.getStringExtra("titre");
       // String description = intent.getStringExtra("description");
        //int imageRes = intent.getIntExtra("image", -1);
        String titre = intent.getStringExtra("TITRE");
        String description = intent.getStringExtra("DESCRIPTION"); // active si tu veux
        String imagePath = intent.getStringExtra("IMAGE_PATH");

        titreText.setText(titre);
        descriptionText.setText(description);
        //imageView.setImageResource(imagePath);
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
    }
}
