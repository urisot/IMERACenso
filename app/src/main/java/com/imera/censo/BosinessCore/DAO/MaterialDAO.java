package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.MaterialEnum;
import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.R;

import java.util.ArrayList;


public class MaterialDAO {

    public void MaterialInsert (Material o_Material){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(MaterialEnum.id_material.toString(), o_Material.getid_material());
            nuevoRegistro.put(MaterialEnum.descripcion.toString(), o_Material.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_materiales), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Material> MaterialGetList(){
        ArrayList<Material> listReturn = new ArrayList<Material>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_materiales)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new Material(cursor.getInt(cursor.getColumnIndex(MaterialEnum.id_material.toString()))
                            ,cursor.getString(cursor.getColumnIndex(MaterialEnum.descripcion.toString()))));
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
