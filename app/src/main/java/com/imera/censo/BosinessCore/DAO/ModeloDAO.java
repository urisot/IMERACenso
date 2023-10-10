package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.ModeloEnum;
import com.imera.censo.Contracts.Models.Modelo;
import com.imera.censo.R;

import java.util.ArrayList;


public class ModeloDAO {

    public void ModeloInsert (Modelo o_Modelo){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(ModeloEnum.id_modelo.toString(), o_Modelo.getid_modelo());
            nuevoRegistro.put(ModeloEnum.descripcion.toString(), o_Modelo.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_modelos), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Modelo> ModeloGetList(){
        ArrayList<Modelo> listReturn = new ArrayList<Modelo>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_modelos)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new Modelo(cursor.getInt(cursor.getColumnIndex(ModeloEnum.id_modelo.toString()))
                            ,cursor.getString(cursor.getColumnIndex(ModeloEnum.descripcion.toString()))));
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
