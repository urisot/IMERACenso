package com.imera.censo.Contracts.Models;

public class Modelo {
    private int id_modelo = 0;
    private String descripcion = "";

    public Modelo(int id_modelo, String descripcion) {
        this.id_modelo = id_modelo;
        this.descripcion = descripcion;
    }

    public int getid_modelo() {
        return id_modelo;
    }

    public void setid_modelo(int id_modelo) {
        this.id_modelo = id_modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
