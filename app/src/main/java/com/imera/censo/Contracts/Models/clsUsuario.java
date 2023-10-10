package com.imera.censo.Contracts.Models;

/**
 * Created by Uriel Soto on 23/08/2017.
 */

public class clsUsuario {
    private String id_usuario = "";
    private String nombre = "";
    private String usuario = "";
    private String key = "";

    public clsUsuario(String id_usuario, String nombre, String usuario, String key) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String descripcion) {
        this.nombre = descripcion;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
