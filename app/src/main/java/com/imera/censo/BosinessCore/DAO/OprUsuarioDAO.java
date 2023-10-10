package com.imera.censo.BosinessCore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.MaterialEnum;
import com.imera.censo.Contracts.Enums.OprUsuarioEnum;
import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.R;
import com.imera.censo.Services.PostCenso;

import java.util.ArrayList;


public class OprUsuarioDAO {
    BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
    SQLiteDatabase db = manejador.getReadableDatabase();


    public void ActualizaCampoDirecto (String strCampo, String strValor, String strIdWere){
/*        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();*/

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues actualizaRegistro = new ContentValues();
            try {
                actualizaRegistro.put(strCampo, strValor);
                String strWhere = "id= " +strIdWere;

                db.update(App.getContext().getString(R.string.opr_usuarios), actualizaRegistro, strWhere, null);

                Log.d("Info","UPDATE OPR_USUARIOS SET "+strCampo+" = "+strValor+" WHERE id="+strIdWere);
            }catch ( Exception e){
                Log.e("DEBUGG(error)->",e.toString());
                Toast.makeText(App.getContext(),"Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
            db.close();

        }
    }

    public OPR_USUARIOS OprOprUsuarioGetById(String id){
/*        BDManager usdbh = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = usdbh.getReadableDatabase();*/
        OPR_USUARIOS opr_usuarios = new OPR_USUARIOS();
        //Si hemos abierto correctamente la base de datos
        String[] strArgs = new String[] {id};

        if (db != null) {

            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.opr_usuarios)+" WHERE id = ?;", strArgs);
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst()) {

                opr_usuarios = ConvertCursorToOprUsuario(cursor);

            }
            //Cerramos la base de datos
            db.close();
            if (!cursor.isClosed()) cursor.close();
        }

        return opr_usuarios;
    }

    public PostCenso OprOprUsuarioSubirGetById(String id){
/*        BDManager usdbh = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = usdbh.getReadableDatabase();*/
        PostCenso censo = new PostCenso();
        //Si hemos abierto correctamente la base de datos
        String[] strArgs = new String[] {id};

        if (db != null) {

            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.opr_usuarios)+" WHERE id = ?;", strArgs);
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst()) {

                censo = ConvertCursorToOprUsuarioSubir(cursor);

            }
            //Cerramos la base de datos
            db.close();
            if (!cursor.isClosed()) cursor.close();
        }

        return censo;
    }

    public void EliminaPadronDespuesSubir(String id){
   /*     BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();*/

        String strIdMametial = "";
        //Si hemos abierto correctamente la base de datos
        if (db != null) {
    /*        Cursor c = db.rawQuery("SELECT id_mat FROM Opr_Materiales WHERE id_orden='"+strIdOrden+"';", null);
            if (c.moveToFirst()) {
                strIdMametial = c.getString(c.getColumnIndex(OprOrdenTrabajoEnum.id_mat.toString()));
                db.execSQL("DELETE FROM Opr_DetMateriales WHERE id_mat= '"+ strIdMametial+"';");
                db.execSQL("DELETE FROM Opr_Materiales WHERE id_mat= '"+ strIdMametial+"';");
            }*/

            db.execSQL("DELETE FROM "+App.getContext().getString(R.string.opr_usuarios)+" WHERE id = "+id+";");

        }
        db.close();
    }

    @SuppressLint("Range")
    public int GetCantidades(int intIdCarga, String strEstatus){
        int intCantidad = 0;
   /*     BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();*/

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT id_carga, COUNT(id) AS cantidad " +
                    "FROM " +App.getContext().getString(R.string.opr_usuarios)+" " +
                    "WHERE id_carga ="+intIdCarga+" AND id_estatus IN ("+strEstatus+")"+
                    "GROUP BY id_carga;", null);
            if (cursor.moveToFirst()) {
                intCantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
            }
            if(!cursor.isClosed())cursor.close();
            db.close();
        }

        return intCantidad;
    }


    @SuppressLint("Range")
    public ArrayList<OPR_USUARIOS> CensoCapturadoGetList(int idRuta){
        ArrayList<OPR_USUARIOS> listReturn = new ArrayList<OPR_USUARIOS>();

        String[] strArgs = new String[] {String.valueOf(idRuta) ,"102"};

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM "+App.getContext().getString(R.string.opr_usuarios)+" " +
                                            "WHERE id_carga = ? " +
                                            "AND id_estatus = ?;", strArgs);

            OPR_USUARIOS Registro = new OPR_USUARIOS();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    Registro = ConvertCursorToOprUsuario(cursor);
                    listReturn.add( Registro );
                    cursor.moveToNext();
                }
            }

            if(!cursor.isClosed())cursor.close();

            //Cerramos la base de datos
            db.close();
        }//6
        return listReturn;
    }

    @SuppressLint("Range")
    private OPR_USUARIOS ConvertCursorToOprUsuario(Cursor cursor){
        OPR_USUARIOS oprusuario = new OPR_USUARIOS();
        if (cursor != null) {
            oprusuario.setId(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id.toString())));
            oprusuario.setId_padron(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_padron.toString())));
            oprusuario.setId_cuenta(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_cuenta.toString())));
            oprusuario.setId_carga(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_carga.toString())));
            oprusuario.setId_censista(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_censista.toString())));
            oprusuario.setId_servicio(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_servicio.toString())));
            oprusuario.setId_tipousuario(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_tipousuario.toString())));
            oprusuario.setMedidor_ins(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.medidor_ins.toString())));
            oprusuario.setLectura_med_ins(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.lectura_med_ins.toString())));
            oprusuario.setId_marca_ins(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_marca_ins.toString())));
            oprusuario.setId_modelo_ins(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_modelo_ins.toString())));
            oprusuario.setId_diametro(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_diametro.toString())));
            oprusuario.setId_materialtoma(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_materialtoma.toString())));
            oprusuario.setId_tipotoma(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_tipotoma.toString())));
            oprusuario.setId_tipocasa(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_tipocasa.toString())));
            oprusuario.setId_condicionpredio(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_condicionpredio.toString())));
            oprusuario.setId_tipoglobo(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_tipoglobo.toString())));
            oprusuario.setId_mat_banqueta(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_mat_banqueta.toString())));
            oprusuario.setId_mat_calle(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_mat_calle.toString())));
            oprusuario.setRegistro_banqueta(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.registro_banqueta.toString())));
            oprusuario.setId_anomalia(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_anomalia.toString())));
            oprusuario.setFac_tecnica(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.fac_tecnica.toString())));
            oprusuario.setTinaco(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.tinaco.toString())));
            oprusuario.setCisterna(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.cisterna.toString())));
            oprusuario.setAlberca(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.alberca.toString())));
            oprusuario.setPozo(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.pozo.toString())));
            oprusuario.setFoto_predio(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.foto_predio.toString())));
            oprusuario.setFoto_toma(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.foto_toma.toString())));
            oprusuario.setNombre_comercial(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.nombre_comercial.toString())));
            oprusuario.setObserva_a(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.observa_a.toString())));
            oprusuario.setId_giro(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_giro.toString())));
            oprusuario.setId_calle_ppal(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_calle_ppal.toString())));
            oprusuario.setId_calle_lat1(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_calle_lat1.toString())));
            oprusuario.setId_calle_lat2(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_calle_lat2.toString())));
            oprusuario.setLatitud(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.latitud.toString())));
            oprusuario.setLongitud(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.longitud.toString())));
            oprusuario.setId_estatus(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_estatus.toString())));
            oprusuario.setId_identificacion(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_identificacion.toString())));
            oprusuario.setId_colonia(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.id_colonia.toString())));
            oprusuario.setDireccion(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.direccion.toString())));
            oprusuario.setNombre(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.nombre.toString())));
            oprusuario.setNum_int(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.num_int.toString())));
            oprusuario.setNum_ext(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.num_ext.toString())));
            oprusuario.setOtro(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.otro.toString())));
            oprusuario.setClaveloc(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.clave_loc.toString())));
        }
        return oprusuario;
    }

    @SuppressLint("Range")
    private PostCenso ConvertCursorToOprUsuarioSubir(Cursor cursor){
        PostCenso oprusuario = new PostCenso();
        if (cursor != null) {
            oprusuario.setId(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id.toString())));
            oprusuario.setId_padron(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_padron.toString())));
            oprusuario.setId_cuenta(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_cuenta.toString())));
            oprusuario.setId_carga(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_carga.toString())));
            oprusuario.setId_censista(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_censista.toString())));
            oprusuario.setId_servicio(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_servicio.toString())));
            oprusuario.setId_tipousuario(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_tipousuario.toString())));
            oprusuario.setMedidor_ins(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.medidor_ins.toString())));
            oprusuario.setLectura_med_ins(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.lectura_med_ins.toString())));
            oprusuario.setId_marca_ins(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_marca_ins.toString())));
            oprusuario.setId_modelo_ins(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_modelo_ins.toString())));
            oprusuario.setId_diametro(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_diametro.toString())));
            oprusuario.setId_materialtoma(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_materialtoma.toString())));
            oprusuario.setId_tipotoma(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_tipotoma.toString())));
            oprusuario.setId_tipocasa(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_tipocasa.toString())));
            oprusuario.setId_condicionpredio(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_condicionpredio.toString())));
            oprusuario.setId_tipoglobo(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_tipoglobo.toString())));
            oprusuario.setId_mat_banqueta(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_mat_banqueta.toString())));
            oprusuario.setId_mat_calle(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_mat_calle.toString())));
            oprusuario.setRegistro_banqueta(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.registro_banqueta.toString())));
            oprusuario.setId_anomalia(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_anomalia.toString())));
            oprusuario.setFac_tecnica(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.fac_tecnica.toString())));
            oprusuario.setTinaco(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.tinaco.toString())));
            oprusuario.setCisterna(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.cisterna.toString())));
            oprusuario.setAlberca(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.alberca.toString())));
            oprusuario.setPozo(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.pozo.toString())));
            oprusuario.setFoto_predio(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.foto_predio.toString())));
            oprusuario.setFoto_toma(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.foto_toma.toString())));
            oprusuario.setNombre_comercial(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.nombre_comercial.toString())));
            oprusuario.setObserva_a(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.observa_a.toString())));
            oprusuario.setId_giro(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_giro.toString())));
            oprusuario.setId_calle_ppal(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_calle_ppal.toString())));
            oprusuario.setId_calle_lat1(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_calle_lat1.toString())));
//            oprusuario.setId_calle_lat2(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_calle_lat2.toString())));
            oprusuario.setLatitud(cursor.getFloat(cursor.getColumnIndex(OprUsuarioEnum.latitud.toString())));
            oprusuario.setLongitud(cursor.getFloat(cursor.getColumnIndex(OprUsuarioEnum.longitud.toString())));
            oprusuario.setId_estatus(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_estatus.toString())));
            oprusuario.setId_identificacion(cursor.getInt(cursor.getColumnIndex(OprUsuarioEnum.id_identificacion.toString())));
            oprusuario.setNum_int(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.num_int.toString())));
            oprusuario.setNum_ext(cursor.getString(cursor.getColumnIndex(OprUsuarioEnum.num_ext.toString())));
        }
        return oprusuario;
    }
}
