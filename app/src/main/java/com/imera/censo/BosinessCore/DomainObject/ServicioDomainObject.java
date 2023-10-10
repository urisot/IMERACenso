package com.imera.censo.BosinessCore.DomainObject;

import com.imera.censo.BosinessCore.DAO.ServicioDAO;
import com.imera.censo.Contracts.Models.Servicio;
import com.imera.censo.Contracts.Services.IServicio;

import java.util.ArrayList;

public class ServicioDomainObject implements IServicio {

    @Override
    public void ServicioInsert(Servicio o_Servicio) {
        ServicioDAO ServicioDAO = new ServicioDAO();
        ServicioDAO.ServicioInsert(o_Servicio);
    }

    @Override
    public ArrayList<Servicio> ServicioGetList() {
        ServicioDAO ServicioDAO = new ServicioDAO();
        return ServicioDAO.ServicioGetList();
    }
}
