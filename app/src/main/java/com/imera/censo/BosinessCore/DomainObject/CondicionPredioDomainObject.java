package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.CondicionPredioDAO;
import com.imera.censo.Contracts.Models.CondicionPredio;
import com.imera.censo.Contracts.Services.ICondicionPredio;

import java.util.ArrayList;

public class CondicionPredioDomainObject implements ICondicionPredio {
    @Override
    public void CondicionPredioInsert(CondicionPredio o_CondicionPredio) {
        CondicionPredioDAO CondicionPredioDAO = new CondicionPredioDAO();
        CondicionPredioDAO.CondicionPredioInsert(o_CondicionPredio);
    }

    @Override
    public ArrayList<CondicionPredio> CondicionPredioGetList() {
        CondicionPredioDAO CondicionPredioDAO = new CondicionPredioDAO();
        return CondicionPredioDAO.CondicionPredioGetList();
    }

    @Override
    public ArrayList<String> CondicionPredioGetListPicker() {
        CondicionPredioDAO CondicionPredioDAO = new CondicionPredioDAO();
        return CondicionPredioDAO.CondicionPredioGetListPicker();
    }
}
