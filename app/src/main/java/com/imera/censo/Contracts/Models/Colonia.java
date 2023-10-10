package com.imera.censo.Contracts.Models;

public class Colonia {
    private int id_colonia = 0;
    private String descripcion = "";

    public Colonia(int id_colonia, String descripcion) {
        this.id_colonia = id_colonia;
        this.descripcion = descripcion;
    }

    public int getid_colonia() {
        return id_colonia;
    }

    public void setid_colonia(int id_colonia) {
        this.id_colonia = id_colonia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
