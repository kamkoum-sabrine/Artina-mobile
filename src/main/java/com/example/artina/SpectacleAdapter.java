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
import java.util.List;

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
        TextView dateSpectacle; // Nouveau champ pour la date

        public ViewHolder(View itemView) {
            super(itemView);
            imageSpectacle = itemView.findViewById(R.id.imageSpectacle);
            titreSpectacle = itemView.findViewById(R.id.titreSpectacle);
            descriptionSpectacle = itemView.findViewById(R.id.descriptionSpectacle);
            dateSpectacle = itemView.findViewById(R.id.dateSpectacle); // Initialisation du champ date
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

            titreSpectacle.setText(spectacle.getTitre());
          //  descriptionSpectacle.setText(spectacle.getDescription());
            dateSpectacle.setText(spectacle.getDate()); // Affichage de la date

            itemView.setOnClickListener(v -> listener.onSpectacleClick(spectacle));
        }
    }
}