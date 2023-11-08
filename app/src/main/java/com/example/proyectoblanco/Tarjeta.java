package com.example.proyectoblanco;

public class Tarjeta {
    private String numeroTarjeta;

    public Tarjeta() {
        // Constructor vacío requerido para Firestore
    }

    public Tarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
}
