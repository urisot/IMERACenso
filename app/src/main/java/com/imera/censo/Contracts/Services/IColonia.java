package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Colonia;

import java.util.ArrayList;

public interface IColonia {
    public void ColoniaInsert (Colonia o_CColonia);
    public ArrayList<Colonia> ColoniaGetList();
}
