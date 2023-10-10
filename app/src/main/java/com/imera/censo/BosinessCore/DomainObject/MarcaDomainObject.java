package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.MarcaDAO;
import com.imera.censo.Contracts.Models.Marca;
import com.imera.censo.Contracts.Services.IMarca;

import java.util.ArrayList;

public class MarcaDomainObject implements IMarca {
    @Override
    public void MarcaInsert(Marca o_Marca) {
        MarcaDAO MarcaDAO = new MarcaDAO();
        MarcaDAO.MarcaInsert(o_Marca);
    }

    @Override
    public ArrayList<Marca> MarcaGetList() {
        MarcaDAO MarcaDAO = new MarcaDAO();
        return MarcaDAO.MarcaGetList();
    }
}
