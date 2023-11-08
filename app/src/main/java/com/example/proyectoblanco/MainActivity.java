package com.example.proyectoblanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView detalles;
    Button Deslog, vertarjeta, agregartarjeta;
    FirebaseUser usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        Deslog = findViewById(R.id.Deslogear);
        detalles = findViewById(R.id.detalles);
        usuario = auth.getCurrentUser();
        vertarjeta = findViewById(R.id.button2);
        agregartarjeta = findViewById(R.id.button3);


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