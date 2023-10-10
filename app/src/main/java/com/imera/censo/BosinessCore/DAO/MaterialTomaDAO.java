package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.MaterialTomaEnum;
import com.imera.censo.Contracts.Models.MaterialToma;
import com.imera.censo.R;

import java.util.ArrayList;


public class MaterialTomaDAO {

    public void MaterialTomaInsert (MaterialToma o_MaterialToma){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(MaterialTomaEnum.id_materialtoma.toString(), o_MaterialToma.getid_materialtoma());
            nuevoRegistro.put(MaterialTomaEnum.descripcion.toString(), o_MaterialToma.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_materialtoma), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<MaterialToma> MaterialTomaGetList(){
        ArrayList<MaterialToma> listReturn = new ArrayList<MaterialToma>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_materialtoma)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new MaterialToma(cursor.getInt(cursor.getColumnIndex(MaterialTomaEnum.id_materialtoma.toString()))
                            ,cursor.getString(cursor.getColumnIndex(MaterialTomaEnum.descripcion.toString()))));
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
