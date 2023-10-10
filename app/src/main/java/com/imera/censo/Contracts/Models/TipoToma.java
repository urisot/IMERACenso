package com.imera.censo.Contracts.Models;

public class TipoToma {
    private int id_TipoToma = 0;
    private String descripcion = "";

    public TipoToma(int id_TipoToma, String descripcion) {
        this.id_TipoToma = id_TipoToma;
        this.descripcion = descripcion;
    }

    public int getId_TipoToma() {
        return id_TipoToma;
    }

    public void setId_TipoToma(int id_TipoToma) {
        this.id_TipoToma = id_TipoToma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
