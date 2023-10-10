package com.imera.censo.BosinessCore.DomainObject;

import com.imera.censo.BosinessCore.DAO.TipoUsuarioDAO;
import com.imera.censo.Contracts.Models.TipoUsuario;
import com.imera.censo.Contracts.Services.ITipoUsuario;

import java.util.ArrayList;

public class TipoUsuarioDomainObject implements ITipoUsuario {

    @Override
    public void TipoUsuarioInsert(TipoUsuario o_TipoUsuario) {
        TipoUsuarioDAO TipoUsuarioDAO = new TipoUsuarioDAO();
        TipoUsuarioDAO.TipoUsuarioInsert(o_TipoUsuario);
    }

    @Override
    public ArrayList<TipoUsuario> TipoUsuarioGetList() {
        TipoUsuarioDAO TipoUsuarioDAO = new TipoUsuarioDAO();
        return TipoUsuarioDAO.TipoUsuarioGetList();
    }
}
