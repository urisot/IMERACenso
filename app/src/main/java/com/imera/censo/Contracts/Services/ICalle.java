package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Calle;

import java.util.ArrayList;

public interface ICalle {
    public void CalleInsert (Calle o_Calle);
    public ArrayList<Calle> CalleGetList();
}
