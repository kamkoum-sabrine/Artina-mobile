package com.example.artina;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RepresentationAdapter extends  RecyclerView.Adapter<RepresentationAdapter.RepresentationViewHolder>  {
    private Context context;
    private List<Representation> representations;

    public RepresentationAdapter(Context context, List<Representation> representations) {
        this.context = context;
        this.representations = representations;
    }

    @NonNull
    @Override
    public RepresentationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_representation, parent, false);
        return new RepresentationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepresentationViewHolder holder, int position) {
        Representation representation = representations.get(position);
       // holder.bind(representation);
       System.out.println("Representation "+representation.toString());
        // Mettre à jour les vues avec les informations des représentations
        holder.dateHeure.setText(representation.getDates());
        holder.lieu.setText(representation.getLieu() != null ? representation.getLieu().getNom() : "Inconnu");
        if (representation.getLieu() != null && representation.getLieu().getUrl() != null) {
            holder.lieu.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(representation.getLieu().getUrl()));
                v.getContext().startActivity(intent);
            });
           /* holder.btnVoirLocalisation.setOnClickListener(v -> {
                String lieu = holder.lieu.getText().toString();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(lieu));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                v.getContext().startActivity(mapIntent);
            });*/

            // Ajouter soulignement pour montrer que c'est cliquable
           // holder.lieu.setPaintFlags(holder.lieu.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            holder.lieu.setOnClickListener(null); // Pas cliquable si pas d'URL
        }

        holder.btnDetailsReserver.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailSpectacleActivity.class);

            // Envoyer les infos dont tu as besoin
            intent.putExtra("TITRE", representation.getTitre());
         //   intent.putExtra("DESCRIPTION", representation.getSpectacle().getDescription());
            intent.putExtra("IMAGE_PATH", representation.getImagePath());
            intent.putExtra("LIEU", representation.getLieu() != null ? representation.getLieu().getNom() : "Lieu inconnu");
            intent.putExtra("VILLE", representation.getLieu() != null ? representation.getLieu().getVille() : "Ville inconnue");
            intent.putExtra("ADRESSE", representation.getLieu() != null ? representation.getLieu().getAdresse() : "Adresse inconnue");
            intent.putExtra("HEURE_DEBUT", representation.gethDebut());
            intent.putExtra("DATES", representation.getDates());
            intent.putExtra("ID", representation.getIdSpec()); // Pour charger les billets

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return (representations != null) ? representations.size() : 0;
        //return representations.size();
    }

    class RepresentationViewHolder extends RecyclerView.ViewHolder {
        TextView dateHeure, lieu;
        ImageView image;

        Button btnDetailsReserver;

        // ImageView btnVoirLocalisation;

        public RepresentationViewHolder(@NonNull View itemView) {
            super(itemView);
            dateHeure = itemView.findViewById(R.id.dateHeure);
            lieu = itemView.findViewById(R.id.lieuRepresentation);
            btnDetailsReserver = itemView.findViewById(R.id.btnDetailsReserver);
            // btnVoirLocalisation = itemView.findViewById(R.id.btnVoirLocalisation);
           // image = itemView.findViewById(R.id.imageRepresentation);
        }

        public void bind(Representation representation) {
            // Formater la date et l'heure
            String dateFormatee = formatDate(representation.getDates());
            String heureFormatee = formatHeure(representation.gethDebut());
            dateHeure.setText(dateFormatee + " • " + heureFormatee);
            System.out.println("reeeeppp "+representation.getLieu().getNom());
            lieu.setText(representation.getLieu().getNom());

            // Charger l'image si disponible
            //System.out.println("Imaageee "+representation.getSpectacle().getImagePath());
           /** if (representation.getImagePath() != null) {
                Picasso.get()
                        .load("http://192.168.1.18:8081/api/images/" + representation.getImagePath())
                        .into(image);
            }**/
        }

        private String formatDate(String dateStr) {
            try {
                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = input.parse(dateStr);
                return output.format(date);
            } catch (Exception e) {
                return dateStr;
            }
        }

        private String formatHeure(double heure) {
            int heures = (int) heure;
            int minutes = (int) ((heure - heures) * 60);
            return String.format(Locale.getDefault(), "%dh%02d", heures, minutes);
        }
    }
}