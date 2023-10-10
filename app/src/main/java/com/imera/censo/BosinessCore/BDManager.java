package com.imera.censo.BosinessCore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BDManager extends SQLiteOpenHelper {

    public BDManager(Context contexto, String nombre,
                     CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreateAnomailas = "CREATE TABLE Cat_Anomalias (id_anomalia integer primary key ,descripcion text not null);";
    String sqlCreateColonias = "CREATE TABLE Cat_Colonias (id_colonia integer primary key ,descripcion text not null);";
    String sqlCreateDiametros = "CREATE TABLE Cat_Diametros (id_diametro integer primary key ,descripcion text not null);";
    String sqlCreateMarcas = "CREATE TABLE Cat_Marcas (id_marca integer primary key ,descripcion text not null);";
    String sqlCreateMateriales = "CREATE TABLE Cat_Materiales (id_material integer primary key ,descripcion text not null);";

    String sqlCreateMaterialToma = "CREATE TABLE Cat_MaterialToma (id_materialtoma integer primary key ,descripcion text not null);";
    String sqlCreateModelos = "CREATE TABLE Cat_Modelos (id_modelo integer primary key ,descripcion text not null);";
    String sqlCreateServicios = "CREATE TABLE Cat_Servicios (id_Servicio integer primary key ,descripcion text not null);";
    String sqlCreateTiposGlobo = "CREATE TABLE Cat_TiposGlobo (id_tipoglobo integer primary key ,descripcion text not null);";
    String sqlCreateTiposToma = "CREATE TABLE Cat_TiposToma (id_TipoToma integer primary key ,descripcion text not null);";

    String sqlCreateTiposUsuario = "CREATE TABLE Cat_TiposUsuario (id_tipousuario integer primary key ,descripcion text not null);";
    String sqlCreateCalles = "CREATE TABLE Cat_Calles (id_calle integer not null ,id_colonia integer not null, descripcion text not null);";
    String sqlCreateGiros = "CREATE TABLE Cat_Giros (id_giro integer primary key , descripcion text not null);";
    String sqlCreateCondicionPredio = "CREATE TABLE Cat_CondicionPredio (id_condicionpredio integer primary key , descripcion text not null);";
    String sqlCreateTipoCasa = "CREATE TABLE Cat_TipoCasa (id_tipocasa integer primary key , descripcion text not null);";

    public static String sqlCreateCencistas = "CREATE TABLE Cat_Censistas (id_Personal integer primary key, nombre text not null, estado_activo integer default 0);";
    String sqlCreaCfgConfig = "CREATE TABLE Cfg_Parametros (id integer primary key,parametro text not null,valor text not null);";
    public static String sqlCreateOprUsuarios = "Create TABLE IF NOT EXISTS OPR_USUARIOS ("+
            "id integer primary key,"+// primary key
            "id_padron integer null,"+
            "id_cuenta integer null,"+// null
            "id_carga integer null,"+
            "id_censista integer null,"+
            "id_servicio integer null,"+
            "id_tipousuario integer null,"+
            "medidor_ins text null,"+
            "lectura_med_ins integer null,"+
            "id_marca_ins integer null,"+
            "id_modelo_ins integer null,"+
            "id_diametro integer null,"+
            "id_materialtoma integer null,"+
            "id_tipotoma integer null,"+
            "id_tipocasa integer null,"+
            "id_condicionpredio integer null,"+
            "id_tipoglobo integer null,"+
            "id_mat_banqueta integer null,"+
            "id_mat_calle integer null,"+
            "registro_banqueta integer null,"+
            "id_anomalia integer null,"+
            "fac_tecnica text null,"+
            "tinaco integer null,"+
            "cisterna integer null,"+
            "alberca integer null,"+
            "pozo integer null,"+
            "foto_predio text null,"+
            "foto_toma text null,"+
            "nombre_comercial text null,"+
            "observa_a text null,"+
            "id_giro integer null,"+
            "id_calle_ppal integer null,"+
            "id_calle_lat1 integer null,"+
            "id_calle_lat2 integer null,"+ //Nuevo
            "latitud integer null,"+
            "longitud integer null,"+
            "id_estatus integer null,"+
            "id_identificacion integer null," +
            "id_colonia integer null,"+
            "direccion text null," +
            "nombre text null," +
            "num_int text null,"+//nuevo
            "num_ext integer null,"+//nuevo
            "otro integer null,"+//nuevo
            "tmedidor integer null,"+//nuevo 2
            "clave_loc text null"+//nuevo 2
            //"PRIMARY KEY (id,id_cuenta)"+
            ");";

    String sqlCreateCatalogosActualiza = "CREATE TABLE Catalogos_Actualizacion (id integer primary key,catalogo text not null,fecha text not null );";

//    String sqlCreateSysUsuarios = "CREATE TABLE Usuarios_Password(id integer primary key,usuario text not null,contrasena text not null);";

    String sqlCreaSysUsuarios = "CREATE TABLE Sys_Usuarios (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
            ",id_usuario text not null" +
            ",nombre text not null" +
            ",usuario text not null" +
            ",imagen text null);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateAnomailas);
        db.execSQL(sqlCreateColonias);
        db.execSQL(sqlCreateDiametros);
        db.execSQL(sqlCreateMarcas);

        db.execSQL(sqlCreateMateriales);
        db.execSQL(sqlCreateMaterialToma);
        db.execSQL(sqlCreateModelos);
        db.execSQL(sqlCreateServicios);

        db.execSQL(sqlCreateTiposGlobo);
        db.execSQL(sqlCreateTiposToma);
        db.execSQL(sqlCreateTiposUsuario);
        db.execSQL(sqlCreateCalles);
        db.execSQL(sqlCreateGiros);

        db.execSQL(sqlCreateCondicionPredio);
        db.execSQL(sqlCreateTipoCasa);

        db.execSQL(sqlCreateCencistas);
        db.execSQL(sqlCreateOprUsuarios);
        db.execSQL(sqlCreateCatalogosActualiza);

        db.execSQL(sqlCreaSysUsuarios);
        db.execSQL(sqlCreaCfgConfig);

        InsertaValoresDefault(db);
    }

    private void InsertaValoresDefault(SQLiteDatabase db){
//        db.execSQL("INSERT INTO Usuarios_Password(usuario,contrasena) VALUES ('Admin','Admin');");
        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('DIRECION_WCF','http://movilesarm.azurewebsites.net/ServiciosMoviles.svc/');");
//        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('DIRECION_WCF','https://armappordenes.azurewebsites.net/ServiciosMoviles.svc/');");
        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('MUESTRA_MENU_LATERAL','0');");
        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('CAPTURA_CENSO_ONLINE','0');");
        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('PERMITE_DESCARGAR_CENSO','0');");
        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('PERMITE_SUBIR_CENSO','0');");
//        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('PERMITE_CANCELAR_ORDEN','0');");
        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('PERMITE_ELIMINAR','0');");
        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('DESCARGAR_ONLINE','0');");
        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('PERMITE_DESCARGAR_CATALOGOS','0');");
        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('AUTOLOCALIZACION','1');");
//        db.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('LISTADO_ORDENES_AGRUPADAS','0');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//Pendiente de Corregir
        db.execSQL("DROP TABLE IF EXISTS Cat_Anomalias");
        db.execSQL("DROP TABLE IF EXISTS Cat_Colonias");
        db.execSQL("DROP TABLE IF EXISTS Cat_Diametros");
        db.execSQL("DROP TABLE IF EXISTS Cat_Marcas");

        db.execSQL("DROP TABLE IF EXISTS Cat_Materiales");
        db.execSQL("DROP TABLE IF EXISTS Cat_MaterialToma");
        db.execSQL("DROP TABLE IF EXISTS Cat_Modelos");
        db.execSQL("DROP TABLE IF EXISTS Cat_Servicios");

        db.execSQL("DROP TABLE IF EXISTS Cat_TiposGlobo");
        db.execSQL("DROP TABLE IF EXISTS Cat_TiposToma");
        db.execSQL("DROP TABLE IF EXISTS Cat_TiposUsuario");
        db.execSQL("DROP TABLE IF EXISTS Cat_Calles");
        db.execSQL("DROP TABLE IF EXISTS Cat_Giros");
        db.execSQL("DROP TABLE IF EXISTS Cat_CondicionPredio");
        db.execSQL("DROP TABLE IF EXISTS Cat_TipoCasa");

        db.execSQL("DROP TABLE IF EXISTS Cat_Censistas");
        db.execSQL("DROP TABLE IF EXISTS OPR_USUARIOS");
        db.execSQL("DROP TABLE IF EXISTS Catalogos_Actualizacion");
        db.execSQL("DROP TABLE IF EXISTS Usuarios_Password");

        db.execSQL(sqlCreateAnomailas);
        db.execSQL(sqlCreateColonias);
        db.execSQL(sqlCreateDiametros);
        db.execSQL(sqlCreateMarcas);

        db.execSQL(sqlCreateMateriales);
        db.execSQL(sqlCreateMaterialToma);
        db.execSQL(sqlCreateModelos);
        db.execSQL(sqlCreateServicios);

        db.execSQL(sqlCreateTiposGlobo);
        db.execSQL(sqlCreateTiposToma);
        db.execSQL(sqlCreateTiposUsuario);
        db.execSQL(sqlCreateCalles);
        db.execSQL(sqlCreateGiros);

        db.execSQL(sqlCreateCondicionPredio);
        db.execSQL(sqlCreateTipoCasa);

        db.execSQL(sqlCreateCencistas);
        db.execSQL(sqlCreateOprUsuarios);
        db.execSQL(sqlCreateCatalogosActualiza);

        db.execSQL(sqlCreaSysUsuarios);
        db.execSQL(sqlCreaCfgConfig);

        InsertaValoresDefault(db);

    }
}
