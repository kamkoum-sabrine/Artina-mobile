package com.example.artina;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PDFGenerator {

    public static void genererBilletPDF(Context context, String titreSpectacle) {
        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        paint.setTextSize(16);
        paint.setColor(Color.BLACK);

        canvas.drawText("🎫 Billet Artina", 80, 50, paint);
        canvas.drawText("Spectacle: " + titreSpectacle, 40, 140, paint);
       // canvas.drawText(lieuSpectacle, 40, 220, paint);
        canvas.drawText("Date de réservation: " + java.time.LocalDate.now(), 40, 180, paint);

        Bitmap qrCode = genererQRCode("https://artina.com/billet/" + UUID.randomUUID());
        if (qrCode != null) {
            canvas.drawBitmap(qrCode, 75, 260, paint);
        }

        document.finishPage(page);

        String directoryPath = context.getExternalFilesDir(null).getPath() + "/";
        String filePath = directoryPath + "billet_" + System.currentTimeMillis() + ".pdf";

        try {
            File file = new File(filePath);
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(context, "Billet téléchargé 📥", Toast.LENGTH_LONG).show();

            Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            context.startActivity(intent);

        } catch (IOException | ActivityNotFoundException e) {
            Toast.makeText(context, "Erreur lors de la génération du billet", Toast.LENGTH_SHORT).show();
        }

        document.close();
    }

    private static Bitmap genererQRCode(String data) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 150, 150);
            return new BarcodeEncoder().createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}

