package com.imera.censo.Contracts.Models;

public class Anomalia {
    private int id_anomalia = 0;
    private String descripcion = "";

    public Anomalia(int id_anomalia, String descripcion) {
        this.id_anomalia = id_anomalia;
        this.descripcion = descripcion;
    }

    public int getId_anomalia() {
        return id_anomalia;
    }

    public void setId_anomalia(int id_anomalia) {
        this.id_anomalia = id_anomalia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
