package com.imera.censo.BosinessCore.DomainObject;

import com.imera.censo.BosinessCore.DAO.UsuarioDAO;
import com.imera.censo.Contracts.Models.clsUsuario;
import com.imera.censo.Contracts.Services.IUsuario;

public class UsuarioDomainObject implements IUsuario {


    @Override
    public void UsuarioUpgrade(clsUsuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.UsuarioUpgrade(usuario);
    }

    @Override
    public void UsuarioDelete(clsUsuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.UsuarioDelete(usuario);
    }
}
