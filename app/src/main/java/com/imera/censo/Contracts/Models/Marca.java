package com.imera.censo.Contracts.Models;

public class Marca {
    private int id_marca = 0;
    private String descripcion = "";

    public Marca(int id_marca, String descripcion) {
        this.id_marca = id_marca;
        this.descripcion = descripcion;
    }

    public int getid_marca() {
        return id_marca;
    }

    public void setid_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
