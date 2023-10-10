package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.CondicionPredioEnum;
import com.imera.censo.Contracts.Models.CondicionPredio;
import com.imera.censo.Contracts.Models.TipoCasa;
import com.imera.censo.R;

import java.util.ArrayList;


public class CondicionPredioDAO {
    BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
    SQLiteDatabase db = manejador.getReadableDatabase();

    public void CondicionPredioInsert (CondicionPredio o_CondicionPredio){
        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(CondicionPredioEnum.id_condicionpredio.toString(), o_CondicionPredio.getId_condicionpredio());
            nuevoRegistro.put(CondicionPredioEnum.descripcion.toString(), o_CondicionPredio.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_condicionpredio), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<CondicionPredio> CondicionPredioGetList(){
        ArrayList<CondicionPredio> listReturn = new ArrayList<CondicionPredio>();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_condicionpredio)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new CondicionPredio(cursor.getInt(cursor.getColumnIndex(CondicionPredioEnum.id_condicionpredio.toString()))
                            ,cursor.getString(cursor.getColumnIndex(CondicionPredioEnum.descripcion.toString()))));
                    cursor.moveToNext();
                }
            }

            if(!cursor.isClosed())cursor.close();

            //Cerramos la base de datos
            db.close();
        }//6
        return listReturn;
    }

    public ArrayList<String> CondicionPredioGetListPicker(){
        ArrayList<CondicionPredio> tCasaList = new ArrayList<CondicionPredio>();
        ArrayList<String> listReturn = new ArrayList<String>();

        tCasaList = CondicionPredioGetList();

        for (CondicionPredio condicionPredio : tCasaList) {
            listReturn.add(condicionPredio.getDescripcion());
        }

        return listReturn;
    }
}
