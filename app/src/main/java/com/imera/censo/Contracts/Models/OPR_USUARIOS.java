package com.imera.censo.Contracts.Models;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;

import java.util.ArrayList;

public class OPR_USUARIOS implements Parcelable {
    private String id = "";
    private String id_padron = "";
    private String id_cuenta = "";
    private String id_carga = "";
    private String id_censista = "";
    private String id_servicio = "";
    private String id_tipousuario = "";
    private String medidor_ins = "";
    private String lectura_med_ins = "";
    private String id_marca_ins = "";
    private String id_modelo_ins = "";
    private String id_diametro = "";
    private String id_materialtoma = "";
    private String id_tipotoma = "";
    private String id_tipocasa = "";
    private String id_condicionpredio = "";
    private String id_tipoglobo = "";
    private String id_mat_banqueta = "";
    private String id_mat_calle = "";
    private String registro_banqueta = "";
    private String id_anomalia = "";
    private String fac_tecnica = "";
    private String tinaco = "";
    private String cisterna = "";
    private String alberca = "";
    private String pozo = "";
    private String foto_predio = "";
    private String foto_toma = "";
    private String nombre_comercial = "";
    private String observa_a = "";
    private String id_giro = "";
    private String id_calle_ppal = "";
    private String id_calle_lat1 = "";
    private String id_calle_lat2 = "";
    private String latitud = "";
    private String longitud = "";
    private String id_estatus = "";
    private String id_identificacion = "";
    private String id_colonia = "";
    private String direccion = "";
    private String nombre = "";
    private String num_int = "";
    private String num_ext = "";
    private String otro = "";
    private String claveloc = "";
    private boolean medidor = false;

    public OPR_USUARIOS(){}


    protected OPR_USUARIOS(Parcel in) {
        id = in.readString();
        id_padron = in.readString();
        id_cuenta = in.readString();
        id_carga = in.readString();
        id_censista = in.readString();
        id_servicio = in.readString();
        id_tipousuario = in.readString();
        medidor_ins = in.readString();
        lectura_med_ins = in.readString();
        id_marca_ins = in.readString();
        id_modelo_ins = in.readString();
        id_diametro = in.readString();
        id_materialtoma = in.readString();
        id_tipotoma = in.readString();
        id_tipocasa = in.readString();
        id_condicionpredio = in.readString();
        id_tipoglobo = in.readString();
        id_mat_banqueta = in.readString();
        id_mat_calle = in.readString();
        registro_banqueta = in.readString();
        id_anomalia = in.readString();
        fac_tecnica = in.readString();
        tinaco = in.readString();
        cisterna = in.readString();
        alberca = in.readString();
        pozo = in.readString();
        foto_predio = in.readString();
        foto_toma = in.readString();
        nombre_comercial = in.readString();
        observa_a = in.readString();
        id_giro = in.readString();
        id_calle_ppal = in.readString();
        id_calle_lat1 = in.readString();
        id_calle_lat2 = in.readString();
        latitud = in.readString();
        longitud = in.readString();
        id_estatus = in.readString();
        id_identificacion = in.readString();
        id_colonia = in.readString();
        direccion = in.readString();
        nombre = in.readString();
        num_int = in.readString();
        num_ext = in.readString();
        otro = in.readString();
        claveloc = in.readString();
    }

    public static final Creator<OPR_USUARIOS> CREATOR = new Creator<OPR_USUARIOS>() {
        @Override
        public OPR_USUARIOS createFromParcel(Parcel in) {
            return new OPR_USUARIOS(in);
        }

        @Override
        public OPR_USUARIOS[] newArray(int size) {
            return new OPR_USUARIOS[size];
        }
    };

    public String getClaveloc() {
        return claveloc;
    }

    public void setClaveloc(String claveloc) {
        this.claveloc = claveloc;
    }

    public boolean isMedidor() {
        return medidor;
    }

    public void setMedidor(boolean medidor) {
        this.medidor = medidor;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_padron() {
        return id_padron;
    }

    public void setId_padron(String id_padron) {
        this.id_padron = id_padron;
    }

    public String getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(String id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getId_carga() {
        return id_carga;
    }

    public void setId_carga(String id_carga) {
        this.id_carga = id_carga;
    }

    public String getId_censista() {
        return id_censista;
    }

    public void setId_censista(String id_censista) {
        this.id_censista = id_censista;
    }

    public String getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(String id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getId_tipousuario() {
        return id_tipousuario;
    }

    public void setId_tipousuario(String id_tipousuario) {
        this.id_tipousuario = id_tipousuario;
    }

    public String getMedidor_ins() {
        return medidor_ins;
    }

    public void setMedidor_ins(String medidor_ins) {
        this.medidor_ins = medidor_ins;
    }

    public String getLectura_med_ins() {
        return lectura_med_ins;
    }

    public void setLectura_med_ins(String lectura_med_ins) {
        this.lectura_med_ins = lectura_med_ins;
    }

    public String getId_marca_ins() {
        return id_marca_ins;
    }

    public void setId_marca_ins(String id_marca_ins) {
        this.id_marca_ins = id_marca_ins;
    }

    public String getId_modelo_ins() {
        return id_modelo_ins;
    }

    public void setId_modelo_ins(String id_modelo_ins) {
        this.id_modelo_ins = id_modelo_ins;
    }

    public String getId_diametro() {
        return id_diametro;
    }

    public void setId_diametro(String id_diametro) {
        this.id_diametro = id_diametro;
    }

    public String getId_materialtoma() {
        return id_materialtoma;
    }

    public void setId_materialtoma(String id_materialtoma) {
        this.id_materialtoma = id_materialtoma;
    }

    public String getId_tipotoma() {
        return id_tipotoma;
    }

    public void setId_tipotoma(String id_tipotoma) {
        this.id_tipotoma = id_tipotoma;
    }

    public String getId_tipoglobo() {
        return id_tipoglobo;
    }

    public void setId_tipoglobo(String id_tipoglobo) {
        this.id_tipoglobo = id_tipoglobo;
    }

    public String getId_mat_banqueta() {
        return id_mat_banqueta;
    }

    public void setId_mat_banqueta(String id_mat_banqueta) {
        this.id_mat_banqueta = id_mat_banqueta;
    }

    public String getId_mat_calle() {
        return id_mat_calle;
    }

    public void setId_mat_calle(String id_mat_calle) {
        this.id_mat_calle = id_mat_calle;
    }

    public String getRegistro_banqueta() {
        return registro_banqueta;
    }

    public void setRegistro_banqueta(String registro_banqueta) {
        this.registro_banqueta = registro_banqueta;
    }

    public String getId_anomalia() {
        return id_anomalia;
    }

    public void setId_anomalia(String id_anomalia) {
        this.id_anomalia = id_anomalia;
    }

    public String getFac_tecnica() {
        return fac_tecnica;
    }

    public void setFac_tecnica(String fac_tecnica) {
        this.fac_tecnica = fac_tecnica;
    }

    public String getTinaco() {
        return tinaco;
    }

    public void setTinaco(String tinaco) {
        this.tinaco = tinaco;
    }

    public String getCisterna() {
        return cisterna;
    }

    public void setCisterna(String cisterna) {
        this.cisterna = cisterna;
    }

    public String getAlberca() {
        return alberca;
    }

    public void setAlberca(String alberca) {
        this.alberca = alberca;
    }

    public String getPozo() {
        return pozo;
    }

    public void setPozo(String pozo) {
        this.pozo = pozo;
    }

    public String getFoto_predio() {
        return foto_predio;
    }

    public void setFoto_predio(String foto_predio) {
        this.foto_predio = foto_predio;
    }

    public String getFoto_toma() {
        return foto_toma;
    }

    public void setFoto_toma(String foto_toma) {
        this.foto_toma = foto_toma;
    }

    public String getNombre_comercial() {
        return nombre_comercial;
    }

    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }

    public String getObserva_a() {
        return observa_a;
    }

    public void setObserva_a(String observa_a) {
        this.observa_a = observa_a;
    }

    public String getId_giro() {
        return id_giro;
    }

    public void setId_giro(String id_giro) {
        this.id_giro = id_giro;
    }

    public String getId_calle_ppal() {
        return id_calle_ppal;
    }

    public void setId_calle_ppal(String id_calle_ppal) {
        this.id_calle_ppal = id_calle_ppal;
    }

    public String getId_calle_lat1() {
        return id_calle_lat1;
    }

    public void setId_calle_lat1(String id_calle_lat1) {
        this.id_calle_lat1 = id_calle_lat1;
    }

    public String getId_calle_lat2() {
        return id_calle_lat2;
    }

    public void setId_calle_lat2(String id_calle_lat2) {
        this.id_calle_lat2 = id_calle_lat2;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(String id_estatus) {
        this.id_estatus = id_estatus;
    }

    public String getId_identificacion() {
        return id_identificacion;
    }

    public void setId_identificacion(String id_identificacion) {
        this.id_identificacion = id_identificacion;
    }

    public String getId_colonia() {
        return id_colonia;
    }

    public void setId_colonia(String id_colonia) {
        this.id_colonia = id_colonia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNum_int() {
        return num_int;
    }

    public void setNum_int(String num_int) {
        this.num_int = num_int;
    }

    public String getNum_ext() {
        return num_ext;
    }

    public void setNum_ext(String num_ext) {
        this.num_ext = num_ext;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId_tipocasa() {
        return id_tipocasa;
    }

    public void setId_tipocasa(String id_tipocasa) {
        this.id_tipocasa = id_tipocasa;
    }

    public String getId_condicionpredio() {
        return id_condicionpredio;
    }

    public void setId_condicionpredio(String id_condicionpredio) {
        this.id_condicionpredio = id_condicionpredio;
    }



    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(id_padron);
        dest.writeString(id_cuenta);
        dest.writeString(id_carga);
        dest.writeString(id_censista);
        dest.writeString(id_servicio);
        dest.writeString(id_tipousuario);
        dest.writeString(medidor_ins);
        dest.writeString(lectura_med_ins);
        dest.writeString(id_marca_ins);
        dest.writeString(id_modelo_ins);
        dest.writeString(id_diametro);
        dest.writeString(id_materialtoma);
        dest.writeString(id_tipotoma);
        dest.writeString(id_tipocasa);
        dest.writeString(id_condicionpredio);
        dest.writeString(id_tipoglobo);
        dest.writeString(id_mat_banqueta);
        dest.writeString(id_mat_calle);
        dest.writeString(registro_banqueta);
        dest.writeString(id_anomalia);
        dest.writeString(fac_tecnica);
        dest.writeString(tinaco);
        dest.writeString(cisterna);
        dest.writeString(alberca);
        dest.writeString(pozo);
        dest.writeString(foto_predio);
        dest.writeString(foto_toma);
        dest.writeString(nombre_comercial);
        dest.writeString(observa_a);
        dest.writeString(id_giro);
        dest.writeString(id_calle_ppal);
        dest.writeString(id_calle_lat1);
        dest.writeString(id_calle_lat2);
        dest.writeString(latitud);
        dest.writeString(longitud);
        dest.writeString(id_estatus);
        dest.writeString(id_identificacion);
        dest.writeString(id_colonia);
        dest.writeString(direccion);
        dest.writeString(nombre);
        dest.writeString(num_int);
        dest.writeString(num_ext);
        dest.writeString(otro);
        dest.writeString(claveloc);
    }

    public static boolean insertarNormal(OPR_USUARIOS registro){
        BDManager usdbh = new BDManager(App.getContext(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            db.execSQL(getInsertarNormalQuery(registro,true));
            db.close();
        }
        return true;
    }

    private static String getInsertarNormalQuery(OPR_USUARIOS registro,boolean esNuevo){
        String query;

        if(esNuevo){

            String HayMed;

            if(!registro.getMedidor_ins().equals(""))
                HayMed = "1";
            else
                HayMed = "0";

            query = "INSERT INTO OPR_USUARIOS (id_padron,id_cuenta,id_carga,id_censista,medidor_ins,id_calle_ppal,id_estatus,id_colonia,direccion,nombre,clave_loc,tmedidor)VALUES(" +
                    "'"+registro.getId_padron()+"'," +
                    "'"+registro.getId_cuenta()+"'," +
                    "'"+registro.getId_carga()+"'," +
                    "'"+registro.getId_censista()+"',"+
                    "'"+registro.getMedidor_ins()+"'," +
                    "'"+registro.getId_calle_ppal()+"'," +
                    "'"+registro.getId_estatus()+"',"+
                    "'"+registro.getId_colonia()+"'," +
                    "'"+registro.getDireccion()+"'," +
                    "'"+registro.getNombre()+"',"+
                    "'"+registro.getClaveloc()+"',"+
                    ""+HayMed+
                    ");";
        }else{

            String HayMed;

            if(registro.isMedidor())
                HayMed = "1";
            else
                HayMed = "0";

            query = "INSERT INTO OPR_USUARIOS (id_padron,id_cuenta,id_carga,id_censista,id_servicio,id_tipousuario,medidor_ins,lectura_med_ins,id_marca_ins,id_modelo_ins,"+
                    "id_diametro,id_materialtoma,id_tipotoma,id_tipocasa,id_condicionpredioid_tipoglobo,id_mat_banqueta,id_mat_calle,registro_banqueta,id_anomalia,fac_tecnica,tinaco,"+
                    "cisterna,alberca,pozo,foto_predio,foto_toma,nombre_comercial,observa_a,id_giro,id_calle_ppal,"+
                    "latitud,longitud,id_estatus,id_identificacion,id_colonia,direccion,nombre,otro,tmedidor,clave_loc) VALUES(" +
                    ""+registro.getId_padron()+","+
                    ""+registro.getId_cuenta()+","+
                    ""+registro.getId_carga()+","+
                    "'"+registro.getId_censista()+"',"+
                    "'"+registro.getId_servicio()+"',"+//s
                    "'"+registro.getId_tipousuario()+"',"+//s
                    "'"+registro.getMedidor_ins()+"',"+//s
                    "'"+registro.getLectura_med_ins()+"',"+//s
                    "'"+registro.getId_marca_ins()+"',"+
                    "'"+registro.getId_modelo_ins()+"',"+
                    "'"+registro.getId_diametro()+"',"+//s
                    "'"+registro.getId_materialtoma()+"',"+//s
                    "'"+registro.getId_tipotoma()+"',"+//s
                    "'"+registro.getId_tipocasa()+"',"+//s
                    "'"+registro.getId_condicionpredio()+"',"+//s
                    "'"+registro.getId_tipoglobo()+"',"+//s
                    "'"+registro.getId_mat_banqueta()+"',"+//s
                    "'"+registro.getId_mat_calle()+"',"+//s
                    "'"+registro.getRegistro_banqueta()+"',"+//s
                    "'"+registro.getId_anomalia()+"',"+//s
                    "'"+registro.getFac_tecnica()+"',"+//s
                    ""+registro.getTinaco() +","+
                    ""+registro.getCisterna()+"," +
                    ""+registro.getAlberca()+","+
                    ""+registro.getPozo()+","+
                    "'"+registro.getFoto_predio()+"',"+//s
                    "'"+registro.getFoto_toma()+"',"+//s
                    "'"+registro.getNombre_comercial()+"',"+//s
                    "'"+registro.getObserva_a()+"',"+//s
                    "'"+registro.getId_giro()+"',"+//s
                    "'"+registro.getId_calle_ppal()+"',"+//s
                    "'"+registro.getLatitud()+"',"+//s
                    "'"+registro.getLongitud()+"',"+//S
                    "'"+registro.getId_estatus()+"',"+//s
                    "'"+registro.getId_identificacion()+"'," +//s
                    "'"+registro.getId_colonia()+"',"+//s
                    "'"+registro.getDireccion()+"'," +//s
                    "'"+registro.getNombre()+"'," +//s
                    "'"+registro.getOtro()+"',"+//s
                    ""+HayMed+","+
                    "'"+registro.getClaveloc()+"'"+
                    ");";
        }
        return query;
    }

    public static void resetearRutas(){
        ArrayList<OPR_USUARIOS> respaldoInformacion = new ArrayList<>();
        BDManager usdbh = new BDManager(App.getContext(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT id FROM OPR_USUARIOS;", null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    Log.e("Id-->",c.getString(0));
                    OPR_USUARIOS nuevo = OPR_USUARIOS.cargarRegistro(c.getString(0));
                    respaldoInformacion.add(nuevo);

                } while (c.moveToNext());
            }

            db.execSQL("DROP TABLE IF EXISTS OPR_USUARIOS");
            db.execSQL(BDManager.sqlCreateOprUsuarios);

            for(int i =0; i<respaldoInformacion.size();i++){
                if(respaldoInformacion.get(i).getId_estatus().equals("101") && respaldoInformacion.get(i).getId_identificacion()==null)
                    db.execSQL(getInsertarNormalQuery(respaldoInformacion.get(i),true));
                else if(respaldoInformacion.get(i).getId_estatus().equals("102") && respaldoInformacion.get(i).getId_identificacion().equals("1")){
                    db.execSQL(getInsertarNormalQuery(respaldoInformacion.get(i),false));
                }
                else if(respaldoInformacion.get(i).getId_estatus().equals("102") && respaldoInformacion.get(i).getId_identificacion().equals("2")){
                    db.execSQL(getInsertarClandestinoQuery(respaldoInformacion.get(i),false));
                }
                else if(respaldoInformacion.get(i).getId_estatus().equals("102") && respaldoInformacion.get(i).getId_identificacion().equals("3")){
                    db.execSQL(getInsertarFactibleQuery(respaldoInformacion.get(i),false));
                }
            }

            //Cerramos la base de datos
            db.close();
            if (!c.isClosed())c.close();
            respaldoInformacion.clear();
        }
    }

    public static OPR_USUARIOS cargarRegistro(String Id){
        BDManager usdbh = new BDManager(App.getContext(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        OPR_USUARIOS Registro = new OPR_USUARIOS();
        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM OPR_USUARIOS WHERE id= "+Id+";", null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                Registro.setId(c.getString(0));
                Registro.setId_padron(c.getString(1));
                Registro.setId_cuenta(c.getString(2));
                Registro.setId_carga(c.getString(3));
                Registro.setId_censista(c.getString(4));
                Registro.setId_servicio(c.getString(5));
                Registro.setId_tipousuario(c.getString(6));
                Registro.setMedidor_ins(c.getString(7));
                Registro.setLectura_med_ins(c.getString(8));
                Registro.setId_marca_ins(c.getString(9));
                Registro.setId_modelo_ins(c.getString(10));
                Registro.setId_diametro(c.getString(11));
                Registro.setId_materialtoma(c.getString(12));
                Registro.setId_tipotoma(c.getString(13));
                Registro.setId_tipocasa(c.getString(14));
                Registro.setId_condicionpredio(c.getString(15));
                Registro.setId_tipoglobo(c.getString(16));
                Registro.setId_mat_banqueta(c.getString(17));
                Registro.setId_mat_calle(c.getString(18));
                Registro.setRegistro_banqueta(c.getString(19));
                Registro.setId_anomalia(c.getString(20));
                Registro.setFac_tecnica(c.getString(21));
                Registro.setTinaco(c.getString(22));
                Registro.setCisterna(c.getString(23));
                Registro.setAlberca(c.getString(24));
                Registro.setPozo(c.getString(25));
                Registro.setFoto_predio(c.getString(26));
                Registro.setFoto_toma(c.getString(27));
                Registro.setNombre_comercial(c.getString(28));
                Registro.setObserva_a(c.getString(29));
                Registro.setId_giro(c.getString(30));
                Registro.setId_calle_ppal(c.getString(31));
                Registro.setId_calle_lat1(c.getString(32));
                Registro.setId_calle_lat2(c.getString(33));
                Registro.setLatitud(c.getString(34));
                Registro.setLongitud(c.getString(35));
                Registro.setId_estatus(c.getString(36));
                Registro.setId_identificacion(c.getString(37));
                Registro.setId_colonia(c.getString(38));
                Registro.setDireccion(c.getString(39));
                Registro.setNombre(c.getString(40));
                Registro.setNum_int(c.getString(41));
                Registro.setNum_ext(c.getString(42));
                Registro.setOtro(c.getString(43));

                if(c.getString(44)!=null){
                    if(c.getString(44).equals("1"))
                        Registro.setMedidor(true);
                    else
                        Registro.setMedidor(false);
                }else
                    Registro.setMedidor(false);

                Registro.setClaveloc(c.getString(45));
            }
            //Cerramos la base de datos
            db.close();
            if (!c.isClosed()) c.close();
        }
        return Registro;
    }

    private static String getInsertarClandestinoQuery(OPR_USUARIOS registro,boolean esNuevo){
        String query;

        query = "INSERT INTO OPR_USUARIOS (id_padron,id_cuenta,id_carga,id_servicio,id_tipousuario," +
                "id_diametro,id_materialtoma,id_tipotoma,id_tipocasa,id_condicionpredio,id_tipoglobo,id_mat_banqueta,id_mat_calle,registro_banqueta,id_anomalia,fac_tecnica,tinaco," +
                "cisterna,alberca,pozo,foto_predio,foto_toma,nombre_comercial,observa_a,id_giro,id_calle_ppal,id_calle_lat1," +
                "id_calle_lat2,latitud,longitud,id_estatus,id_identificacion,id_colonia,direccion,nombre,num_int,num_ext,otro,clave_loc) VALUES(" +
                "" + registro.getId_padron() + ",";

        if(esNuevo) query += "0,";else query+=registro.getId_cuenta()+","; query+=

                "" + registro.getId_carga() + "," +
                        "" + registro.getId_servicio() + "," +
                        "" + registro.getId_tipousuario() + "," +
                        "" + registro.getId_diametro() + "," +
                        "" + registro.getId_materialtoma() + "," +
                        "" + registro.getId_tipotoma() + "," +
                        "" + registro.getId_tipocasa() + "," +
                        "" + registro.getId_condicionpredio() + "," +
                        "" + registro.getId_tipoglobo() + "," +
                        "" + registro.getId_mat_banqueta() + "," +
                        "" + registro.getId_mat_calle() + "," +
                        "" + registro.getRegistro_banqueta() + "," +
                        "" + registro.getId_anomalia() + "," +
                        "'" + registro.getFac_tecnica() + "'," +
                        "" + registro.getTinaco() + "," +
                        "" + registro.getCisterna() + "," +
                        "" + registro.getAlberca() + "," +
                        "" + registro.getPozo() + "," +
                        "'" + registro.getFoto_predio() + "'," +
                        "'" + registro.getFoto_toma() + "'," +
                        "'" + registro.getNombre_comercial() + "'," +
                        "'" + registro.getObserva_a() + "'," +
                        "" + registro.getId_giro() + "," +
                        "" + registro.getId_calle_ppal() + "," +
                        "" + registro.getId_calle_lat1() + "," +
                        "'" + registro.getId_calle_lat2() + "'," +
                        "" + registro.getLatitud() + "," +
                        "" + registro.getLongitud() + "," +
                        "" + registro.getId_estatus() + "," +
                        "" + registro.getId_identificacion() + "," +
                        "" + registro.getId_colonia() + ",";

        if(esNuevo)
            query += "'" + Catalogo.GetText("Cat_Colonias", registro.getId_colonia(), "id_colonia") + " " + Catalogo.GetText("Cat_Calles", registro.getId_calle_ppal(), "id_calle") + " #" + registro.getNum_ext() + "',";
        else
            query += "'"+registro.getDireccion()+"',";  query+=

                "'" + registro.getNombre() + "'," +
                        "'" + registro.getNum_int() + "'," +
                        "'" + registro.getNum_ext() + "'," +
                        "" + registro.getOtro() + "," +
                        "'" + registro.getClaveloc() + "'" +
                        ");";

        return query;
    }

    private static void restaurarInfoDeHasta(ArrayList<OPR_USUARIOS> respaldoInformacion,int inicio, int fin){
        BDManager usdbh = new BDManager(App.getContext(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            for(int i =inicio; i<fin;i++){
                if(respaldoInformacion.get(i).getId_estatus().equals("101") && respaldoInformacion.get(i).getId_identificacion()==null)//Normal sin censar
                    db.execSQL(getInsertarNormalQuery(respaldoInformacion.get(i),true));
                else if(respaldoInformacion.get(i).getId_estatus().equals("102") && respaldoInformacion.get(i).getId_identificacion().equals("1")){//Normal censado
                    db.execSQL(getInsertarNormalQuery(respaldoInformacion.get(i),false));
                }
                else if(respaldoInformacion.get(i).getId_estatus().equals("102") && respaldoInformacion.get(i).getId_identificacion().equals("2")){//Clandestino
                    db.execSQL(getInsertarClandestinoQuery(respaldoInformacion.get(i),false));
                }
                else if(respaldoInformacion.get(i).getId_estatus().equals("102") && respaldoInformacion.get(i).getId_identificacion().equals("3")){//Factible
                    db.execSQL(getInsertarFactibleQuery(respaldoInformacion.get(i),false));
                }
            }
            //Cerramos la base de datos
            db.close();
            //respaldoInformacion.clear();
        }
    }


    public static boolean insertarRegistro(OPR_USUARIOS registro){
        ArrayList<OPR_USUARIOS> respaldoInformacion = new ArrayList<>();

        BDManager usdbh = new BDManager(App.getContext(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            //Guardamos todos los registros en memoria
            Cursor respaldar = db.rawQuery("SELECT id FROM OPR_USUARIOS;", null);
            if (respaldar.moveToFirst()) {
                do {
                    OPR_USUARIOS nuevo = OPR_USUARIOS.cargarRegistro(respaldar.getString(0));
                    respaldoInformacion.add(nuevo);

                } while (respaldar.moveToNext());
            }

            db.execSQL("DROP TABLE IF EXISTS OPR_USUARIOS");
            db.execSQL(BDManager.sqlCreateOprUsuarios);

            OPR_USUARIOS.restaurarInfoDeHasta(respaldoInformacion,0,Integer.parseInt(registro.getId())-1);

            /* unicos dos casos en los que se inserta, cuando es un nuevo factible o cuando es un
             * nuevo clandestino
             */
            if(registro.getId_identificacion().equals("2")){//Clandestino

                /** TODO
                 *  caso particular que no funciona bien con restaurarInfoDeHasta
                 */
                registro.setId_estatus("102");
                db.execSQL(getInsertarClandestinoQuery(registro,true));

            }else if(registro.getId_identificacion().equals("3")){

                registro.setId_estatus("102");
                db.execSQL(getInsertarFactibleQuery(registro,true));
            }

            OPR_USUARIOS.restaurarInfoDeHasta(respaldoInformacion,Integer.parseInt(registro.getId())-1, respaldoInformacion.size());
            respaldoInformacion.clear();

            //Cerramos la base de datos
            db.close();
            if(!respaldar.isClosed())respaldar.close();
        }
        return true;
    }


    private static String getInsertarFactibleQuery(OPR_USUARIOS registro,boolean esNuevo){
        String query;

        query = "INSERT INTO OPR_USUARIOS (id_padron,id_cuenta,id_carga,id_mat_banqueta,id_mat_calle,registro_banqueta,fac_tecnica,foto_predio,observa_a,id_calle_ppal,id_calle_lat1,"+
                "id_calle_lat2,latitud,longitud,id_estatus,id_identificacion,id_colonia,direccion,nombre,num_int,num_ext,clave_loc) VALUES(" +
                ""+registro.getId_padron()+",";

        if(esNuevo) query+="0,"; else query+="" +registro.getId_cuenta()+","; query+=

                ""+registro.getId_carga()+","+
                        ""+registro.getId_mat_banqueta()+","+
                        ""+registro.getId_mat_calle()+","+
                        ""+registro.getRegistro_banqueta()+","+
                        "'"+registro.getFac_tecnica()+"'," +//no se como se me paso
                        "'"+registro.getFoto_predio()+"',"+
                        "'"+registro.getObserva_a()+"',"+
                        ""+registro.getId_calle_ppal()+","+
                        ""+registro.getId_calle_lat1()+","+
                        "'"+registro.getId_calle_lat2()+"',"+
                        ""+registro.getLatitud()+","+
                        ""+registro.getLongitud()+","+
                        ""+registro.getId_estatus()+","+
                        ""+registro.getId_identificacion()+"," +
                        ""+registro.getId_colonia()+",";

        if(esNuevo)
            query+= "'" + Catalogo.GetText("Cat_Colonias", registro.getId_colonia(), "id_colonia") + " " + Catalogo.GetText("Cat_Calles", registro.getId_calle_ppal(), "id_calle") + " #" + registro.getNum_ext() + "',";
        else
            query += "'"+registro.getDireccion()+"',";
        query+=

                "'"+registro.getNombre()+"'," +
                        "'"+registro.getNum_int()+"',"+
                        "'"+registro.getNum_ext()+"',"+
                        "'"+registro.getClaveloc()+"'"+
                        ");";

        return query;

    }

    public static boolean actualizarRegistro(OPR_USUARIOS registro){
        BDManager usdbh = new BDManager(App.getContext(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            registro.setId_estatus("102");
            switch (registro.getId_identificacion()){
                case "1":
                    db.execSQL(getUpdateNormalQuery(registro));
                    break;
                case "2":
                    db.execSQL(getUpdateClandestinoQuery(registro));
                    break;
                case "3":
                    db.execSQL(getUpdateFactibleQuery(registro));
                    break;
            }

            db.close();
        }
        return true;
    }

    private static String getUpdateNormalQuery(OPR_USUARIOS registro){
        String query;

        String haymedidor = "";
        String haymedidorb = "0";

        if(registro.isMedidor()){
            haymedidor = ""+
                    "id_marca_ins = '"+registro.getId_marca_ins()+"',"+
                    "id_modelo_ins = '"+registro.getId_modelo_ins()+"',";
            haymedidorb="1";
        }else{
            haymedidor = ""+
                    "id_marca_ins = null,"+
                    "id_modelo_ins = null,";
        }

        query = "UPDATE OPR_USUARIOS " +
                "SET id_padron = "+registro.getId_padron()+","+
                "id_servicio = "+registro.getId_servicio()+","+
                "id_tipousuario = "+registro.getId_tipousuario()+","+
                "id_diametro = "+registro.getId_diametro()+","+
                "id_materialtoma = "+registro.getId_materialtoma()+","+
                "id_tipotoma = "+registro.getId_tipotoma()+","+
                "id_tipocasa = "+registro.getId_tipocasa()+","+
                "id_condicionpredio = "+registro.getId_condicionpredio()+","+
                "id_tipoglobo = "+registro.getId_tipoglobo()+","+
                "medidor_ins = '"+registro.getMedidor_ins()+"',"+
                "lectura_med_ins = '"+registro.getLectura_med_ins()+"',"+
                haymedidor+
                "id_mat_banqueta = "+registro.getId_mat_banqueta()+","+
                "id_mat_calle = "+registro.getId_mat_calle()+","+
                "registro_banqueta = "+registro.getRegistro_banqueta()+","+
                "id_anomalia = "+registro.getId_anomalia()+","+
                "fac_tecnica = '"+registro.getFac_tecnica()+"',"+
                "tinaco = "+registro.getTinaco()+","+
                "cisterna = "+registro.getCisterna()+","+
                "alberca = "+registro.getAlberca()+","+
                "pozo = "+registro.getPozo()+","+
                "foto_predio = '"+registro.getFoto_predio()+"',"+
                "foto_toma = '"+registro.getFoto_toma()+"',"+
                "nombre_comercial = '"+registro.getNombre_comercial()+"',"+
                "observa_a = '"+registro.getObserva_a()+"',"+
                "id_giro = "+registro.getId_giro()+","+
                "id_identificacion = "+registro.getId_identificacion()+"," +
                "latitud = "+registro.getLatitud()+","+
                "longitud = "+registro.getLongitud()+","+
                "id_estatus = "+registro.getId_estatus()+","+
                "direccion = '"+registro.getDireccion()+"'," +
                "nombre = '"+registro.getNombre()+"'," +
                "otro = "+registro.getOtro()+"," +
                "tmedidor = "+haymedidorb+"," +
                "clave_loc = '"+ registro.getClaveloc()+"' "+
                " WHERE id="+registro.getId();

        return query;
    }

    private static String getUpdateClandestinoQuery(OPR_USUARIOS registro){
        String query;

        query = "UPDATE OPR_USUARIOS " +
                "SET id_servicio = "+registro.getId_servicio()+","+
                "id_tipousuario = "+registro.getId_tipousuario()+","+
                "id_diametro = "+registro.getId_diametro()+","+
                "id_materialtoma = "+registro.getId_materialtoma()+","+
                "id_tipotoma = "+registro.getId_tipotoma()+","+
                "id_tipocasa = "+registro.getId_tipocasa()+","+
                "id_condicionpredio = "+registro.getId_condicionpredio()+","+
                "id_tipoglobo = "+registro.getId_tipoglobo()+","+
                "id_mat_banqueta = "+registro.getId_mat_banqueta()+","+
                "id_mat_calle = "+registro.getId_mat_calle()+","+
                "registro_banqueta = "+registro.getRegistro_banqueta()+","+
                "id_anomalia = "+registro.getId_anomalia()+","+
                "fac_tecnica = '"+registro.getFac_tecnica()+"',"+
                "tinaco = "+registro.getTinaco()+","+
                "cisterna = "+registro.getCisterna()+","+
                "alberca = "+registro.getAlberca()+","+
                "pozo = "+registro.getPozo()+","+
                "foto_predio = '"+registro.getFoto_predio()+"',"+
                "foto_toma = '"+registro.getFoto_toma()+"',"+
                "nombre_comercial = '"+registro.getNombre_comercial()+"',"+
                "observa_a = '"+registro.getObserva_a()+"',"+
                "id_giro = "+registro.getId_giro()+","+
                "id_calle_ppal = "+registro.getId_calle_ppal()+","+
                "id_calle_lat1 = "+registro.getId_calle_lat1()+","+
                "id_calle_lat2 = '"+registro.getId_calle_lat2()+"',"+
                "latitud = "+registro.getLatitud()+","+
                "longitud = "+registro.getLongitud()+","+
                "id_estatus = "+registro.getId_estatus()+","+
                "id_identificacion = "+registro.getId_identificacion()+"," +
                "id_colonia = "+ registro.getId_colonia()+","+
                "nombre = '"+registro.getNombre()+"'," +
                "num_int = '"+registro.getNum_int()+"',"+
                "num_ext = '"+registro.getNum_ext()+"',"+
                "otro = "+registro.getOtro()+","+
                "clave_loc = '"+ registro.getClaveloc()+"' "+
                " WHERE id="+registro.getId();
        return query;
    }

    private static String getUpdateFactibleQuery(OPR_USUARIOS registro){
        String query;

        query = "UPDATE OPR_USUARIOS " +
                "SET id_mat_banqueta = "+registro.getId_mat_banqueta()+","+
                "id_mat_calle = "+registro.getId_mat_calle()+","+
                "registro_banqueta = "+registro.getRegistro_banqueta()+","+
                "fac_tecnica = '"+registro.getFac_tecnica()+"',"+
                "foto_predio = '"+registro.getFoto_predio()+"',"+
                "observa_a = '"+registro.getObserva_a()+"',"+
                "id_calle_ppal = "+registro.getId_calle_ppal()+","+
                "id_calle_lat1 = "+registro.getId_calle_lat1()+","+
                "id_calle_lat2 = '"+registro.getId_calle_lat2()+"',"+
                "latitud = "+registro.getLatitud()+","+
                "longitud = "+registro.getLongitud()+","+
                "id_estatus = "+registro.getId_estatus()+","+
                "id_identificacion = "+registro.getId_identificacion()+"," +
                "id_colonia = "+registro.getId_colonia()+","+
                "nombre = '"+registro.getNombre()+"'," +
                "num_int = '"+registro.getNum_int()+"',"+
                "num_ext = '"+registro.getNum_ext()+"',"+
                "clave_loc = '"+ registro.getClaveloc()+"' "+
                " WHERE id="+registro.getId();

        return query;
    }



}
