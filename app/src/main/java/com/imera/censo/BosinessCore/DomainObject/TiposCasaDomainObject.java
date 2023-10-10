package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.TipoCasaDAO;
import com.imera.censo.Contracts.Models.TipoCasa;
import com.imera.censo.Contracts.Services.ITipoCasa;

import java.util.ArrayList;

public class TiposCasaDomainObject implements ITipoCasa {
    @Override
    public void TipoCasaInsert(TipoCasa o_TipoCasa) {
        TipoCasaDAO TipoCasaDAO = new TipoCasaDAO();
        TipoCasaDAO.TipoCasaInsert(o_TipoCasa);
    }

    @Override
    public ArrayList<TipoCasa> TipoCasaGetList() {
        TipoCasaDAO TipoCasaDAO = new TipoCasaDAO();
        return TipoCasaDAO.TipoCasaGetList();
    }

    @Override
    public ArrayList<String> TipoCasaGetListPicker() {
        TipoCasaDAO TipoCasaDAO = new TipoCasaDAO();
        return TipoCasaDAO.TipoCasaGetListPicker();
    }
}
