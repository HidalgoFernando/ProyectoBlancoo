package com.example.proyectoblanco;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TarjetaAdapter extends RecyclerView.Adapter<TarjetaAdapter.TarjetaViewHolder> {

    private List<Tarjeta> tarjetasList;

    public TarjetaAdapter(List<Tarjeta> tarjetasList) {
        this.tarjetasList = tarjetasList;
    }

    @NonNull
    @Override
    public TarjetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarjeta, parent, false);
        return new TarjetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TarjetaViewHolder holder, int position) {
        Tarjeta tarjeta = tarjetasList.get(position);
        holder.bindTarjeta(tarjeta);
    }

    @Override
    public int getItemCount() {
        return tarjetasList.size();
    }

    public class TarjetaViewHolder extends RecyclerView.ViewHolder {

        private TextView numeroTarjetaTextView;
        private TextView idDocumentoTextView;

        public TarjetaViewHolder(View itemView) {
            super(itemView);
            numeroTarjetaTextView = itemView.findViewById(R.id.numeroTarjetaTextView);
            idDocumentoTextView = itemView.findViewById(R.id.nameTextView);
        }

        public void bindTarjeta(Tarjeta tarjeta) {
            numeroTarjetaTextView.setText(tarjeta.getNumeroTarjeta());

            idDocumentoTextView.setText("" + tarjeta.getIdDocumento());
        }
    }
}
