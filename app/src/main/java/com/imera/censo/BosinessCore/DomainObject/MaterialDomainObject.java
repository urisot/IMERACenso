package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.MaterialDAO;
import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.Contracts.Services.IMaterial;

import java.util.ArrayList;

public class MaterialDomainObject implements IMaterial {
    @Override
    public void MaterialInsert(Material o_Material) {
        MaterialDAO MaterialDAO = new MaterialDAO();
        MaterialDAO.MaterialInsert(o_Material);
    }

    @Override
    public ArrayList<Material> MaterialGetList() {
        MaterialDAO MaterialDAO = new MaterialDAO();
        return MaterialDAO.MaterialGetList();
    }
}
