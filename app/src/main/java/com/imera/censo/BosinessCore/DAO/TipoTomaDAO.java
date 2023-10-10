package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.TipoTomaEnum;
import com.imera.censo.Contracts.Models.TipoToma;
import com.imera.censo.R;

import java.util.ArrayList;


public class TipoTomaDAO {

    public void TipoTomaInsert (TipoToma o_TipoToma){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(TipoTomaEnum.id_TipoToma.toString(), o_TipoToma.getId_TipoToma());
            nuevoRegistro.put(TipoTomaEnum.descripcion.toString(), o_TipoToma.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_tipostoma), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<TipoToma> TipoTomaGetList(){
        ArrayList<TipoToma> listReturn = new ArrayList<TipoToma>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_tipostoma)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new TipoToma(cursor.getInt(cursor.getColumnIndex(TipoTomaEnum.id_TipoToma.toString()))
                            ,cursor.getString(cursor.getColumnIndex(TipoTomaEnum.descripcion.toString()))));
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
