package com.imera.censo.Contracts.Services;

/**
 * Created by Daniel Esparza on 18/06/2015.
 */
public interface OnRutaSelectListener {
    public void OnItemClick(String Id);
    public void img_nohayl();
    public void muestra_oculta_menu_lateral(boolean boolMostrar);
    public void muestra_oculta_listado_agrupado(boolean boolMostrar);
    public void muestra_pagina_principal();
    public void muestra_descargar_ordenes();
    public void actualiza_pocicion();
}


