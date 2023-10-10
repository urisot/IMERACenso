package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.GiroDAO;
import com.imera.censo.Contracts.Models.Giro;
import com.imera.censo.Contracts.Services.IGiro;

import java.util.ArrayList;

public class GiroDomainObject implements IGiro {
    @Override
    public void GiroInsert(Giro o_Giro) {
        GiroDAO GiroDAO = new GiroDAO();
        GiroDAO.GiroInsert(o_Giro);
    }

    @Override
    public ArrayList<Giro> GiroGetList() {
        GiroDAO GiroDAO = new GiroDAO();
        return GiroDAO.GiroGetList();
    }
}
