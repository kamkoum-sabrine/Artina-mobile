package com.example.artina;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artina.R;
import com.example.artina.Spectacle;

import java.util.List;

public class SpectacleAdapter extends RecyclerView.Adapter<SpectacleAdapter.SpectacleViewHolder> {

    private List<Spectacle> spectacleList;

    public SpectacleAdapter(List<Spectacle> spectacleList) {
        this.spectacleList = spectacleList;
    }

    @NonNull
    @Override
    public SpectacleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spectacle, parent, false);
        return new SpectacleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpectacleViewHolder holder, int position) {
        Spectacle spectacle = spectacleList.get(position);
        holder.titre.setText(spectacle.getTitre());
        holder.description.setText(spectacle.getDescription());
        holder.image.setImageResource(spectacle.getImageResource());
    }

    @Override
    public int getItemCount() {
        return spectacleList.size();
    }

    public static class SpectacleViewHolder extends RecyclerView.ViewHolder {
        TextView titre, description;
        ImageView image;

        public SpectacleViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titreSpectacle);
            description = itemView.findViewById(R.id.descriptionSpectacle);
            image = itemView.findViewById(R.id.imageSpectacle);
        }
    }
}
