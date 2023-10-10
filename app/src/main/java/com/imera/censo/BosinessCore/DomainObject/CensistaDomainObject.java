package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.CensistaDAO;
import com.imera.censo.Contracts.Models.Censista;
import com.imera.censo.Contracts.Services.ICensista;

import java.util.ArrayList;

public class CensistaDomainObject implements ICensista {
    @Override
    public void CensistaInsert(Censista o_Censista) {
        CensistaDAO CensistaDAO = new CensistaDAO();
        CensistaDAO.CensistaInsert(o_Censista);
    }

    @Override
    public ArrayList<Censista> CensistaGetList() {
        CensistaDAO CensistaDAO = new CensistaDAO();
        return CensistaDAO.CensistaGetList();
    }
}
