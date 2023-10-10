package com.imera.censo.BosinessCore.DomainObject;

import com.imera.censo.BosinessCore.DAO.TipoGloboDAO;
import com.imera.censo.Contracts.Models.TipoGlobo;
import com.imera.censo.Contracts.Services.ITipoGlobo;

import java.util.ArrayList;

public class TipoGloboDomainObject implements ITipoGlobo {

    @Override
    public void TipoGloboInsert(TipoGlobo o_TipoGlobo) {
        TipoGloboDAO TipoGloboDAO = new TipoGloboDAO();
        TipoGloboDAO.TipoGloboInsert(o_TipoGlobo);
    }

    @Override
    public ArrayList<TipoGlobo> TipoGloboGetList() {
        TipoGloboDAO TipoGloboDAO = new TipoGloboDAO();
        return TipoGloboDAO.TipoGloboGetList();
    }
}
