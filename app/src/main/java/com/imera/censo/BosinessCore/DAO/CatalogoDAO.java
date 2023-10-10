package com.imera.censo.BosinessCore.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CatalogoDAO {

    public String GetId(String nombreTabla ,String descripcion,String nombreCampoId){
        String Id = "";
        BDManager usdbh = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT "+nombreCampoId+" FROM "+nombreTabla+" WHERE descripcion LIKE '"+descripcion+"' ", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    Id = c.getString(0);
                } while (c.moveToNext());
            }

            if(!c.isClosed())c.close();

            //Cerramos la base de datos
            db.close();
        }

        return Id;
    }

    public String GetText(String nombreTabla,String Id,String nombreCampoId){
        String Palabra = "";
        BDManager usdbh = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT descripcion FROM "+nombreTabla+" WHERE "+nombreCampoId+" = "+Id+";", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    Id = c.getString(0);
                } while (c.moveToNext());
            }

            if(!c.isClosed())c.close();
            //Cerramos la base de datos
            db.close();
        }
        return Id;
    }

    public void ActualizaUltimaVez(String strNombreCatalogo){
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT fecha " +
                                            "FROM Catalogos_Actualizacion " +
                                            "WHERE catalogo LIKE '" + strNombreCatalogo + "'", null);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            ContentValues contentValues = new ContentValues();
            contentValues.put("fecha",dateFormat.format(date));

            if (cursor.moveToFirst()) {
                String strWhere = "catalogo LIKE '" +strNombreCatalogo+ "'";
                db.update("Catalogos_Actualizacion", contentValues, strWhere, null);

//                db.execSQL("UPDATE Catalogos_Actualizacion SET fecha = '" + dateFormat.format(date) + "' WHERE catalogo LIKE '" + strNombreCatalogo + "'");
            } else {
                contentValues.put("catalogo",strNombreCatalogo);
                db.insert("Catalogos_Actualizacion", null, contentValues);
//                db.execSQL("INSERT INTO Catalogos_Actualizacion (catalogo,fecha) VALUES ('" + strNombreCatalogo + "','" + dateFormat.format(date) + "')");
            }
        }
        db.close();
    }

    public void InicializaTablas(String strNombreTabla, String strCampoId){
        BDManager manager = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manager.getWritableDatabase();
        if (db != null) {//6
            db.execSQL("DROP TABLE IF EXISTS " + strNombreTabla);

            switch (strNombreTabla) {
                case "Cat_Censistas":
                    db.execSQL(BDManager.sqlCreateCencistas);
                    break;
                case "Cat_Calles":
                    db.execSQL("CREATE TABLE " + strNombreTabla + " (" + strCampoId + " integer not null ,id_colonia integer not null, descripcion text not null);");
                    break;
                default:
                    db.execSQL("CREATE TABLE " + strNombreTabla + " (" + strCampoId + " integer primary key ,descripcion text not null);");
                    break;
            }

            /*if (ListCatalogos.get(Id).getNombreTabla().equals("Cat_Capturistas"))
                db.execSQL(BDManager.sqlCreaCapturistas);
                //db.execSQL("CREATE TABLE " + ListCatalogos.get(Id).getNombreTabla() + " (" + ListCatalogos.get(Id).getNombreId() + " string primary key, nombre text not null, estado_activo integer default 0);");
            else if (ListCatalogos.get(Id).getNombreTabla().equals("Cat_Brigadistas"))
                db.execSQL(BDManager.sqlCreaBrigadistas);
                //db.execSQL("CREATE TABLE " + ListCatalogos.get(Id).getNombreTabla() + " (" + ListCatalogos.get(Id).getNombreId() + " integer primary key ,nombre text not null,estado_activo integer default 0);");
            else if (ListCatalogos.get(Id).getNombreTabla().equals("Cat_Articulos"))
                db.execSQL(BDManager.sqlCreaArticulos);
                //db.execSQL("CREATE TABLE " + ListCatalogos.get(Id).getNombreTabla() + " (" + ListCatalogos.get(Id).getNombreId() + " integer primary key ,descripcion text not null,costo REAL not null);");
            else if (ListCatalogos.get(Id).getNombreTabla().equals("Cfg_Columnas"))
                db.execSQL(BDManager.sqlCfgColumnas);
                *//*db.execSQL("CREATE TABLE " + ListCatalogos.get(Id).getNombreTabla() + " (" + ListCatalogos.get(Id).getNombreId() + " integer PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                                                                                                                                    "id_trabajo integer null," +
                                                                                                                                    "column_name text null,"+
                                                                                                                                    "tabla text null,"+
                                                                                                                                    "posicion integer null," +
                                                                                                                                    "descripcion text null,"+
                                                                                                                                    "control text null," +
                                                                                                                                    "datafield text null,"+
                                                                                                                                    "datatable text null,"+
                                                                                                                                    "displayfield text null,"+
                                                                                                                                    "returnfield text null,"+
                                                                                                                                    "newvalue text null,"+
                                                                                                                                    "min_value integer null,"+
                                                                                                                                    "max_value integer null,"+
                                                                                                                                    "editable integer null"+
                                                                                                                                    ");");*//*
            else
                db.execSQL("CREATE TABLE " + ListCatalogos.get(Id).getNombreTabla() + " (" + ListCatalogos.get(Id).getNombreId() + " integer primary key ,descripcion text not null);");

            */

        }
        db.close();

    }

    public String GetFechaActualizacion(String nombreCatalogo){
        String strFecha = "";
        BDManager usdbh = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT fecha " +
                                        "FROM Catalogos_Actualizacion " +
                                        "WHERE catalogo LIKE '"+nombreCatalogo+"';",null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    strFecha = c.getString(0);
                } while (c.moveToNext());
            }

            if(!c.isClosed())c.close();
            //Cerramos la base de datos
            db.close();
        }
        return strFecha;
    }

}
