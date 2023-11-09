package com.example.proyectoblanco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MisTarjetas extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private TarjetaAdapter adapter;
    private List<Tarjeta> tarjetasList;
    Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_tarjetas);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        tarjetasList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        volver = findViewById(R.id.volverButton);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agrega aquí la lógica para volver al MainActivity
                Intent intent = new Intent(MisTarjetas.this, MainActivity.class);
                startActivity(intent);
            }
        });

        MisTarjetas.OnEliminarTarjetaListener eliminarTarjetaListener = new MisTarjetas.OnEliminarTarjetaListener() {
            @Override
            public void onTarjetaEliminada(String idDocumento) {
                // Crear una referencia al documento que se desea eliminar
                String correoUsuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                DocumentReference tarjetaRef = FirebaseFirestore.getInstance()
                        .collection("usuarios")
                        .document(correoUsuario)
                        .collection("Mis_tarjetas")
                        .document(idDocumento);

                // Eliminar el documento de Firestore
                tarjetaRef.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Tarjeta eliminada con éxito
                                Toast.makeText(MisTarjetas.this, "Tarjeta eliminada", Toast.LENGTH_SHORT).show();

                                // Elimina la tarjeta de la lista y notifica al adaptador
                                eliminarTarjetaDeLista(idDocumento);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Ocurrió un error al eliminar la tarjeta
                                Toast.makeText(MisTarjetas.this, "Error al eliminar la tarjeta", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        };

        // Crea el adaptador y pásale la lista de tarjetas y el listener
        adapter = new TarjetaAdapter(tarjetasList, eliminarTarjetaListener);
        recyclerView.setAdapter(adapter);

        obtenerTarjetas();

    }


    public interface OnEliminarTarjetaListener {
        void onTarjetaEliminada(String idDocumento);
    }

    private void eliminarTarjetaDeLista(String idDocumento) {
        // Encuentra y elimina la tarjeta correspondiente en la lista
        for (int i = 0; i < tarjetasList.size(); i++) {
            Tarjeta tarjeta = tarjetasList.get(i);
            if (tarjeta.getIdDocumento().equals(idDocumento)) {
                tarjetasList.remove(i);
                break;
            }
        }
    }


    private void obtenerTarjetas() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String correoUsuario = currentUser.getEmail();
            DocumentReference usuarioRef = db.collection("usuarios").document(correoUsuario);
            CollectionReference tarjetasRef = usuarioRef.collection("Mis_tarjetas");

            tarjetasRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        tarjetasList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Tarjeta tarjeta = document.toObject(Tarjeta.class);
                            tarjetasList.add(tarjeta);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MisTarjetas.this, "Error al obtener las tarjetas", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

