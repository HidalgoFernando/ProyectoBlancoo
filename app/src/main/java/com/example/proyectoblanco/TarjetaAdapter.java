package com.example.proyectoblanco;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TarjetaAdapter extends RecyclerView.Adapter<TarjetaAdapter.TarjetaViewHolder> {

    private List<Tarjeta> tarjetasList;
    private MisTarjetas.OnEliminarTarjetaListener eliminarTarjetaListener;

    // Constructor que recibe la lista de tarjetas y el listener
    public TarjetaAdapter(List<Tarjeta> tarjetasList, MisTarjetas.OnEliminarTarjetaListener eliminarTarjetaListener) {
        this.tarjetasList = tarjetasList;
        this.eliminarTarjetaListener = eliminarTarjetaListener;
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
        private Button eliminarButton;

        public TarjetaViewHolder(View itemView) {
            super(itemView);
            numeroTarjetaTextView = itemView.findViewById(R.id.numeroTarjetaTextView);
            idDocumentoTextView = itemView.findViewById(R.id.nameTextView);
            eliminarButton = itemView.findViewById(R.id.eliminarButton);

            eliminarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idDocumento = idDocumentoTextView.getText().toString();

                    eliminarTarjetaListener.onTarjetaEliminada(idDocumento);
                }
            });
        }
        public void bindTarjeta(Tarjeta tarjeta) {
            numeroTarjetaTextView.setText(tarjeta.getNumeroTarjeta());

            idDocumentoTextView.setText("" + tarjeta.getFecha());
        }
    }
}
