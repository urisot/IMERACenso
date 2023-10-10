package com.imera.censo.Contracts.Models;

/**
 * Created by Daniel Esparza on 15/01/2016.
 */

/*Anteriormente Catalogo_Descarga4 mopdificado el 05/02/2016 despues del respaldo del mismo dia*/
public class Catalogo_Descarga {

    String id_calle = "";
    String id_colonia = "";
    String nombre = "";
    String descripcion = "";

    public Catalogo_Descarga(String id_calle, String id_colonia, String nombre, String descripcion) {
        this.id_calle = id_calle;
        this.id_colonia = id_colonia;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getId_colonia() {
        return id_colonia;
    }

    public String getId_calle() {
        return id_calle;
    }


    public String toString() {
        return "Id_Calle: "+ this.getId_calle() + "Id_Colonia: "+ this.getId_colonia() +" nombre: "+getNombre() + " descripcion: "+getDescripcion();
    }
}
