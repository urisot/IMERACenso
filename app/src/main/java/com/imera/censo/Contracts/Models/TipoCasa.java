package com.imera.censo.Contracts.Models;

public class TipoCasa {
    private int id_tipocasa = 0;
    private String descripcion = "";

    public TipoCasa(int id_tipocasa, String descripcion) {
        this.id_tipocasa = id_tipocasa;
        this.descripcion = descripcion;
    }

    public int getId_tipocasa() {
        return id_tipocasa;
    }

    public void setId_tipocasa(int id_tipocasa) {
        this.id_tipocasa = id_tipocasa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
