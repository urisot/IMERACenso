package com.imera.censo.BosinessCore.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.ParametroEnum;
import com.imera.censo.R;


public class ParametroDAO {

    public void ParametroUpgrade (String strParametro, String strValor){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM Cfg_Parametros " +
                                        "WHERE parametro = '" +strParametro+"';", null);

            ContentValues contentValues = new ContentValues();

            contentValues.put(ParametroEnum.parametro.toString(), strParametro);
            contentValues.put(ParametroEnum.valor.toString(), strValor);

            if(!c.moveToFirst()){

                db.insert(App.getContext().getString(R.string.cfg_parametro), null, contentValues);

            }else {

                String strWhere = "parametro = '" + strParametro +"'";
                db.update(App.getContext().getString(R.string.cfg_parametro), contentValues, strWhere, null);
            }
            if(!c.isClosed())c.close();
            db.close();
        }
    }

    public String GetValorParametro(String strParametro){
        String strValorParametro = "";
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();
        if(db!=null){
            Cursor c = db.rawQuery("SELECT valor " +
                                        "FROM Cfg_Parametros " +
                                        "WHERE parametro ='"+strParametro+"';",null);
            if(c.moveToFirst()){
                strValorParametro = c.getString(0).toString().trim();

            }
            if(!c.isClosed())c.close();
            db.close();
        }

        return strValorParametro;
    }

}
