package com.example.proyectoblanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView detalles, inicio;
    Button Deslog, vertarjeta, agregartarjeta, Lector;
    FirebaseUser usuario;
    ImageView verperfil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        Deslog = findViewById(R.id.Deslogear);
        detalles = findViewById(R.id.detalles);
        usuario = auth.getCurrentUser();
        verperfil = findViewById(R.id.imageView6);
        vertarjeta = findViewById(R.id.button2);
        agregartarjeta = findViewById(R.id.button3);
        Lector = findViewById(R.id.button6);
        inicio = findViewById(R.id.Bienvenido);

        verperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), miperfil.class);
                startActivity(intent2);
                finish();
            }
        });
        Lector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LectorTarjeta.class);
                startActivity(intent);
                finish();
            }
        });


        vertarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MisTarjetas.class);
                startActivity(intent);
                finish();
            }
        });

        agregartarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AgregarTarjeta.class);
                startActivity(intent);
                finish();
            }
        });
        if (usuario == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else {

            inicio.setText("Bienvenido");
            detalles.setText(usuario.getEmail());
        }

        Deslog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}