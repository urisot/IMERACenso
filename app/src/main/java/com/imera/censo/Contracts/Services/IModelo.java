package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.Contracts.Models.Modelo;

import java.util.ArrayList;

public interface IModelo {
    public void ModeloInsert (Modelo o_modelo);
    public ArrayList<Modelo>ModeloGetList();
}
