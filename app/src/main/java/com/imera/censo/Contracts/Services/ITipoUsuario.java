package com.imera.censo.Contracts.Services;


import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.Contracts.Models.TipoUsuario;

import java.util.ArrayList;

public interface ITipoUsuario {
    public void TipoUsuarioInsert (TipoUsuario o_tipousuario);
    public ArrayList<TipoUsuario>TipoUsuarioGetList();
}
