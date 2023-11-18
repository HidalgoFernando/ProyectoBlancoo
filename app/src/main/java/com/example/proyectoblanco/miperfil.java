package com.example.proyectoblanco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class miperfil extends AppCompatActivity {

    Button cambiar, volver;
    FirebaseAuth auth;
    FirebaseUser usuario;

    ImageView IMG1,IMG2,IMG3,IMG4,IMG5,IMG6;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miperfil);

        IMG1 = findViewById(R.id.imageView11);
        IMG2 = findViewById(R.id.imageView14);
        IMG3 = findViewById(R.id.imageView12);
        IMG4 = findViewById(R.id.imageView13);
        IMG5 = findViewById(R.id.imagenFecha);
        IMG6 = findViewById(R.id.imageView7);

        cambiar = findViewById(R.id.button9);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        usuario = auth.getCurrentUser();
        volver = findViewById(R.id.button10);

        String correoUsuario = usuario.getEmail();
        buscarUsuarioPorCorreo(correoUsuario);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
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

        TextView nombreTextView = findViewById(R.id.nombreTextView2);
        nombreTextView.setText("Nombre: " + nombre);

        TextView apellidoTextView = findViewById(R.id.apellidoTextView2);
        apellidoTextView.setText("Apellido: " + apellido);

        TextView edadTextView = findViewById(R.id.edadTextView2);
        edadTextView.setText("Edad: " + edad);

        TextView telefonoTextView = findViewById(R.id.telefonoTextView2);
        telefonoTextView.setText("Telefono: " + telefono);

        TextView personaTextView = findViewById(R.id.personaTextView2);
        personaTextView.setText("Usuario: " + correo);
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(miperfil.this, mensaje, Toast.LENGTH_SHORT).show();
    }

}