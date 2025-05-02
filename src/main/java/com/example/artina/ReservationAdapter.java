package com.example.artina;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private final List<Reservation> reservations;

    public ReservationAdapter(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);
        holder.bind(reservation);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSpectacleName;
        private final TextView tvDate;
        private final TextView tvQuantity;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSpectacleName = itemView.findViewById(R.id.tvSpectacleName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }

        public void bind(Reservation reservation) {
            tvSpectacleName.setText(reservation.getSpectacle().getTitre());
            //tvDate.setText(reservation.getDateReservation());
            tvQuantity.setText(reservation.getQuantiteBillet() + " billet(s)");
        }
    }
}
