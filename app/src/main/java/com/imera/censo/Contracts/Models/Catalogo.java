package com.imera.censo.Contracts.Models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;

/**
 * Created by Daniel Esparza on 16/06/2015.
 */
public class Catalogo {

    private String NombreReal = "";
    private String NombreWCF = "";
    private String NombreId = "";
    private String NombreTabla = "";
    private boolean MostrarCargando = false;
    private boolean MostrarCorrecto = false;
    private boolean MostrarError = false;


    public Catalogo(String n0, String n1, String n2, String n3){
        this.NombreReal = n0;
        this.NombreWCF = n1;
        this.NombreId = n2;
        this.NombreTabla = n3;
    }

    public boolean isMostrarCargando() {
        return MostrarCargando;
    }

    public void setMostrarCargando(boolean mostrarCargando) {
        MostrarCargando = mostrarCargando;
    }

    public boolean isMostrarCorrecto() {
        return MostrarCorrecto;
    }

    public void setMostrarCorrecto(boolean mostrarCorrecto) {
        MostrarCorrecto = mostrarCorrecto;
    }

    public boolean isMostrarError() {
        return MostrarError;
    }

    public void setMostrarError(boolean mostrarError) {
        MostrarError = mostrarError;
    }

    public String getNombreReal() {
        return NombreReal;
    }

    public void setNombreReal(String nombreReal) {
        NombreReal = nombreReal;
    }

    public String getNombreId() {
        return NombreId;
    }

    public void setNombreId(String nombreId) {
        NombreId = nombreId;
    }

    public String getNombreWCF() {
        return NombreWCF;
    }

    public void setNombreWCF(String nombreWCF) {
        NombreWCF = nombreWCF;
    }

    public String getNombreTabla() {
        return NombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        NombreTabla = nombreTabla;
    }

    public static String GetText(String Catalogo,String Id,String nombreid){
        String Palabra = "";
        BDManager usdbh = new BDManager(App.getContext(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT descripcion FROM "+Catalogo+" WHERE "+nombreid+" = "+Id+";", null);

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

    public static String GetId(String Catalogo,String Palabra,String nombreid){
        String Id = "";
        BDManager usdbh = new BDManager(App.getContext(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT "+nombreid+" FROM "+Catalogo+" WHERE descripcion LIKE '"+Palabra+"' ", null);

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

}
