package com.imera.censo.BosinessCore.DomainObject;

import com.imera.censo.BosinessCore.DAO.CatalogoDAO;
import com.imera.censo.BosinessCore.DAO.OprUsuarioDAO;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Services.IOprUsuario;
import com.imera.censo.Services.PostCenso;


import java.util.ArrayList;

public class OprUsuarioDomainObject implements IOprUsuario {

    @Override
    public ArrayList<OPR_USUARIOS> CensoCapturadoGetList(int idRuta) {
       OprUsuarioDAO oprUsuarioDAO = new OprUsuarioDAO();
        return oprUsuarioDAO.CensoCapturadoGetList(idRuta);
    }

    @Override
    public OPR_USUARIOS OprOprUsuarioGetById(String id) {
        OprUsuarioDAO oprUsuarioDAO = new OprUsuarioDAO();
        return oprUsuarioDAO.OprOprUsuarioGetById(id);
    }

    @Override
    public void EliminaPadronDespuesSubir(String id) {
        OprUsuarioDAO oprUsuarioDAO = new OprUsuarioDAO();
        oprUsuarioDAO.EliminaPadronDespuesSubir(id);
    }

    @Override
    public PostCenso OprOprUsuarioSubirGetById(String id) {
        OprUsuarioDAO oprUsuarioDAO = new OprUsuarioDAO();
        return oprUsuarioDAO.OprOprUsuarioSubirGetById(id);
    }

    @Override
    public void ActualizaCampoDirecto(String strCampo, String strValor, String strIdWere) {
        OprUsuarioDAO oprUsuarioDAO = new OprUsuarioDAO();
        oprUsuarioDAO.ActualizaCampoDirecto(strCampo,strValor,strIdWere);
    }

    @Override
    public int GetCantidades(int intIdCarga, String strEstatus) {
        OprUsuarioDAO oprUsuarioDAO = new OprUsuarioDAO();
        return oprUsuarioDAO.GetCantidades(intIdCarga,strEstatus);
    }
}
