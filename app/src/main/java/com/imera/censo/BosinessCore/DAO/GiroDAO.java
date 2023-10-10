package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.GiroEnum;
import com.imera.censo.Contracts.Models.Giro;
import com.imera.censo.R;

import java.util.ArrayList;


public class GiroDAO {

    public void GiroInsert (Giro o_Giro){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(GiroEnum.id_giro.toString(), o_Giro.getid_giro());
            nuevoRegistro.put(GiroEnum.descripcion.toString(), o_Giro.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_giros), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Giro> GiroGetList(){
        ArrayList<Giro> listReturn = new ArrayList<Giro>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_giros)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new Giro(cursor.getInt(cursor.getColumnIndex(GiroEnum.id_giro.toString()))
                            ,cursor.getString(cursor.getColumnIndex(GiroEnum.descripcion.toString()))));
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
