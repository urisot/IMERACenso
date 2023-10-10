package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.ColoniaDAO;
import com.imera.censo.Contracts.Models.Colonia;
import com.imera.censo.Contracts.Services.IColonia;

import java.util.ArrayList;

public class ColoniaDomainObject implements IColonia {
    @Override
    public void ColoniaInsert(Colonia o_Colonia) {
        ColoniaDAO ColoniaDAO = new ColoniaDAO();
        ColoniaDAO.ColoniaInsert(o_Colonia);
    }

    @Override
    public ArrayList<Colonia> ColoniaGetList() {
        ColoniaDAO ColoniaDAO = new ColoniaDAO();
        return ColoniaDAO.ColoniaGetList();
    }
}
