package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.TipoGloboEnum;
import com.imera.censo.Contracts.Models.TipoGlobo;
import com.imera.censo.R;

import java.util.ArrayList;


public class TipoGloboDAO {

    public void TipoGloboInsert (TipoGlobo o_TipoGlobo){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(TipoGloboEnum.id_tipoglobo.toString(), o_TipoGlobo.getid_tipoglobo());
            nuevoRegistro.put(TipoGloboEnum.descripcion.toString(), o_TipoGlobo.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_tiposglobo), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<TipoGlobo> TipoGloboGetList(){
        ArrayList<TipoGlobo> listReturn = new ArrayList<TipoGlobo>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_tiposglobo)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new TipoGlobo(cursor.getInt(cursor.getColumnIndex(TipoGloboEnum.id_tipoglobo.toString()))
                            ,cursor.getString(cursor.getColumnIndex(TipoGloboEnum.descripcion.toString()))));
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
