package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.TipoUsuarioEnum;
import com.imera.censo.Contracts.Models.TipoUsuario;
import com.imera.censo.R;

import java.util.ArrayList;


public class TipoUsuarioDAO {

    public void TipoUsuarioInsert (TipoUsuario o_TipoUsuario){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put(TipoUsuarioEnum.id_tipousuario.toString(), o_TipoUsuario.getid_tipousuario());
            nuevoRegistro.put(TipoUsuarioEnum.descripcion.toString(), o_TipoUsuario.getDescripcion());
            db.insert(App.getContext().getString(R.string.cat_tiposusuario), null, nuevoRegistro);
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<TipoUsuario> TipoUsuarioGetList(){
        ArrayList<TipoUsuario> listReturn = new ArrayList<TipoUsuario>();
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.cat_tiposusuario)+";", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    listReturn.add( new TipoUsuario(cursor.getInt(cursor.getColumnIndex(TipoUsuarioEnum.id_tipousuario.toString()))
                            ,cursor.getString(cursor.getColumnIndex(TipoUsuarioEnum.descripcion.toString()))));
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
