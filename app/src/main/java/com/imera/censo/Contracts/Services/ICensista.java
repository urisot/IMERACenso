package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Censista;

import java.util.ArrayList;

public interface ICensista {
    public void CensistaInsert (Censista o_Censista);
    public ArrayList<Censista> CensistaGetList();
}
