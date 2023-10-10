package com.imera.censo.ui.Adapters;


import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Services.DataUpdate;

public class MiFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private int cantidadItems;//cantidad fragments por implementacion
    private enCambioFragmento listener;// escucha para diferentes fragments


    public MiFragmentStatePagerAdapter(FragmentManager fragmentManager, int cantidadItems, enCambioFragmento enCambioFragmentoListener ) {
        super(fragmentManager);
        this.cantidadItems = cantidadItems;
        this.listener = enCambioFragmentoListener;
    }

    @Override
    public int getCount() {
        return cantidadItems;
    }

    @Override
    public Fragment getItem(int position) {
        return listener.cambioFragment(position);
    }

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        Log.e("getItemPosition","getItemPosition");
        listener.autoGuardado(((DataUpdate)object).getData());
        return PagerAdapter.POSITION_NONE;
    }

    /**
     * al cambiar de fragments se usa la posicion para evaluar que tipo de 'instancia' de fragment
     * regresara segun la implementación.
     */
    public interface enCambioFragmento{
        Fragment cambioFragment(int position);
        void autoGuardado(OPR_USUARIOS data);
    }
}





