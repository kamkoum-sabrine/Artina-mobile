package com.example.artina;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailSpectacleActivity extends AppCompatActivity {

    ImageView imageView;
    TextView titreText, hdebutText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spectacle);

        imageView = findViewById(R.id.detail_image);
        titreText = findViewById(R.id.detail_titre);
     //   hdebutText = findViewById(R.id.h_debut);
        TextView dateHeureSpectacle = findViewById(R.id.dateHeureSpectacleDetail);
      //  TextView dateSpectacle = findViewById(R.id.dateSpectacle);
      //  TextView heureSpectacle = findViewById(R.id.heureSpectacle);
        Intent intent = getIntent();
       // String titre = intent.getStringExtra("titre");
       // String description = intent.getStringExtra("description");
        //int imageRes = intent.getIntExtra("image", -1);

        Spectacle spectacle = (Spectacle) getIntent().getSerializableExtra("SPECTACLE");
        TextView lieuSpectacle = findViewById(R.id.lieuSpectacle);
        String titre = intent.getStringExtra("TITRE");
        String description = intent.getStringExtra("DESCRIPTION"); // active si tu veux
        String imagePath = intent.getStringExtra("IMAGE_PATH");
       // String hdebut = intent.getStringExtra("HEURE_DEBUT");
        Double hdebut = getIntent().getDoubleExtra("HEURE_DEBUT", 0.0); // 0.0 est la valeur par défaut
        System.out.println("heure spec " + hdebut);
        String dates = intent.getStringExtra("DATES");
        titreText.setText(titre);
    //    dateSpectacle.setText(dates);
        //heureSpectacle.setText(String.valueOf(hdebut));
      System.out.println("heure spec "+hdebut);
       // hdebutText.setText(hdebut);

        // Formatage date et heure
        // Formatage et affichage de la date et heure
       try {
            // Formatage de la date (ex: "2025-04-23" -> "23/04/2025")
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(dates);
            String dateFormatted = dateFormat.format(date);

            // Formatage de l'heure (ex: 19 -> "19h00")
            String heureFormatted = String.format(Locale.getDefault(), "%dh%02d",
                    hdebut.intValue(), 0);

            dateHeureSpectacle.setText(String.format("%s • %s", dateFormatted, heureFormatted));

        } catch (Exception e) {
            dateHeureSpectacle.setText("Date non disponible");
            Log.e("DateFormat", "Erreur formatage date", e);
        }

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
