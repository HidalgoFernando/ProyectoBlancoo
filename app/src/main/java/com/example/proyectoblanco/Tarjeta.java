package com.example.proyectoblanco;

public class Tarjeta {
    private String numeroTarjeta;
    private String idDocumento;

    public Tarjeta() {

    }

    public Tarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public Tarjeta(String numeroTarjeta, String idDocumento) {
        this.numeroTarjeta = numeroTarjeta;
        this.idDocumento = idDocumento;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getIdDocumento() {
        return idDocumento;
    }
}
