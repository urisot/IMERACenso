package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.CondicionPredio;

import java.util.ArrayList;

public interface ICondicionPredio {
    void CondicionPredioInsert (CondicionPredio o_CCondicionPredio);
    ArrayList<CondicionPredio> CondicionPredioGetList();
    ArrayList<String> CondicionPredioGetListPicker();
}
