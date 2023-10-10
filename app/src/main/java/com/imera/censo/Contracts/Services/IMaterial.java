package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Material;

import java.util.ArrayList;

public interface IMaterial {
    public void MaterialInsert (Material o_CMaterial);
    public ArrayList<Material> MaterialGetList();
}
