package com.example.proyectoblanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class MisDatos extends AppCompatActivity {
    FirebaseAuth auth;
    TextView texto;
    EditText nombre, apellido, edad, telefono;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);

        texto = findViewById(R.id.textView);
        auth = FirebaseAuth.getInstance();
        nombre = findViewById(R.id.editTextText);
        apellido = findViewById(R.id.editTextText2);
        edad = findViewById(R.id.editTextNumber);
        telefono = findViewById(R.id.editTextPhone);
        boton = findViewById(R.id.button);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreText = nombre.getText().toString();
                String apellidoText = apellido.getText().toString();
                String edadText = edad.getText().toString();
                String telefonoText = telefono.getText().toString();

                if (nombreText.isEmpty() || apellidoText.isEmpty() || edadText.isEmpty() || telefonoText.isEmpty()) {
                    Toast.makeText(MisDatos.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int edadValue = 0;

                try {
                    edadValue = Integer.parseInt(edadText);
                } catch (NumberFormatException e) {
                    Toast.makeText(MisDatos.this, "Ingrese una edad válida", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario usuario = new Usuario(nombreText, apellidoText, edadValue, telefonoText);

                guardarDatosEnFirestore(usuario);

                Toast.makeText(MisDatos.this, "Datos Guardados.",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void guardarDatosEnFirestore(Usuario usuario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String correoUsuario = auth.getCurrentUser().getEmail();

        db.collection("usuarios")
                .document(correoUsuario)
                .set(usuario, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MisDatos.this, "Datos guardados en Firestore", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MisDatos.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                });
    }
}