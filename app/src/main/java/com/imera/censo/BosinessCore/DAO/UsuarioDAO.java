package com.imera.censo.BosinessCore.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.UsuarioEnum;
import com.imera.censo.Contracts.Models.clsUsuario;
import com.imera.censo.R;


public class UsuarioDAO {

    public void UsuarioUpgrade (clsUsuario usuario){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.sys_usuarios)+" WHERE usuario = '" +usuario.getUsuario()+"';", null);

            ContentValues contentValues = new ContentValues();

            contentValues.put(UsuarioEnum.id_usuario.toString(), usuario.getId_usuario());
            contentValues.put(UsuarioEnum.nombre.toString(), usuario.getNombre());
            contentValues.put(UsuarioEnum.usuario.toString(), usuario.getUsuario());
            contentValues.put(UsuarioEnum.imagen.toString(), "");

            if(!c.moveToFirst()){

                db.insert(App.getContext().getString(R.string.sys_usuarios), null, contentValues);

            }else {

                String strWhere = "usuario = '" + usuario.getUsuario() +"'";
                db.update(App.getContext().getString(R.string.sys_usuarios), contentValues, strWhere, null);
            }
            if(!c.isClosed())c.close();
            db.close();
        }
    }

    public void UsuarioDelete (clsUsuario usuario){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            String strWhere = "usuario = '" + usuario.getUsuario() +"'";
            db.delete(App.getContext().getString(R.string.sys_usuarios), strWhere, null);
            db.close();
        }

    }

}
