package com.imera.censo.Contracts.Models;

public class MaterialToma {
    private int id_materialtoma = 0;
    private String descripcion = "";

    public MaterialToma(int id_materialtoma, String descripcion) {
        this.id_materialtoma = id_materialtoma;
        this.descripcion = descripcion;
    }

    public int getid_materialtoma() {
        return id_materialtoma;
    }

    public void setid_materialtoma(int id_materialtoma) {
        this.id_materialtoma = id_materialtoma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
