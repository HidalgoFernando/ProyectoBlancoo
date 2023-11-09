package com.example.proyectoblanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AgregarTarjeta extends AppCompatActivity {

    TextView texto;
    Button agregar, cancelar;
    EditText numero;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private int contadorTarjetas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarjeta);

        texto = findViewById(R.id.textView2);
        agregar = findViewById(R.id.button4);
        cancelar = findViewById(R.id.button5);
        numero = findViewById(R.id.editTextText3);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroTarjeta = numero.getText().toString();
                if (!numeroTarjeta.isEmpty()) {
                    agregarTarjetaFirestore(numeroTarjeta);
                } else {
                    Toast.makeText(AgregarTarjeta.this, "Ingresa un número de tarjeta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void agregarTarjetaFirestore(String numeroTarjeta) {
        String correoUsuario = mAuth.getCurrentUser().getEmail();
        DocumentReference usuarioRef = db.collection("usuarios").document(correoUsuario);

        String idDocumento = "Tarjeta" + (++contadorTarjetas);

        Tarjeta nuevaTarjeta = new Tarjeta(numeroTarjeta, idDocumento);

        // Agregar el documento en la subcolección "Mis_tarjetas"
        usuarioRef.collection("Mis_tarjetas")
                .document(idDocumento)
                .set(nuevaTarjeta)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AgregarTarjeta.this, "Tarjeta agregada correctamente", Toast.LENGTH_SHORT).show();
                            numero.setText("");
                        } else {
                            Toast.makeText(AgregarTarjeta.this, "Error al agregar la tarjeta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
