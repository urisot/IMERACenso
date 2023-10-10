package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.CensistaEnum;
import com.imera.censo.Contracts.Models.Censista;
import com.imera.censo.R;

import java.util.ArrayList;


public class CensistaDAO {

    public void CensistaInsert (Censista o_Censista){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(CensistaEnum.id_Personal.toString(), o_Censista.getId_Personal());
            nuevoRegistro.put(CensistaEnum.nombre.toString(), o_Censista.getNombre());
            db.insert(App.getContext().getString(R.string.cat_censistas), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Censista> CensistaGetList(){
        ArrayList<Censista> listReturn = new ArrayList<Censista>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_censistas)+";", null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {

                    listReturn.add( new Censista(cursor.getInt(cursor.getColumnIndex(CensistaEnum.id_Personal.toString()))
                            ,cursor.getString(cursor.getColumnIndex(CensistaEnum.nombre.toString()))));
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
