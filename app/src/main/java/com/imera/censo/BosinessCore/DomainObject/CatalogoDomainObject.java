package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.CatalogoDAO;
import com.imera.censo.Contracts.Services.ICatalogo;

public class CatalogoDomainObject implements ICatalogo {

    @Override
    public String GetId(String nombreTabla, String descripcion, String nombreCampoId) {
        CatalogoDAO catalogoDAO = new CatalogoDAO();
        return catalogoDAO.GetId(nombreTabla,descripcion,nombreCampoId);
    }

    @Override
    public String GetText(String nombreTabla, String Id, String nombreCampoId) {
        CatalogoDAO catalogoDAO = new CatalogoDAO();
        return catalogoDAO.GetText(nombreTabla,Id,nombreCampoId);
    }

    @Override
    public void ActualizaUltimaVez(String strNombreCatalogo) {
        CatalogoDAO catalogoDAO = new CatalogoDAO();
        catalogoDAO.ActualizaUltimaVez(strNombreCatalogo);
    }

    @Override
    public void InicializaTablas(String strNombreTabla, String strCampoId) {
        CatalogoDAO catalogoDAO = new CatalogoDAO();
        catalogoDAO.InicializaTablas(strNombreTabla,strCampoId);
    }

    @Override
    public String GetFechaActualizacion(String nombreCatalogo) {
        CatalogoDAO catalogoDAO = new CatalogoDAO();
        return catalogoDAO.GetFechaActualizacion(nombreCatalogo);
    }
}
