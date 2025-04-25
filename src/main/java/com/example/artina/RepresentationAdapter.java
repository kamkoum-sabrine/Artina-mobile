package com.example.artina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    }

    @Override
    public int getItemCount() {
        return (representations != null) ? representations.size() : 0;
        //return representations.size();
    }

    class RepresentationViewHolder extends RecyclerView.ViewHolder {
        TextView dateHeure, lieu;
        ImageView image;

        public RepresentationViewHolder(@NonNull View itemView) {
            super(itemView);
            dateHeure = itemView.findViewById(R.id.dateHeure);
            lieu = itemView.findViewById(R.id.lieu);
           // image = itemView.findViewById(R.id.imageRepresentation);
        }

        public void bind(Representation representation) {
            // Formater la date et l'heure
            String dateFormatee = formatDate(representation.getDates());
            String heureFormatee = formatHeure(representation.gethDebut());
            dateHeure.setText(dateFormatee + " • " + heureFormatee);

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