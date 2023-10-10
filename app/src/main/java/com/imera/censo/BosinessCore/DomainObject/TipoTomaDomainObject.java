package com.imera.censo.BosinessCore.DomainObject;

import com.imera.censo.BosinessCore.DAO.TipoTomaDAO;
import com.imera.censo.Contracts.Models.TipoToma;
import com.imera.censo.Contracts.Services.ITipoToma;

import java.util.ArrayList;

public class TipoTomaDomainObject implements ITipoToma {

    @Override
    public void TipoTomaInsert(TipoToma o_TipoToma) {
        TipoTomaDAO TipoTomaDAO = new TipoTomaDAO();
        TipoTomaDAO.TipoTomaInsert(o_TipoToma);
    }

    @Override
    public ArrayList<TipoToma> TipoTomaGetList() {
        TipoTomaDAO TipoTomaDAO = new TipoTomaDAO();
        return TipoTomaDAO.TipoTomaGetList();
    }
}
