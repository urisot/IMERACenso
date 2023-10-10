package com.imera.censo.Contracts.Services;

import com.imera.censo.Contracts.Models.OPR_USUARIOS;

/**
 * Created by Daniel Esparza on 19/06/2015.
 */
public interface InicioCensoListeners {

    public void onCapturarClickListener();

    public void onClandestinoClickListener();

    public void onFactibleClickListener();

    public void onBackButtonPressed();

    public void FragmentChange(OPR_USUARIOS respaldo);

    public void ChangeCurrentId(String Id);

    public void OnSave(OPR_USUARIOS ofinal);

}
