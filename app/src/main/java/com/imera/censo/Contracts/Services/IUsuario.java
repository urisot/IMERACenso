package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.clsUsuario;

public interface IUsuario {
    public void UsuarioUpgrade (clsUsuario usuario);
    public void UsuarioDelete (clsUsuario usuario);
}
