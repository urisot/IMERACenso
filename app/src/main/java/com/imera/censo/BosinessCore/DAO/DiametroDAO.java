package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.DiametroEnum;
import com.imera.censo.Contracts.Models.Diametro;
import com.imera.censo.R;

import java.util.ArrayList;


public class DiametroDAO {

    public void DiametroInsert (Diametro o_Diametro){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(DiametroEnum.id_diametro.toString(), o_Diametro.getId_diametro());
            nuevoRegistro.put(DiametroEnum.descripcion.toString(), o_Diametro.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_diametros), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Diametro> DiametroGetList(){
        ArrayList<Diametro> listReturn = new ArrayList<Diametro>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_diametros)+";", null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {

                    listReturn.add( new Diametro(cursor.getInt(cursor.getColumnIndex(DiametroEnum.id_diametro.toString()))
                            ,cursor.getString(cursor.getColumnIndex(DiametroEnum.descripcion.toString()))));
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
