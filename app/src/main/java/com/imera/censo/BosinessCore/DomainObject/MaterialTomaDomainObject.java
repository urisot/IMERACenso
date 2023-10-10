package com.imera.censo.BosinessCore.DomainObject;

import com.imera.censo.BosinessCore.DAO.MaterialDAO;
import com.imera.censo.BosinessCore.DAO.MaterialTomaDAO;
import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.Contracts.Models.MaterialToma;
import com.imera.censo.Contracts.Services.IMaterialToma;

import java.util.ArrayList;

public class MaterialTomaDomainObject implements IMaterialToma {

    @Override
    public void MaterialTomaInsert(MaterialToma o_Material) {
        MaterialTomaDAO materialTomaDAO = new MaterialTomaDAO();
        materialTomaDAO.MaterialTomaInsert(o_Material);
    }

    @Override
    public ArrayList<MaterialToma> MaterialTomaGetList() {
        MaterialTomaDAO materialTomaDAO = new MaterialTomaDAO();
        return materialTomaDAO.MaterialTomaGetList();
    }
}
