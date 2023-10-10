package com.imera.censo.Contracts.Models;

public class Calle {
    private int id_calle = 0;
    private int id_colonia = 0;
    private String descripcion = "";

    public Calle(int id_calle, int id_colonia, String descripcion) {
        this.id_calle = id_calle;
        this.id_colonia = id_colonia;
        this.descripcion = descripcion;
    }



    public int getId_calle() {
        return id_calle;
    }

    public void setId_calle(int id_calle) {
        this.id_calle = id_calle;
    }

    public int getId_colonia() {
        return id_colonia;
    }

    public void setId_colonia(int id_colonia) {
        this.id_colonia = id_colonia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
