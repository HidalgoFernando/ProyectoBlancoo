package com.example.proyectoblanco;

public class Tarjeta {
    private String numeroTarjeta;
    private String fecha;
    private String correoUsuario;

    public Tarjeta() {

    }

    public Tarjeta(String numeroTarjeta, String fecha, String correoUsuario) {
        this.numeroTarjeta = numeroTarjeta;
        this.fecha = fecha;
        this.correoUsuario = correoUsuario;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }
}

