package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.Contracts.Models.TipoToma;

import java.util.ArrayList;

public interface ITipoToma {
    public void TipoTomaInsert (TipoToma o_tipotoma);
    public ArrayList<TipoToma>TipoTomaGetList();
}
