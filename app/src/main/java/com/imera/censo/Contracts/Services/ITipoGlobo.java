package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.Contracts.Models.TipoGlobo;

import java.util.ArrayList;

public interface ITipoGlobo {
    public void TipoGloboInsert (TipoGlobo o_tipoglobo);
    public ArrayList<TipoGlobo>TipoGloboGetList();
}
