package com.imera.censo.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imera.censo.Contracts.Models.ItemComboRutas;
import com.imera.censo.R;

import java.util.ArrayList;

public class ComboRutasAdapter extends BaseAdapter {

    private ArrayList<ItemComboRutas> Datos_Lista = new ArrayList<>();
    private Context contexto;
    private LayoutInflater inflador;

    public ComboRutasAdapter(Context c, ArrayList<ItemComboRutas> Datos){
        Datos_Lista = Datos;
        contexto = c;
    }
    public void updateData(ArrayList<ItemComboRutas> lista) {
        Datos_Lista.clear();
        Datos_Lista.addAll(lista);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return Datos_Lista.size();
    }

    @Override
    public Object getItem(int posicion) {
        return Datos_Lista.get(posicion);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Vista ManejadorVista = new Vista();

        if (convertView == null) {
            inflador= (LayoutInflater)
                    contexto.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


            convertView = inflador.inflate(R.layout.listitem, null);
        }

        ManejadorVista.Icono = (ImageView) convertView.findViewById(R.id.img_menu);
        ManejadorVista.Descripcion = (TextView) convertView.findViewById(R.id.txt_menu);

        ManejadorVista.Descripcion.setText(Datos_Lista.get(position).toString());
  /*      ManejadorVista.Icono.setImageDrawable(contexto.getResources().
                getDrawable(Datos_Lista.get(position).Id_Icono));*/

        convertView.setEnabled(false);

        return convertView;
    }

    static class Vista{
        protected ImageView Icono;
        protected TextView Descripcion;
    }


}
