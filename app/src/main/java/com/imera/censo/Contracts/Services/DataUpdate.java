package com.imera.censo.Contracts.Services;

import com.imera.censo.Contracts.Models.OPR_USUARIOS;

/**
 * Created by DanielEsparza on 31/08/2016.
 */
public interface DataUpdate {
    void setData(OPR_USUARIOS data);
    OPR_USUARIOS getData();
}
