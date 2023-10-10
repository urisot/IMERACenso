package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.DiametroDAO;
import com.imera.censo.Contracts.Models.Diametro;
import com.imera.censo.Contracts.Services.IDiametro;

import java.util.ArrayList;

public class DiametroDomainObject implements IDiametro {
    @Override
    public void DiametroInsert(Diametro o_Diametro) {
        DiametroDAO DiametroDAO = new DiametroDAO();
        DiametroDAO.DiametroInsert(o_Diametro);
    }

    @Override
    public ArrayList<Diametro> DiametroGetList() {
        DiametroDAO DiametroDAO = new DiametroDAO();
        return DiametroDAO.DiametroGetList();
    }
}
