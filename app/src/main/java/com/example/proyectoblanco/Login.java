package com.example.proyectoblanco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText editText3, editText4;
    TextView Textview3, Textview4;
    Button boton2;
    FirebaseAuth mAuth2;
    ProgressBar progressBar2;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth2.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boton2 = findViewById(R.id.botonlogin);
        Textview3 = findViewById(R.id.textViewlogin);
        editText3 = findViewById(R.id.editTextCorreo);
        editText4 = findViewById(R.id.editTextContrasena);
        progressBar2 = findViewById(R.id.progressBar);
        Textview4 = findViewById(R.id.VolverLogin);
        mAuth2 = FirebaseAuth.getInstance();

        Textview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar2.setVisibility(View.VISIBLE);
                String correo2, contrasena2;
                correo2 = String.valueOf(editText3.getText());
                contrasena2 = String.valueOf(editText4.getText());

                if (TextUtils.isEmpty(correo2)){
                    Toast.makeText(Login.this, "Ingresar correo",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(contrasena2)){
                    Toast.makeText(Login.this, "Ingresar contrasena",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth2.signInWithEmailAndPassword(correo2, contrasena2)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar2.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(), "Usuario logeado", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}