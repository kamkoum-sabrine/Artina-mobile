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

public class SpectacleAdapter extends RecyclerView.Adapter<SpectacleAdapter.ViewHolder> {

    private List<Spectacle> spectacleList;
    private OnSpectacleClickListener listener;

    public interface OnSpectacleClickListener {
        void onSpectacleClick(Spectacle spectacle);
    }

    public SpectacleAdapter(List<Spectacle> spectacleList, OnSpectacleClickListener listener) {
        this.spectacleList = spectacleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
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

        public ViewHolder(View itemView) {
            super(itemView);
            imageSpectacle = itemView.findViewById(R.id.imageSpectacle);
            titreSpectacle = itemView.findViewById(R.id.titreSpectacle);
            descriptionSpectacle = itemView.findViewById(R.id.descriptionSpectacle);
        }

        public void bind(Spectacle spectacle, OnSpectacleClickListener listener) {
            imageSpectacle.setImageResource(spectacle.getImageResource());
            titreSpectacle.setText(spectacle.getTitre());
            descriptionSpectacle.setText(spectacle.getDescription());

            itemView.setOnClickListener(v -> listener.onSpectacleClick(spectacle));
        }
    }
}
