package com.imera.censo.Contracts.Services;

public interface ICatalogo {
    public String GetId(String nombreTabla ,String descripcion,String nombreCampoId);
    public String GetText(String nombreTabla,String Id,String nombreCampoId);
    public void ActualizaUltimaVez(String strNombreCatalogo);
    public void InicializaTablas(String strNombreTabla, String strCampoId);
    public String GetFechaActualizacion(String nombreCatalogo);
}
