package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.Contracts.Models.Servicio;

import java.util.ArrayList;

public interface IServicio {
    public void ServicioInsert (Servicio o_servicio);
    public ArrayList<Servicio>ServicioGetList();
}
