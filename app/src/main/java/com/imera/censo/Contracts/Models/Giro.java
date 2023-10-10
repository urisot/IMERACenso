package com.imera.censo.Contracts.Models;

public class Giro {
    private int id_giro = 0;
    private String descripcion = "";

    public Giro(int id_giro, String descripcion) {
        this.id_giro = id_giro;
        this.descripcion = descripcion;
    }

    public int getid_giro() {
        return id_giro;
    }

    public void setid_giro(int id_giro) {
        this.id_giro = id_giro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
