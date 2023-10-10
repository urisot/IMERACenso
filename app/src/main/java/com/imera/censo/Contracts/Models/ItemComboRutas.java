package com.imera.censo.Contracts.Models;

public class ItemComboRutas {
    private int id_ruta;
    private String descripcion;
    private int cantidad;

    public ItemComboRutas(int id_ruta, String descripcion, int cantidad) {
        this.id_ruta = id_ruta;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    /*    private boolean MostrarCargando = false;
    private boolean MostrarAdvertencia = false;
    private boolean MostrarError = false;
    private boolean MostrarDescargar = true;*/

/*    public clsItemOrdenTrabajo(int id_trabajo, String trabajo, int cantidad) {
        this.id_trabajo = id_trabajo;
        this.trabajo = trabajo;
        this.cantidad = cantidad;
    }

    public clsItemOrdenTrabajo(int id_trabajo, String trabajo, int cantidad, boolean mostrarDescargar) {
        this.id_trabajo = id_trabajo;
        this.trabajo = trabajo;
        this.cantidad = cantidad;
        MostrarDescargar = mostrarDescargar;
    }

    public boolean isMostrarAdvertencia() {
        return MostrarAdvertencia;
    }

    public void setMostrarAdvertencia(boolean mostrarAdvertencia) {
        MostrarAdvertencia = mostrarAdvertencia;
    }*/

    public int getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Ruta " + id_ruta + ", " + cantidad+ " Usuarios";
    }


}
