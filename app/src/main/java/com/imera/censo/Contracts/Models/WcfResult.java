package com.imera.censo.Contracts.Models;

public class WcfResult {
    private String estado;
    private String descripcion;
    private int error_number;
    private String error_menssage;

    public WcfResult() {
    }

    public WcfResult(String estado, String descripcion, int error_number, String error_menssage) {
        this.estado = estado;
        this.descripcion = descripcion;
        this.error_number = error_number;
        this.error_menssage = error_menssage;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getError_number() {
        return error_number;
    }

    public void setError_number(int error_number) {
        this.error_number = error_number;
    }

    public String getError_menssage() {
        return error_menssage;
    }

    public void setError_menssage(String error_menssage) {
        this.error_menssage = error_menssage;
    }
}
