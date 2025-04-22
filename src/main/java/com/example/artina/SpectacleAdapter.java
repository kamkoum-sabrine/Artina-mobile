package com.example.artina;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SpectacleAdapter extends RecyclerView.Adapter<SpectacleAdapter.ViewHolder> {

    private Context context;
    private List<Spectacle> spectacleList;
    private OnSpectacleClickListener listener;

    public interface OnSpectacleClickListener {
        void onSpectacleClick(Spectacle spectacle);
    }

    public SpectacleAdapter(Context context, List<Spectacle> spectacleList, OnSpectacleClickListener listener) {
        this.context = context;
        this.spectacleList = spectacleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_spectacle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Spectacle spectacle = spectacleList.get(position);
        holder.bind(spectacle, listener);
    }

    @Override
    public int getItemCount() {
        return spectacleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageSpectacle;
        TextView titreSpectacle;
        TextView descriptionSpectacle;
        TextView dateHeureSpectacle;

        TextView hdebutSpectacle;

        TextView lieuSpectacle;

        public ViewHolder(View itemView) {
            super(itemView);
            imageSpectacle = itemView.findViewById(R.id.imageSpectacle);
            titreSpectacle = itemView.findViewById(R.id.titreSpectacle);
         //   descriptionSpectacle = itemView.findViewById(R.id.descriptionSpectacle);
            dateHeureSpectacle = itemView.findViewById(R.id.dateHeureSpectacle);
          //  hdebutSpectacle = itemView.findViewById(R.id.hdebutSpectacle);
            lieuSpectacle = itemView.findViewById(R.id.lieuSpectacle); // Ajouté
        }

        public void bind(Spectacle spectacle, OnSpectacleClickListener listener) {
            // Chargement de l'image avec Picasso
            if (spectacle.getImagePath() != null && !spectacle.getImagePath().isEmpty()) {
                String imageUrl = "http://192.168.1.187:8081/api/images/" + spectacle.getImagePath();

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

            //   titreSpectacle.setText(spectacle.getTitre());
          //  descriptionSpectacle.setText(spectacle.getDescription());
          //  dateSpectacle.setText(spectacle.getDate()); // Affichage de la date
           // hdebutSpectacle.setText(spectacle.getH_debut());
            titreSpectacle.setText(spectacle.getTitre());
            //dateSpectacle.setText(spectacle.getDate());

            // Formatage date et heure
            // Formatage et affichage de la date et heure
            try {
                // Formatage de la date (ex: "2025-04-23" -> "23/04/2025")
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = inputFormat.parse(spectacle.getDate());
                String dateFormatted = dateFormat.format(date);

                // Formatage de l'heure (ex: 19 -> "19h00")
                String heureFormatted = String.format(Locale.getDefault(), "%dh%02d",
                        spectacle.getHeureDebut().intValue(), 0);

                dateHeureSpectacle.setText(String.format("%s • %s", dateFormatted, heureFormatted));

            } catch (Exception e) {
                dateHeureSpectacle.setText("Date non disponible");
                Log.e("DateFormat", "Erreur formatage date", e);
            }

            // Affichage du lieu
            if (spectacle.getIdLieu() != null && spectacle.getIdLieu().getNom() != null) {
                lieuSpectacle.setText(spectacle.getIdLieu().getNom());
            } else {
                lieuSpectacle.setText("Lieu non précisé");
            }
            itemView.setOnClickListener(v -> listener.onSpectacleClick(spectacle));
        }
        private String formatDate(String date) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date parsedDate = inputFormat.parse(date);
                return outputFormat.format(parsedDate);
            } catch (Exception e) {
                return date;
            }
        }

        private String formatHeure(Double heureDecimal) {
            if (heureDecimal == null) return "";
            int heures = (int) Math.floor(heureDecimal);
            int minutes = (int) Math.round((heureDecimal - heures) * 60);
            return String.format(Locale.getDefault(), "%dh%02d", heures, minutes);
        }
    }

}