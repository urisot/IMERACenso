package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.ColoniaEnum;
import com.imera.censo.Contracts.Models.Colonia;
import com.imera.censo.R;

import java.util.ArrayList;


public class ColoniaDAO {

    public void ColoniaInsert (Colonia o_Colonia){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(ColoniaEnum.id_colonia.toString(), o_Colonia.getid_colonia());
            nuevoRegistro.put(ColoniaEnum.descripcion.toString(), o_Colonia.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_colonias), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Colonia> ColoniaGetList(){
        ArrayList<Colonia> listReturn = new ArrayList<Colonia>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_colonias)+";", null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {

                    listReturn.add( new Colonia(cursor.getInt(cursor.getColumnIndex(ColoniaEnum.id_colonia.toString()))
                            ,cursor.getString(cursor.getColumnIndex(ColoniaEnum.descripcion.toString()))));
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
