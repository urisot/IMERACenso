package com.imera.censo.Contracts.Services;

import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Services.PostCenso;

import java.util.ArrayList;

public interface IOprUsuario {
    ArrayList<OPR_USUARIOS> CensoCapturadoGetList(int idRuta);
    OPR_USUARIOS OprOprUsuarioGetById(String id);
    void EliminaPadronDespuesSubir(String id);
    PostCenso OprOprUsuarioSubirGetById(String id);
    void ActualizaCampoDirecto(String strCampo, String strValor, String strIdWere);
    int GetCantidades(int intIdCarga, String strEstatus);
}
