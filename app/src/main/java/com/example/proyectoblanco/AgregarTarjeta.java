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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarTarjeta extends AppCompatActivity {

    TextView texto;
    Button agregar, cancelar;
    EditText numero;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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

    private void agregarTarjetaFirestore(final String numeroTarjeta) {
        String correoUsuario = mAuth.getCurrentUser().getEmail();
        DocumentReference usuarioRef = db.collection("usuarios").document(correoUsuario);

        // Realiza una consulta en la colección "Tarjetas" para buscar el número de tarjeta
        db.collection("Tarjetas")
                .document(numeroTarjeta)  // Usa el número de tarjeta como ID del documento
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // El número de tarjeta ya existe en la base de datos
                                Toast.makeText(AgregarTarjeta.this, "El número de tarjeta ya existe", Toast.LENGTH_SHORT).show();
                            } else {
                                // El número de tarjeta no existe, puedes agregarlo
                                Date fechaActual = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String fecha = dateFormat.format(fechaActual);

                                Tarjeta nuevaTarjeta = new Tarjeta(numeroTarjeta, fecha, correoUsuario);

                                // Agregar el documento en la colección "Tarjetas" con el número de tarjeta como ID
                                db.collection("Tarjetas")
                                        .document(numeroTarjeta)
                                        .set(nuevaTarjeta)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Agregar la tarjeta a la subcolección "Mis_Tarjetas" del usuario actual
                                                usuarioRef.collection("Mis_Tarjetas")
                                                        .document(numeroTarjeta)
                                                        .set(nuevaTarjeta)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(AgregarTarjeta.this, "Tarjeta agregada correctamente", Toast.LENGTH_SHORT).show();
                                                                numero.setText("");
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(AgregarTarjeta.this, "Error al agregar la tarjeta a Mis_Tarjetas", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AgregarTarjeta.this, "Error al agregar la tarjeta a Tarjetas", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            // Error al realizar la consulta
                            Toast.makeText(AgregarTarjeta.this, "Error al verificar el número de tarjeta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

