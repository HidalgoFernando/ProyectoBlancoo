package com.example.proyectoblanco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_tarjetas);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        tarjetasList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TarjetaAdapter(tarjetasList);
        recyclerView.setAdapter(adapter);



        obtenerTarjetas();
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
                        tarjetasList.clear(); // Borra las tarjetas existentes antes de agregar nuevas
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Tarjeta tarjeta = document.toObject(Tarjeta.class); // Supongamos que tienes una clase Tarjeta para representar los datos
                            tarjetasList.add(tarjeta);
                        }
                        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                    } else {
                        Toast.makeText(MisTarjetas.this, "Error al obtener las tarjetas", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
