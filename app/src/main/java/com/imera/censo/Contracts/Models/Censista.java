package com.imera.censo.Contracts.Models;

public class Censista {
    private int id_Personal = 0;
    private String nombre = "";

    public Censista(int id_Personal, String nombre) {
        this.id_Personal = id_Personal;
        this.nombre = nombre;
    }

    public int getId_Personal() {
        return id_Personal;
    }

    public void setId_Personal(int id_Personal) {
        this.id_Personal = id_Personal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
