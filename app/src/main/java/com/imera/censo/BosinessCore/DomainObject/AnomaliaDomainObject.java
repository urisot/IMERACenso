package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.AnomaliaDAO;
import com.imera.censo.Contracts.Models.Anomalia;
import com.imera.censo.Contracts.Services.IAnomalia;

import java.util.ArrayList;

public class AnomaliaDomainObject implements IAnomalia {
    @Override
    public void AnomaliaInsert(Anomalia o_anomalia) {
        AnomaliaDAO anomaliaDAO = new AnomaliaDAO();
        anomaliaDAO.AnomaliaInsert(o_anomalia);
    }

    @Override
    public ArrayList<Anomalia> AnomaliaGetList() {
        AnomaliaDAO anomaliaDAO = new AnomaliaDAO();
        return anomaliaDAO.AnomaliaGetList();
    }
}
