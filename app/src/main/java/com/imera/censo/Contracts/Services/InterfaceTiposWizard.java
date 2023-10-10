package com.imera.censo.Contracts.Services;

/**
 * Created by Daniel Esparza on 18/02/2016.
 */
public interface InterfaceTiposWizard {

    public void Siguiente(int pantalla);
    public void Anterior(int pantalla);
    public void Guardar(Object Datos);
    public void onClick(String accion);
    public void callSnackBar(String mensaje);

}
