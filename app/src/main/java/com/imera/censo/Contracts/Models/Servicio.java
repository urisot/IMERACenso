package com.imera.censo.Contracts.Models;

public class Servicio {
    private int id_Servicio = 0;
    private String descripcion = "";

    public Servicio(int id_Servicio, String descripcion) {
        this.id_Servicio = id_Servicio;
        this.descripcion = descripcion;
    }

    public int getId_Servicio() {
        return id_Servicio;
    }

    public void setId_Servicio(int id_Servicio) {
        this.id_Servicio = id_Servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
