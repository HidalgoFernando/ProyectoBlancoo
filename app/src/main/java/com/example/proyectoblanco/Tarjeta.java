package com.example.proyectoblanco;

public class Tarjeta {
    private String numeroTarjeta;
    private String fecha;

    public Tarjeta() {

    }

    public Tarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public Tarjeta(String numeroTarjeta, String fecha){
        this.numeroTarjeta = numeroTarjeta;
        this.fecha = fecha;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getFecha() {
        return fecha;
    }
}

