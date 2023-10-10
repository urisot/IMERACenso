package com.imera.censo.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imera.censo.R;

import java.util.ArrayList;

public class AdaptadorRutas extends BaseAdapter {

    private ArrayList<ItemListaRutas> Datos_Lista = new ArrayList<>();
    private Context contexto;
    private LayoutInflater inflador;

    public AdaptadorRutas(Context c, ArrayList<ItemListaRutas> Datos){
        Datos_Lista = Datos;
        contexto = c;
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


            convertView = inflador.inflate(R.layout.listitemrutas, null);
        }

        ManejadorVista.t1 = (TextView) convertView.findViewById(R.id.txt_texto);
        ManejadorVista.t2 = (TextView) convertView.findViewById(R.id.txt_texto2);
        ManejadorVista.t3 = (TextView) convertView.findViewById(R.id.txt_texto3);
        ManejadorVista.t4 = (TextView) convertView.findViewById(R.id.txt_texto4);
        ManejadorVista.t5 = (TextView) convertView.findViewById(R.id.txt_texto5);

        ManejadorVista.t1.setText(Datos_Lista.get(position).t1);
        ManejadorVista.t2.setText(Datos_Lista.get(position).t2);
        ManejadorVista.t3.setText(Datos_Lista.get(position).t3);

        if(Datos_Lista.get(position).t4!=null){
            if(!Datos_Lista.get(position).t4.equals("") && !Datos_Lista.get(position).t4.equals("null"))
            ManejadorVista.t4.setText("Clave Localizacion: "+Datos_Lista.get(position).t4);
            else
                ManejadorVista.t4.setText("");
        }else
            ManejadorVista.t4.setText("");

        if(Datos_Lista.get(position).t5!=null)
        {
            if(!Datos_Lista.get(position).t5.equals("") && !Datos_Lista.get(position).t5.equals("null"))
                ManejadorVista.t5.setText("Medidor: "+Datos_Lista.get(position).t5);
            else
                ManejadorVista.t5.setText("");
        }else
            ManejadorVista.t5.setText("");


        convertView.setEnabled(false);

        return convertView;
    }

    public static class ItemListaRutas{
        String t1 ="";
        String t2 ="";
        String t3 ="";
        String t4 = "";
        String t5 = "";

        public ItemListaRutas(String t1, String t2, String t3,String t4, String t5) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
        }
    }

    static class Vista{
        protected TextView t1;
        protected TextView t2;
        protected TextView t3;
        protected TextView t4;
        protected TextView t5;
    }


}
