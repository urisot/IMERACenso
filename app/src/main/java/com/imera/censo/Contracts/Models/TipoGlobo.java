package com.imera.censo.Contracts.Models;

public class TipoGlobo {
    private int id_tipoglobo = 0;
    private String descripcion = "";

    public TipoGlobo(int id_tipoglobo, String descripcion) {
        this.id_tipoglobo = id_tipoglobo;
        this.descripcion = descripcion;
    }

    public int getid_tipoglobo() {
        return id_tipoglobo;
    }

    public void setid_tipoglobo(int id_tipoglobo) {
        this.id_tipoglobo = id_tipoglobo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
