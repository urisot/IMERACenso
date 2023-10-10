package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.AnomaliaEnum;
import com.imera.censo.Contracts.Models.Anomalia;
import com.imera.censo.R;

import java.util.ArrayList;


public class AnomaliaDAO {

    public void AnomaliaInsert (Anomalia o_anomalia){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(AnomaliaEnum.id_anomalia.toString(), o_anomalia.getId_anomalia());
            nuevoRegistro.put(AnomaliaEnum.descripcion.toString(), o_anomalia.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_anomalias), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Anomalia> AnomaliaGetList(){
        ArrayList<Anomalia> listReturn = new ArrayList<Anomalia>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_anomalias)+";", null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {

                    listReturn.add( new Anomalia(cursor.getInt(cursor.getColumnIndex(AnomaliaEnum.id_anomalia.toString()))
                            ,cursor.getString(cursor.getColumnIndex(AnomaliaEnum.descripcion.toString()))));
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
