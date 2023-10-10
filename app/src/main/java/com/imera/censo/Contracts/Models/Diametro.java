package com.imera.censo.Contracts.Models;

public class Diametro {
    private int id_diametro = 0;
    private String descripcion = "";

    public Diametro(int id_diametro, String descripcion) {
        this.id_diametro = id_diametro;
        this.descripcion = descripcion;
    }

    public int getId_diametro() {
        return id_diametro;
    }

    public void setId_diametro(int id_diametro) {
        this.id_diametro = id_diametro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
