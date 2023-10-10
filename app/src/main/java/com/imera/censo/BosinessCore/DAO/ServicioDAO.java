package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.ServicioEnum;
import com.imera.censo.Contracts.Models.Servicio;
import com.imera.censo.R;

import java.util.ArrayList;


public class ServicioDAO {


    public void ServicioInsert (Servicio o_Servicio){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();
        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(ServicioEnum.id_Servicio.toString(), o_Servicio.getId_Servicio());
            nuevoRegistro.put(ServicioEnum.descripcion.toString(), o_Servicio.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_servicios), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Servicio> ServicioGetList(){
        ArrayList<Servicio> listReturn = new ArrayList<Servicio>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_servicios)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new Servicio(cursor.getInt(cursor.getColumnIndex(ServicioEnum.id_Servicio.toString()))
                            ,cursor.getString(cursor.getColumnIndex(ServicioEnum.descripcion.toString()))));
                    cursor.moveToNext();
                }
            }

            if(!cursor.isClosed())cursor.close();

            //Cerramos la base de datos
            db.close();
        }//6
        return listReturn;
    }
}
