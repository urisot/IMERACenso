package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Anomalia;

import java.util.ArrayList;

public interface IAnomalia {
    public void AnomaliaInsert (Anomalia o_anomalia);
    public ArrayList<Anomalia> AnomaliaGetList();
}
