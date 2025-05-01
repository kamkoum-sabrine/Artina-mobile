package com.example.artina;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaiementActivity extends AppCompatActivity {

    private EditText numeroCarte, nomCarte, dateExpiration, codeCVV;
    private Button btnValiderPaiement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement);

        // Récupère les données envoyées via l'Intent
        String titreSpectacle = getIntent().getStringExtra("titre_spectacle");
        String lieuSpectacle = getIntent().getStringExtra("lieu_spectacle");
        System.out.println("PAge paiement titre  "+titreSpectacle);
        System.out.println("PAge paiement lieu  "+lieuSpectacle);


        numeroCarte = findViewById(R.id.numeroCarte);
        nomCarte = findViewById(R.id.nomCarte);
        dateExpiration = findViewById(R.id.dateExpiration);
        codeCVV = findViewById(R.id.codeCVV);
        btnValiderPaiement = findViewById(R.id.btnValiderPaiement);

        btnValiderPaiement.setOnClickListener(v -> {
            if (verifierChamps()) {
                genererBilletPDF(titreSpectacle, lieuSpectacle);
            } else {
                Toast.makeText(PaiementActivity.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean verifierChamps() {
        return !numeroCarte.getText().toString().isEmpty()
                && !nomCarte.getText().toString().isEmpty()
                && !dateExpiration.getText().toString().isEmpty()
                && !codeCVV.getText().toString().isEmpty();
    }

    private void genererBilletPDF(String titreSpectacle, String lieuSpectacle) {
        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        paint.setTextSize(16);
        paint.setColor(Color.BLACK);
        System.out.println("dans la fonction "+titreSpectacle);
        System.out.println("dans la fonction "+lieuSpectacle);

        // Texte statique du billet
        canvas.drawText("🎫 Billet Artina", 80, 50, paint);
        //canvas.drawText("Nom: " + nomCarte.getText().toString(), 40, 100, paint);
        canvas.drawText("Spectacle: "+titreSpectacle , 40, 140, paint);
        canvas.drawText(lieuSpectacle, 40, 220, paint);
        canvas.drawText("Date de réservation: "+ LocalDate.now(), 40, 180, paint);

        // Générer QR code
        Bitmap qrCode = genererQRCode("https://example.com/billet/123456");
        if (qrCode != null) {
            canvas.drawBitmap(qrCode, 75, 260, paint);  // Positionner le QR code
        }

        document.finishPage(page);

        // Sauvegarde
        String directoryPath = getExternalFilesDir(null).getPath() + "/";
        String filePath = directoryPath + "billet.pdf";

        try {
            File file = new File(filePath);
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "Billet téléchargé avec succès 📥\nChemin: " + filePath, Toast.LENGTH_LONG).show();

            // Ouvrir le PDF avec une application compatible
            Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Vérifier si une application PDF est disponible pour ouvrir le fichier
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Aucune application trouvée pour ouvrir le PDF", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la génération du billet", Toast.LENGTH_LONG).show();
        }
        document.close();
    }

    private Bitmap genererQRCode(String contenu) {
        try {
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(contenu, BarcodeFormat.QR_CODE, 150, 150);
            BarcodeEncoder encoder = new BarcodeEncoder();
            return encoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }



}