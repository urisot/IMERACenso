package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Giro;

import java.util.ArrayList;

public interface IGiro {
    public void GiroInsert (Giro o_CGiro);
    public ArrayList<Giro> GiroGetList();
}
