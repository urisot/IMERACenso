package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.CalleEnum;
import com.imera.censo.Contracts.Models.Calle;
import com.imera.censo.R;

import java.util.ArrayList;


public class CalleDAO {

    public void CalleInsert (Calle o_Calle){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(CalleEnum.id_calle.toString(), o_Calle.getId_calle());
            nuevoRegistro.put(CalleEnum.id_colonia.toString(), o_Calle.getId_calle());
            nuevoRegistro.put(CalleEnum.descripcion.toString(), o_Calle.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_calles), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Calle> CalleGetList(){
        ArrayList<Calle> listReturn = new ArrayList<Calle>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_calles)+";", null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {

                    listReturn.add( new Calle(cursor.getInt(cursor.getColumnIndex(CalleEnum.id_calle.toString()))
                            ,cursor.getInt(cursor.getColumnIndex(CalleEnum.id_colonia.toString()))
                            ,cursor.getString(cursor.getColumnIndex(CalleEnum.descripcion.toString()))));
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
