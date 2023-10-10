package com.imera.censo.BosinessCore.DomainObject;

import com.imera.censo.BosinessCore.DAO.ModeloDAO;
import com.imera.censo.Contracts.Models.Modelo;
import com.imera.censo.Contracts.Services.IModelo;

import java.util.ArrayList;

public class ModeloDomainObject implements IModelo {

    @Override
    public void ModeloInsert(Modelo o_modelo) {
        ModeloDAO ModeloDAO = new ModeloDAO();
        ModeloDAO.ModeloInsert(o_modelo);
    }

    @Override
    public ArrayList<Modelo> ModeloGetList() {
        ModeloDAO ModeloDAO = new ModeloDAO();
        return ModeloDAO.ModeloGetList();
    }
}
