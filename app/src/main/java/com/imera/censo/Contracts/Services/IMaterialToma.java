package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.Contracts.Models.MaterialToma;

import java.util.ArrayList;

public interface IMaterialToma {
    public void MaterialTomaInsert (MaterialToma o_Material);
    public ArrayList<MaterialToma> MaterialTomaGetList();
}
