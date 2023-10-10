package com.imera.censo.Contracts.Models;

public class Material {
    private int id_material = 0;
    private String descripcion = "";

    public Material(int id_material, String descripcion) {
        this.id_material = id_material;
        this.descripcion = descripcion;
    }

    public int getid_material() {
        return id_material;
    }

    public void setid_material(int id_material) {
        this.id_material = id_material;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
