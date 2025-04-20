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
        String titre = intent.getStringExtra("titre");
        String description = intent.getStringExtra("description");
        int imageRes = intent.getIntExtra("image", -1);

        titreText.setText(titre);
        descriptionText.setText(description);
        imageView.setImageResource(imageRes);
    }
}
