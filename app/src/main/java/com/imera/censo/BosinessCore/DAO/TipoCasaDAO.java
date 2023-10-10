package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.TipoCasaEnum;
import com.imera.censo.Contracts.Models.TipoCasa;
import com.imera.censo.R;

import java.util.ArrayList;


public class TipoCasaDAO {
    BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
    SQLiteDatabase db = manejador.getReadableDatabase();

    public void TipoCasaInsert (TipoCasa o_TipoCasa){
        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(TipoCasaEnum.id_tipocasa.toString(), o_TipoCasa.getId_tipocasa());
            nuevoRegistro.put(TipoCasaEnum.descripcion.toString(), o_TipoCasa.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_tipocasa), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<TipoCasa> TipoCasaGetList(){
        ArrayList<TipoCasa> listReturn = new ArrayList<TipoCasa>();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_tipocasa)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new TipoCasa(cursor.getInt(cursor.getColumnIndex(TipoCasaEnum.id_tipocasa.toString()))
                            ,cursor.getString(cursor.getColumnIndex(TipoCasaEnum.descripcion.toString()))));
                    cursor.moveToNext();
                }
            }

            if(!cursor.isClosed())cursor.close();

            //Cerramos la base de datos
            db.close();
        }//6
        return listReturn;
    }

    public ArrayList<String> TipoCasaGetListPicker(){
        ArrayList<TipoCasa> tCasaList = new ArrayList<TipoCasa>();
        ArrayList<String> listReturn = new ArrayList<String>();

        tCasaList = TipoCasaGetList();

        for (TipoCasa tipoCasa : tCasaList) {
            listReturn.add(tipoCasa.getDescripcion());
        }

        return listReturn;
    }
}
