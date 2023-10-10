package com.imera.censo.BosinessCore.DomainObject;


import com.imera.censo.BosinessCore.DAO.ParametroDAO;
import com.imera.censo.Contracts.Services.IParametro;

public class ParametroDomainObject implements IParametro {

    @Override
    public void ParametroUpgrade(String strParametro, String strValor) {
        ParametroDAO parametroDAO = new ParametroDAO();
        parametroDAO.ParametroUpgrade(strParametro,strValor);
    }

    @Override
    public String GetValorParametro(String strParametro) {
        ParametroDAO parametroDAO = new ParametroDAO();
        return parametroDAO.GetValorParametro(strParametro);
    }
}
