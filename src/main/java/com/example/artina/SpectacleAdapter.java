package com.example.artina;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SpectacleAdapter extends RecyclerView.Adapter<SpectacleAdapter.SpectacleViewHolder> {
    private Context context;
    private List<GroupeSpectacle> spectaclesGroupes;

    ImageView imageSpectacle; // Nouvelle ligne
    public SpectacleAdapter(Context context, List<GroupeSpectacle> spectaclesGroupes) {
        this.context = context;
        this.spectaclesGroupes = spectaclesGroupes;
    }


    public void updateData(List<GroupeSpectacle> nouvellesDonnees) {
        this.spectaclesGroupes = nouvellesDonnees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SpectacleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_spectacle_groupe, parent, false);
        return new SpectacleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpectacleViewHolder holder, int position) {
        GroupeSpectacle groupe = spectaclesGroupes.get(position);
        holder.bind(groupe);

        // Ajouter le clic sur l'élément
        holder.itemView.setOnClickListener(v -> {
            // Créer un Intent pour démarrer l'activité de représentation
            Intent intent = new Intent(context, RepresentationsActivity.class);
            System.out.println("Spectacle "+groupe.toString());
            intent.putExtra("spectacle", groupe); // Passer l'objet groupe à l'activité suivante
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (spectaclesGroupes != null) ? spectaclesGroupes.size() : 0;
    }


    class SpectacleViewHolder extends RecyclerView.ViewHolder {
        TextView titreSpectacle;
       // RecyclerView recyclerRepresentations;

        public SpectacleViewHolder(@NonNull View itemView) {
            super(itemView);
            titreSpectacle = itemView.findViewById(R.id.titreSpectacle);
            imageSpectacle = itemView.findViewById(R.id.imageSpectacle); // Nouvelle ligne
          //  recyclerRepresentations = itemView.findViewById(R.id.recyclerRepresentations);
        }

        public void bind(GroupeSpectacle groupe) {
            titreSpectacle.setText(groupe.getTitre());

            // Chargement de l'image principale
           // String imageUrl = groupe.getImagePrincipale();
            System.out.println("aaaaaaaaaa "+groupe.getImagePrincipale());
            if (groupe.getImagePrincipale() != null && !groupe.getImagePrincipale().isEmpty()) {
                String imageUrl = "http://192.168.1.187:8081/api/images/" + groupe.getImagePrincipale();

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
            // Adapter pour les représentations
            RepresentationAdapter representationAdapter = new RepresentationAdapter(context, groupe.getRepresentations());
         //   recyclerRepresentations.setLayoutManager(new LinearLayoutManager(context));
          //  recyclerRepresentations.setAdapter(representationAdapter);
        }
    }
}
