package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Diametro;

import java.util.ArrayList;

public interface IDiametro {
    public void DiametroInsert (Diametro o_CDiametro);
    public ArrayList<Diametro> DiametroGetList();
}
