package com.imera.censo.Contracts.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CondicionPredio {
    @SerializedName("id_condicion")
    @Expose
    private int id_condicionpredio = 0;
    private String descripcion = "";

    public CondicionPredio(int id_condicionpredio, String descripcion) {
        this.id_condicionpredio = id_condicionpredio;
        this.descripcion = descripcion;
    }

    public int getId_condicionpredio() {
        return id_condicionpredio;
    }

    public void setId_condicionpredio(int id_condicion) {
        this.id_condicionpredio = id_condicion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
