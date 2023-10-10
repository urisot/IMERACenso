package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Marca;

import java.util.ArrayList;

public interface IMarca {
    public void MarcaInsert (Marca o_CMarca);
    public ArrayList<Marca> MarcaGetList();
}
