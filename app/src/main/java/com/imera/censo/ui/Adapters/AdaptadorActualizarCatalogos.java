package com.imera.censo.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.BosinessCore.DomainObject.CatalogoDomainObject;
import com.imera.censo.Contracts.Models.Catalogo;
import com.imera.censo.Contracts.Services.ICatalogo;
import com.imera.censo.R;

import java.util.ArrayList;

public class AdaptadorActualizarCatalogos extends BaseAdapter {
    private ArrayList<Catalogo> Al_Data = new ArrayList<Catalogo>();
    private Context contexto;
    private LayoutInflater inflador;

    public AdaptadorActualizarCatalogos(Context c,ArrayList<Catalogo> al_Data){
        this.contexto = c;
        this.Al_Data = al_Data;
    }

    public void updateData(ArrayList<Catalogo> lista) {
        Al_Data.clear();
        Al_Data.addAll(lista);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Al_Data.size();
    }

    @Override
    public Object getItem(int position) {
        return Al_Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            inflador = (LayoutInflater)contexto.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflador.inflate(R.layout.listitem_actualizacion_catalogos,null);

            holder = new ViewHolder();

            holder.progressbar = (ProgressBar)convertView.findViewById(R.id.pb_estado_actualizacion);
            holder.correcto = (ImageView)convertView.findViewById(R.id.img_done);
            holder.error = (ImageView)convertView.findViewById(R.id.img_error);
            holder.texto = (TextView)convertView.findViewById(R.id.txt_texto);
            holder.Fecha = (TextView)convertView.findViewById(R.id.txt_fecha_actualizacion);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder)convertView.getTag();
        }


        holder.texto.setText(Al_Data.get(position).getNombreReal());

        ICatalogo catService = new CatalogoDomainObject();
        String strFechaActualizacion = catService.GetFechaActualizacion(Al_Data.get(position).getNombreTabla());
        holder.Fecha.setText("Ultima Actualizacion: "+strFechaActualizacion);


        if(Al_Data.get(position).isMostrarCargando()){
            holder.progressbar.setVisibility(View.VISIBLE);
        }else{
            holder.progressbar.setVisibility(View.INVISIBLE);
        }

        if(Al_Data.get(position).isMostrarCorrecto()){
            holder.correcto.setVisibility(View.VISIBLE);
        }else{
            holder.correcto.setVisibility(View.INVISIBLE);
        }

        if(Al_Data.get(position).isMostrarError()){
            holder.error.setVisibility(View.VISIBLE);
        }else{
            holder.error.setVisibility(View.INVISIBLE);
        }

        if (holder.texto.getText().equals("Todos")){
            holder.progressbar.setVisibility(View.GONE);
            holder.texto.setTypeface(null, Typeface.BOLD);
            holder.Fecha.setVisibility(View.GONE);
        }

        convertView.setEnabled(false);
        return convertView;
    }

    private class ViewHolder {
        TextView texto;
        ProgressBar progressbar;
        ImageView correcto;
        ImageView error;
        TextView Fecha;
    }
}
