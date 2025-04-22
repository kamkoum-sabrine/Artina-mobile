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

import com.squareup.picasso.BuildConfig;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BilletAdapter extends RecyclerView.Adapter<BilletAdapter.ViewHolder> {

    private Context context;
    private List<Billet> billetsList;
    private BilletAdapter.OnBilletClickListener listener;

    public interface OnBilletClickListener {
        void onBilletClick(Billet billet);
    }

    public BilletAdapter(Context context, List<Billet> billestList, BilletAdapter.OnBilletClickListener listener) {
        this.context = context;
        this.billetsList = billestList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BilletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_billet, parent, false);
        return new BilletAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BilletAdapter.ViewHolder holder, int position) {
        Billet billet = billetsList.get(position);
        holder.bind(billet, listener);
    }

    @Override
    public int getItemCount() {
        return billetsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView categorie;

        TextView prix;

        TextView nbreRestante;

        public ViewHolder(View itemView) {
            super(itemView);
            categorie = itemView.findViewById(R.id.categorieBillet);
            prix = itemView.findViewById(R.id.prixBillet);
            nbreRestante = itemView.findViewById(R.id.placesDisponibles);
        }

        public void bind(Billet billet, BilletAdapter.OnBilletClickListener listener) {
            categorie.setText(billet.getCategorie());
            prix.setText(billet.getPrix());
            nbreRestante.setText(billet.getNbrerestant());

            itemView.setOnClickListener(v -> listener.onBilletClick(billet));
        }
    }
}
