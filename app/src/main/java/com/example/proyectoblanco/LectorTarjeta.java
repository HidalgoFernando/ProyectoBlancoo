package com.example.proyectoblanco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LectorTarjeta extends AppCompatActivity {

    EditText numero;
    Button leer, volver;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_tarjeta);

        numero = findViewById(R.id.editTextText4);
        leer = findViewById(R.id.button7);
        db = FirebaseFirestore.getInstance();
        volver = findViewById(R.id.button8);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        leer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroTarjeta = numero.getText().toString();
                buscarUsuarioPorTarjeta(numeroTarjeta);
            }
        });
    }

    private void buscarUsuarioPorTarjeta(final String numeroTarjeta) {

        db.collection("Tarjetas")
                .document(numeroTarjeta)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String correoUsuario = document.getString("correoUsuario");
                                if (correoUsuario != null) {
                                    buscarUsuarioPorCorreo(correoUsuario);
                                } else {
                                    mostrarMensaje("No se encontró el correo asociado a la tarjeta.");
                                }
                            } else {
                                mostrarMensaje("No se encontró el número de tarjeta.");
                            }
                        } else {

                            mostrarMensaje("Error al buscar la tarjeta: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private void buscarUsuarioPorCorreo(final String correoUsuario) {

        db.collection("usuarios")
                .document(correoUsuario)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot usuarioDocument = task.getResult();
                            if (usuarioDocument.exists()) {

                                mostrarDatosUsuario(usuarioDocument);
                            } else {
                                mostrarMensaje("No se encontró el usuario con el correo asociado.");
                            }
                        } else {
                            mostrarMensaje("Error al buscar el usuario por correo: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private void mostrarDatosUsuario(DocumentSnapshot usuarioDocument) {
        String nombre = usuarioDocument.getString("nombre");
        String apellido = usuarioDocument.getString("apellido");
        int edad = usuarioDocument.getLong("edad").intValue();
        String telefono = usuarioDocument.getString("telefono");
        String correo = usuarioDocument.getId();

        TextView nombreTextView = findViewById(R.id.nombreTextView);
        nombreTextView.setText("Nombre: " + nombre);

        TextView apellidoTextView = findViewById(R.id.apellidoTextView);
        apellidoTextView.setText("Apellido: " + apellido);

        TextView edadTextView = findViewById(R.id.edadTextView);
        edadTextView.setText("Edad: " + edad);

        TextView telefonoTextView = findViewById(R.id.telefonoTextView);
        telefonoTextView.setText("Telefono: " + telefono);

        TextView personaTextView = findViewById(R.id.personaTextView);
        personaTextView.setText("Usuario: " + correo);
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(LectorTarjeta.this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
