package com.imera.censo.Contracts.Models;

public class TipoUsuario {
    private int id_tipousuario = 0;
    private String descripcion = "";

    public TipoUsuario(int id_tipousuario, String descripcion) {
        this.id_tipousuario = id_tipousuario;
        this.descripcion = descripcion;
    }

    public int getid_tipousuario() {
        return id_tipousuario;
    }

    public void setid_tipousuario(int id_tipousuario) {
        this.id_tipousuario = id_tipousuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
