package com.imera.censo.Contracts.Models;

/**
 * Created by Daniel Esparza on 16/06/2015.
 */
public class UsuarioPadron {

    private String clave = "";
    private String direccion = "";
    private String id_calle_ppal = "";
    private String id_colonia = "";
    private String id_cuenta = "";
    private String id_padron ="";
    private String medidor = "";
    private String nombre ="";

    public String toString() {
        return "claveloc: '" + this.clave + "', direccion: '" + this.direccion + "', id_ppal: '"
                + this.id_calle_ppal + "', id_colonia: '" + this.id_colonia + "', id_cuenta: '" + this.id_cuenta + "'" +
                ", id_padron: '" + this.id_padron + "', medidor: '" + this.medidor + "', nombre: '" + this.nombre + "' ";
    }

    public UsuarioPadron(String clave, String direccion, String id_calle_ppal, String id_colonia, String id_cuenta, String id_padron, String medidor, String nombre) {

        this.clave = clave;
        this.direccion = direccion;
        this.id_calle_ppal = id_calle_ppal;
        this.id_colonia = id_colonia;
        this.id_cuenta = id_cuenta;
        this.id_padron = id_padron;
        this.medidor = medidor;
        this.nombre = nombre;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMedidor() {
        return medidor;
    }

    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }

    public String getId_padron() {
        return id_padron;
    }

    public void setId_padron(String id_padron) {
        this.id_padron = id_padron;
    }

    public String getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(String id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getId_colonia() {
        return id_colonia;
    }

    public void setId_colonia(String id_colonia) {
        this.id_colonia = id_colonia;
    }

    public String getId_calle_ppal() {
        return id_calle_ppal;
    }

    public void setId_calle_ppal(String id_calle_ppal) {
        this.id_calle_ppal = id_calle_ppal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
