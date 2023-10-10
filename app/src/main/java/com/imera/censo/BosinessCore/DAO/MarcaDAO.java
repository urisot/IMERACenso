package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.MarcaEnum;
import com.imera.censo.Contracts.Models.Marca;
import com.imera.censo.R;

import java.util.ArrayList;


public class MarcaDAO {

    public void MarcaInsert (Marca o_Marca){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(MarcaEnum.id_marca.toString(), o_Marca.getid_marca());
            nuevoRegistro.put(MarcaEnum.descripcion.toString(), o_Marca.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_marcas), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Marca> MarcaGetList(){
        ArrayList<Marca> listReturn = new ArrayList<Marca>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_marcas)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new Marca(cursor.getInt(cursor.getColumnIndex(MarcaEnum.id_marca.toString()))
                            ,cursor.getString(cursor.getColumnIndex(MarcaEnum.descripcion.toString()))));
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
