package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.TipoCasa;

import java.util.ArrayList;

public interface ITipoCasa {
    void TipoCasaInsert (TipoCasa o_CTipoCasa);
    ArrayList<TipoCasa> TipoCasaGetList();
    ArrayList<String> TipoCasaGetListPicker();
}
