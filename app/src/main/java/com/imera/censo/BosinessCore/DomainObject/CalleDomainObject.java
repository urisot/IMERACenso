package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.CalleDAO;
import com.imera.censo.Contracts.Models.Calle;
import com.imera.censo.Contracts.Services.ICalle;

import java.util.ArrayList;

public class CalleDomainObject implements ICalle {
    @Override
    public void CalleInsert(Calle o_Calle) {
        CalleDAO CalleDAO = new CalleDAO();
        CalleDAO.CalleInsert(o_Calle);
    }

    @Override
    public ArrayList<Calle> CalleGetList() {
        CalleDAO CalleDAO = new CalleDAO();
        return CalleDAO.CalleGetList();
    }
}
