package com.imera.censo.ui.Fragments;

import android.app.AlertDialog;
import android.content.AsyncQueryHandler;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Contracts.Models.ItemComboRutas;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Models.Parameter;
import com.imera.censo.R;
import com.imera.censo.Services.ApiManager;
import com.imera.censo.ui.Adapters.ComboRutasAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class SubirRutasFragment extends Fragment {
    private ArrayList<String> AL_Rutas = new ArrayList<String>();
    private ArrayList<String> AL_Rutas_Ids = new ArrayList<String>();

    private ArrayAdapter<String> Adaptador;
    private Button btn_Subir;
    private ImageView img_Subir;
    private ProgressBar pb_Estado;
    private Spinner sp_Rutas;
    private AsyncTask Proceso_Segundo_Plano;
    private Bitmap ImagenPredio = null;
    private Bitmap ImagenToma = null;
//    private int Seleccionado=0;

    private SQLiteDatabase db;
    private View root;
    private SubirRutas subirRutasHttp;
    private ArrayList<ItemComboRutas> listaRutas = new ArrayList<ItemComboRutas>();
    private ComboRutasAdapter comboRutasAdapter;
    private ItemComboRutas rutaSeleccionada = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_subir_rutas, container, false);
        btn_Subir = (Button) root.findViewById(R.id.btn_Subir);
        img_Subir = (ImageView) root.findViewById(R.id.img_upload);
        pb_Estado  = (ProgressBar) root.findViewById(R.id.pb_estado_subida);
        sp_Rutas = (Spinner) root.findViewById(R.id.sp_RutasSubir);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

     /*   Adaptador = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,AL_Rutas);
        sp_Rutas.setAdapter(Adaptador);
        Actualizar_Lista();*/

        comboRutasAdapter = new ComboRutasAdapter(getContext(),listaRutas);
        sp_Rutas.setAdapter(comboRutasAdapter);
        comboRutasAdapter.notifyDataSetChanged();

        sp_Rutas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rutaSeleccionada = listaRutas.get(position);;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_Subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_Subir.setVisibility(View.INVISIBLE);
                pb_Estado.setVisibility(View.VISIBLE);
//                Subir(AL_Rutas_Ids.get(Seleccionado));

                subirRutasHttp = new SubirRutas();
                subirRutasHttp.execute(String.valueOf(rutaSeleccionada.getId_ruta()));

//                new SubirOrdenesOffLine().execute(String.valueOf(Seleccionado));
            }
        });
        ActualizarListaOrdenesCargadas();
    }

    private void ActualizarListaOrdenesCargadas(){
        listaRutas.clear();
        comboRutasAdapter.notifyDataSetChanged();

/*        lblRegSubidos.setVisibility(View.INVISIBLE);
        lblRegNoSubidos.setVisibility(View.INVISIBLE);
        txtContadorRegistrosSubidos.setText("0");
        txtContadorRegistrosSubidos.setVisibility(View.INVISIBLE);
        txtContadorRegistrosNoSubidos.setText("0");
        txtContadorRegistrosNoSubidos.setVisibility(View.INVISIBLE);*/


/*        IOprOrdenTrabajo OTService = new OprOrdenTrabajoDomainObject();
        listOrdenesCargadas = OTService.OrdenesCargadasGetList();
        int intTotalOT = 0;
        if (listOrdenesCargadas.size() > 0){
            img_Subir.setVisibility(View.VISIBLE);

            for (clsItemOrdenTrabajo itemListOrden: listOrdenesCargadas){
                intTotalOT += itemListOrden.getCantidad();
            }
        }else {
            img_Subir.setVisibility(View.INVISIBLE);
        }*/

      /*  listOrdenesCargadas.add(new clsItemListOrden(0,
                "Todas las Ordenes de Trabajo",
                intTotalOT,
                false));*/
        BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
        db = usdbh.getReadableDatabase();

        if (db != null) {//6
            Cursor c = db.rawQuery("SELECT MAX(id_carga),count(id_carga) " +
                                        "FROM OPR_USUARIOS " +
                                        "GROUP BY id_carga " +
                                        "ORDER BY count(id_carga) DESC;", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    listaRutas.add(new ItemComboRutas( c.getInt(0),"",c.getInt(1)));
       /*             AL_Rutas.add("Ruta " + c.getString(0) + ", " +c.getString(1)+" Usuarios");
                    AL_Rutas_Ids.add(c.getString(0));
                    Adaptador.notifyDataSetChanged();*/
                    comboRutasAdapter.notifyDataSetChanged();
                } while (c.moveToNext());
            }

            if(!c.isClosed())c.close();
            //Cerramos la base de datos
            db.close();
        }//6


//        comboRutasAdapter.updateData(listaRutas);

        /*int indexOfElement = listOrdenesCargadas.indexOf(listOrdenesCargadas.size()-1);

        Sp_Ordenes.setSelection(indexOfElement);*/

    }
    public void Actualizar_Lista(){

        AL_Rutas.clear();
        AL_Rutas_Ids.clear();
        Adaptador.notifyDataSetChanged();

        BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
        db = usdbh.getReadableDatabase();

        if (db != null) {//6
            Cursor c = db.rawQuery("SELECT MAX(id_carga),count(id_carga) FROM OPR_USUARIOS GROUP BY id_carga ORDER BY count(id_carga) DESC;", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    AL_Rutas.add("Ruta " + c.getString(0) + ", " +c.getString(1)+" Usuarios");
                    AL_Rutas_Ids.add(c.getString(0));
                    Adaptador.notifyDataSetChanged();
                } while (c.moveToNext());
            }

            if(!c.isClosed())c.close();
            //Cerramos la base de datos
            db.close();
        }//6

    }

/*    public void Subir(final String Id_Carga){//2
        Proceso_Segundo_Plano = new AsyncTask<String, Void, ArrayList<String>>() {//2
            @Override//Sending Data
            protected ArrayList<String> doInBackground(String... params) {//3
                String result;
                ArrayList<String> Errores = new ArrayList<>();
                OPR_USUARIOS Registro;

                try {//4

                    String Url = "http://"+getResources().getString(R.string.GV_DN)+
                            "/IMERA_OS.svc/addCuenta";

                    //String Url = "http://censocomapa.azurewebsites.net/IMERA_OS.svc/addCuenta";
                    //String Url = "http://192.168.0.107/IMERA_OS/IMERA_OS.svc/addCuenta";


                    BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                    db = usdbh.getReadableDatabase();

                    //Si hemos abierto correctamente la base de datos
                    if (db != null) {
                        Cursor c = db.rawQuery("SELECT id FROM OPR_USUARIOS WHERE id_carga= "+Id_Carga+" AND id_estatus=102;", null);
                        //Nos aseguramos de que existe al menos un registro
                        if (c.moveToFirst()) {
                            //Recorremos el cursor hasta que no haya mas registros
                            do {
                                Registro = OPR_USUARIOS.cargarRegistro(c.getString(0));
                                ArrayList<Parameter> Parametros = new ArrayList<>();


                                if(Registro.getId_padron()!=null)
                                    Parametros.add(new Parameter("p1",Registro.getId_padron()));
                                if(Registro.getId_cuenta()!=null)
                                    Parametros.add(new Parameter("p2",Registro.getId_cuenta()));
                                if(Registro.getId_carga()!=null)
                                    Parametros.add(new Parameter("p3",Registro.getId_carga()));
                                if(Registro.getId_censista()!=null)
                                    Parametros.add(new Parameter("p4",Registro.getId_censista()));
                                if(Registro.getId_servicio()!=null)
                                    Parametros.add(new Parameter("p5", Registro.getId_servicio()));

                                if(Registro.getId_tipousuario()!=null)
                                    Parametros.add(new Parameter("p6",Registro.getId_tipousuario()));
                                if(Registro.getMedidor_ins()!=null && !Registro.getMedidor_ins().equals(""))
                                    Parametros.add(new Parameter("p7",Registro.getMedidor_ins()));
                                if(Registro.getLectura_med_ins()!=null && !Registro.getLectura_med_ins().equals(""))

                                    Parametros.add(new Parameter("p8",Registro.getLectura_med_ins()));

                                if(Registro.getId_marca_ins()!=null  && !Registro.getId_marca_ins().equals(""))
                                    Parametros.add(new Parameter("p9",Registro.getId_marca_ins()));
                                if(Registro.getId_modelo_ins()!=null && !Registro.getId_modelo_ins().equals(""))
                                    Parametros.add(new Parameter("p10",Registro.getId_modelo_ins()));

                                if(Registro.getId_diametro()!=null)
                                    Parametros.add(new Parameter("p11",Registro.getId_diametro()));
                                if(Registro.getId_materialtoma()!=null)
                                    Parametros.add(new Parameter("p12",Registro.getId_materialtoma()));
                                if(Registro.getId_tipotoma()!=null)
                                    Parametros.add(new Parameter("p13",Registro.getId_tipotoma()));
                                if(Registro.getId_tipoglobo()!=null)
                                    Parametros.add(new Parameter("p14",Registro.getId_tipoglobo()));
                                if(Registro.getId_mat_banqueta()!=null)
                                    Parametros.add(new Parameter("p15",Registro.getId_mat_banqueta()));

                                if(Registro.getId_mat_calle()!=null)
                                    Parametros.add(new Parameter("p16",Registro.getId_mat_calle()));
                                if(Registro.getRegistro_banqueta()!=null)
                                    Parametros.add(new Parameter("p17",Registro.getRegistro_banqueta()));
                                if(Registro.getId_anomalia()!=null)
                                *//*Parametros.add(new Parameter("p18",Registro.getId_anomalia()));      //Sin Anomalias
                                if(Registro.getFac_tecnica()!=null)*//*
                                    Parametros.add(new Parameter("p19",Registro.getFac_tecnica()));
                                if(Registro.getTinaco()!=null)
                                    Parametros.add(new Parameter("p20",Registro.getTinaco()));

                                if(Registro.getCisterna()!=null)
                                    Parametros.add(new Parameter("p21",Registro.getCisterna()));
                                if(Registro.getAlberca()!=null)
                                    Parametros.add(new Parameter("p22",Registro.getAlberca()));
                                if(Registro.getPozo()!=null)
                                    Parametros.add(new Parameter("p23",Registro.getPozo()));

                                if(Registro.getFoto_predio()!=null)
                                    Parametros.add(new Parameter("p24",Registro.getFoto_predio()));
                                if(Registro.getFoto_toma()!=null)
                                    Parametros.add(new Parameter("p25",Registro.getFoto_toma()));

                                if(Registro.getNombre_comercial()!=null && !Registro.getNombre_comercial().equals(""))
                                    Parametros.add(new Parameter("p26",Registro.getNombre_comercial()));
                                if(Registro.getObserva_a()!=null)
                                    Parametros.add(new Parameter("p27",Registro.getObserva_a()));
                                if(Registro.getId_giro()!=null)
                                    Parametros.add(new Parameter("p28",Registro.getId_giro()));
                                if(Registro.getId_calle_ppal()!=null)
                                    Parametros.add(new Parameter("p29",Registro.getId_calle_ppal()));
                                if(Registro.getId_calle_lat1()!=null)
                                    Parametros.add(new Parameter("p30",Registro.getId_calle_lat1()));

                                if(Registro.getLatitud()!=null)
                                    Parametros.add(new Parameter("p31",Registro.getLatitud()));
                                if(Registro.getLongitud()!=null)
                                    Parametros.add(new Parameter("p32",Registro.getLongitud()));
                                if(Registro.getId_estatus()!=null)
                                    Parametros.add(new Parameter("p33",Registro.getId_estatus()));
                                if(Registro.getId_identificacion()!=null)
                                    Parametros.add(new Parameter("p34",Registro.getId_identificacion()));
                                if(Registro.getOtro()!=null)
                                    Parametros.add(new Parameter("p35",Registro.getOtro()));

                                if(Registro.getNum_ext()!=null)
                                    Parametros.add(new Parameter("p36",Registro.getNum_ext()));
                                if(Registro.getNum_int()!=null)
                                    Parametros.add(new Parameter("p37",Registro.getNum_int()));

                                Parametros.add(new Parameter("p38",Registro.getClaveloc()));
                                Parametros.add(new Parameter("40","c3ns0k3y16"));


                                result = Send_Functions.Send_Get_New(Url, Parametros);

                                Log.e("Debbug(result)-->", result);

                                if(result.equals("Error")){//Error de Conexion
                                    Errores.add(Registro.getId());
                                    Log.e("Error", "De Conexion");
                                }else{
                                    JSONObject obj = new JSONObject(result);
                                    result = obj.get("addCuentaResult").toString();
                                    if(result.equals("1"))
                                    {
                                        //No se eliminara sino es hasta dentro de la configuracion
                                        //db.execSQL("DELETE FROM OPR_USUARIOS WHERE id = " + Registro.getId() + ";");
                                        Log.e("Debugg","Fue eliminado");
                                    }
                                    else{
                                        Errores.add(Registro.getId());//Error de Registro
                                        Log.e("Error","Error WCF");
                                    }
                                }

                            } while (c.moveToNext());
                        }

                        if(!c.isClosed())c.close();

                        //Cerramos la base de datos
                        db.close();
                    }

                    return Errores;

                }catch (Exception e) {//4
                    Log.e("Error", e.toString());
                    Errores.add("Error");
                    return Errores;
                }//4

            }//3
            @Override
            protected void onPostExecute(ArrayList<String> result) {//3
                super.onPostExecute(result);

                img_Subir.setVisibility(View.VISIBLE);
                pb_Estado.setVisibility(View.INVISIBLE);

                if(!result.contains("Error")){
                    if(result.size()!=0){

                        OPR_USUARIOS.resetearRutas();
                        Actualizar_Lista();

                        Toast.makeText(getActivity(),"Hubo "+result.size()+" registros que no pudieron ser subidos (posiblemente repetidos)",Toast.LENGTH_LONG).show();

                        *//*SnackBar snackbar = new SnackBar(getActivity(), "Hubo "+result.size()+" registros que no pudieron ser subidos (posiblemente repetidos)", null, null);
                        snackbar.show();*//*
                    }
                    else{
                        OPR_USUARIOS.resetearRutas();
                        Actualizar_Lista();

                        Toast.makeText(getActivity(),"Operacion Completada.",Toast.LENGTH_LONG).show();

                        *//*SnackBar snackbar = new SnackBar(getActivity(), "Operacion Completada.", null, null);
                        snackbar.show();*//*
                    }
                }else{

                    Toast.makeText(getActivity(),"Ha Ocurrido un Error",Toast.LENGTH_LONG).show();

                    *//*SnackBar snackbar = new SnackBar(getActivity(), "Ha Ocurrido un Error", null, null);
                    snackbar.show();*//*
                }


            }//3

            @Override
            protected void onCancelled() {//4
                super.onCancelled();
            }//4

        }.execute(null, null, null);

    }*/


    public class SubirRutas extends AsyncTask<String,Integer,Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            Integer result;
            String Id_Carga =  params[0];
            OPR_USUARIOS Registro;

            try {//4

                String Url = "http://"+getResources().getString(R.string.GV_DN)+
                        "/IMERA_OS.svc/addCuenta";

                //String Url = "http://censocomapa.azurewebsites.net/IMERA_OS.svc/addCuenta";
                //String Url = "http://192.168.0.107/IMERA_OS/IMERA_OS.svc/addCuenta";


                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                db = usdbh.getReadableDatabase();

                //Si hemos abierto correctamente la base de datos
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT id FROM OPR_USUARIOS WHERE id_carga= "+Id_Carga+" AND id_estatus=102;", null);
                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Registro = OPR_USUARIOS.cargarRegistro(c.getString(0));
                            ArrayList<Parameter> Parametros = new ArrayList<>();


                            if(Registro.getId_padron()!=null)
                                Parametros.add(new Parameter("p1",Registro.getId_padron()));
                            if(Registro.getId_cuenta()!=null)
                                Parametros.add(new Parameter("p2",Registro.getId_cuenta()));
                            if(Registro.getId_carga()!=null)
                                Parametros.add(new Parameter("p3",Registro.getId_carga()));
                            if(Registro.getId_censista()!=null)
                                Parametros.add(new Parameter("p4",Registro.getId_censista()));
                            if(Registro.getId_servicio()!=null)
                                Parametros.add(new Parameter("p5", Registro.getId_servicio()));

                            if(Registro.getId_tipousuario()!=null)
                                Parametros.add(new Parameter("p6",Registro.getId_tipousuario()));
                            if(Registro.getMedidor_ins()!=null && !Registro.getMedidor_ins().equals(""))
                                Parametros.add(new Parameter("p7",Registro.getMedidor_ins()));
                            if(Registro.getLectura_med_ins()!=null && !Registro.getLectura_med_ins().equals(""))

                                Parametros.add(new Parameter("p8",Registro.getLectura_med_ins()));

                            if(Registro.getId_marca_ins()!=null  && !Registro.getId_marca_ins().equals(""))
                                Parametros.add(new Parameter("p9",Registro.getId_marca_ins()));
                            if(Registro.getId_modelo_ins()!=null && !Registro.getId_modelo_ins().equals(""))
                                Parametros.add(new Parameter("p10",Registro.getId_modelo_ins()));

                            if(Registro.getId_diametro()!=null)
                                Parametros.add(new Parameter("p11",Registro.getId_diametro()));
                            if(Registro.getId_materialtoma()!=null)
                                Parametros.add(new Parameter("p12",Registro.getId_materialtoma()));
                            if(Registro.getId_tipotoma()!=null)
                                Parametros.add(new Parameter("p13",Registro.getId_tipotoma()));
                            if(Registro.getId_tipoglobo()!=null)
                                Parametros.add(new Parameter("p14",Registro.getId_tipoglobo()));
                            if(Registro.getId_mat_banqueta()!=null)
                                Parametros.add(new Parameter("p15",Registro.getId_mat_banqueta()));

                            if(Registro.getId_mat_calle()!=null)
                                Parametros.add(new Parameter("p16",Registro.getId_mat_calle()));
                            if(Registro.getRegistro_banqueta()!=null)
                                Parametros.add(new Parameter("p17",Registro.getRegistro_banqueta()));
                            if(Registro.getId_anomalia()!=null)
                                /*Parametros.add(new Parameter("p18",Registro.getId_anomalia()));      //Sin Anomalias
                                if(Registro.getFac_tecnica()!=null)*/
                                Parametros.add(new Parameter("p19",Registro.getFac_tecnica()));
                            if(Registro.getTinaco()!=null)
                                Parametros.add(new Parameter("p20",Registro.getTinaco()));

                            if(Registro.getCisterna()!=null)
                                Parametros.add(new Parameter("p21",Registro.getCisterna()));
                            if(Registro.getAlberca()!=null)
                                Parametros.add(new Parameter("p22",Registro.getAlberca()));
                            if(Registro.getPozo()!=null)
                                Parametros.add(new Parameter("p23",Registro.getPozo()));

                            if(Registro.getFoto_predio()!=null)
                                Parametros.add(new Parameter("p24",Registro.getFoto_predio()));
                            if(Registro.getFoto_toma()!=null)
                                Parametros.add(new Parameter("p25",Registro.getFoto_toma()));

                            if(Registro.getNombre_comercial()!=null && !Registro.getNombre_comercial().equals(""))
                                Parametros.add(new Parameter("p26",Registro.getNombre_comercial()));
                            if(Registro.getObserva_a()!=null)
                                Parametros.add(new Parameter("p27",Registro.getObserva_a()));
                            if(Registro.getId_giro()!=null)
                                Parametros.add(new Parameter("p28",Registro.getId_giro()));
                            if(Registro.getId_calle_ppal()!=null)
                                Parametros.add(new Parameter("p29",Registro.getId_calle_ppal()));
                            if(Registro.getId_calle_lat1()!=null)
                                Parametros.add(new Parameter("p30",Registro.getId_calle_lat1()));

                            if(Registro.getLatitud()!=null)
                                Parametros.add(new Parameter("p31",Registro.getLatitud()));
                            if(Registro.getLongitud()!=null)
                                Parametros.add(new Parameter("p32",Registro.getLongitud()));
                            if(Registro.getId_estatus()!=null)
                                Parametros.add(new Parameter("p33",Registro.getId_estatus()));
                            if(Registro.getId_identificacion()!=null)
                                Parametros.add(new Parameter("p34",Registro.getId_identificacion()));
                            if(Registro.getOtro()!=null)
                                Parametros.add(new Parameter("p35",Registro.getOtro()));

                            if(Registro.getNum_ext()!=null)
                                Parametros.add(new Parameter("p36",Registro.getNum_ext()));
                            if(Registro.getNum_int()!=null)
                                Parametros.add(new Parameter("p37",Registro.getNum_int()));

                            Parametros.add(new Parameter("p38",Registro.getClaveloc()));
                            Parametros.add(new Parameter("40","c3ns0k3y16"));


                            ApiManager apiManager = ApiManager.getInstance();

                            result = apiManager.ActualizarPadron(Registro.getId_padron()
                                    ,Registro.getId_cuenta()
                                    ,Registro.getId_carga()
                                    ,Registro.getId_censista()
                                    ,Registro.getId_servicio()
                                    ,Registro.getId_tipousuario()
                                    ,Registro.getMedidor_ins()
                                    ,Registro.getLectura_med_ins()
                                    ,Registro.getId_marca_ins()
                                    ,Registro.getId_modelo_ins()
                                    ,Registro.getId_diametro()
                                    ,Registro.getId_materialtoma()
                                    ,Registro.getId_tipotoma()
                                    ,Registro.getId_tipoglobo()
                                    ,Registro.getId_mat_banqueta()
                                    ,Registro.getId_mat_calle()
                                    ,Registro.getRegistro_banqueta()
                                    ,Registro.getId_anomalia()
                                    ,Registro.getFac_tecnica()
                                    ,Registro.getTinaco()
                                    ,Registro.getCisterna()
                                    ,Registro.getAlberca()
                                    ,Registro.getPozo()
                                    ,Registro.getFoto_predio()
                                    ,Registro.getFoto_toma()
                                    ,Registro.getNombre_comercial()
                                    ,Registro.getObserva_a()
                                    ,Registro.getId_giro()
                                    ,Registro.getId_calle_ppal()
                                    ,Registro.getId_calle_lat1()
                                    ,Registro.getLatitud()
                                    ,Registro.getLongitud()
                                    ,Registro.getId_estatus()
                                    ,Registro.getId_identificacion()
                                    ,Registro.getOtro()
                                    ,Registro.getNum_ext()
                                    ,Registro.getNum_int()
                                    ,Registro.getClaveloc()
                                    ,"c3ns0k3y16");

//                            result = Send_Functions.Send_Get_New(Url, Parametros);

                            publishProgress(result);

                            if (!(result > 0)){
//                                ListId_OtSubidos.add(RegistroOrden.getId_orden());
                            }else {
//                                ListId_OtNoSubidos.add(RegistroOrden.getId_orden());
                            }

                            /*Log.e("Debbug(result)-->", result);

                            if(result.equals("Error")){//Error de Conexion
//                                Errores.add(Registro.getId());
                                Log.e("Error", "De Conexion");
                            }else{
                                JSONObject obj = new JSONObject(result);
                                result = obj.get("addCuentaResult").toString();
                                if(result.equals("1"))
                                {
                                    //No se eliminara sino es hasta dentro de la configuracion
                                    //db.execSQL("DELETE FROM OPR_USUARIOS WHERE id = " + Registro.getId() + ";");
                                    Log.e("Debugg","Fue eliminado");
                                }
                                else{
//                                    Errores.add(Registro.getId());//Error de Registro
                                    Log.e("Error","Error WCF");
                                }
                            }*/

                        } while (c.moveToNext());
                    }

                    if(!c.isClosed())c.close();

                    //Cerramos la base de datos
                    db.close();
                }

                return 0;

            }catch (Exception e) {//4
                Log.e("Error", e.toString());
//                Errores.add("Error");
                return 0;
            }//4
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            if (!(values[0] > 0) ) {
         /*       lblRegSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosSubidos.setText(String.valueOf(Integer.parseInt(txtContadorRegistrosSubidos.getText().toString()) + 1));
                Log.e("Exito?", values[0].getError_message());*/
            } else {
        /*        lblRegNoSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosNoSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosNoSubidos.setText(String.valueOf(Integer.parseInt(txtContadorRegistrosNoSubidos.getText().toString()) + 1));
                Log.e("Error", values[0].getError_message());*/
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            img_Subir.setVisibility(View.VISIBLE);
            pb_Estado.setVisibility(View.INVISIBLE);

            if(!(false)){
                if(true){

                    OPR_USUARIOS.resetearRutas();
                    Actualizar_Lista();

                    Toast.makeText(getActivity(),"Hubo  registros que no pudieron ser subidos (posiblemente repetidos)",Toast.LENGTH_LONG).show();

                        /*SnackBar snackbar = new SnackBar(getActivity(), "Hubo "+result.size()+" registros que no pudieron ser subidos (posiblemente repetidos)", null, null);
                    snackbar.show();*/
                }
                else{
                    OPR_USUARIOS.resetearRutas();
                    Actualizar_Lista();

                    Toast.makeText(getActivity(),"Operacion Completada.",Toast.LENGTH_LONG).show();

                        /*SnackBar snackbar = new SnackBar(getActivity(), "Operacion Completada.", null, null);
                    snackbar.show();*/
                }
            }else{

                Toast.makeText(getActivity(),"Ha Ocurrido un Error",Toast.LENGTH_LONG).show();

                    /*SnackBar snackbar = new SnackBar(getActivity(), "Ha Ocurrido un Error", null, null);
                snackbar.show();*/
            }
        }
    }

/*
    public class SubirOrdenesOffLine extends AsyncTask<Void,clsResultadoWCF,clsResultadoWCF> {
        @Override
        protected clsResultadoWCF doInBackground(Void... strings) {
            clsResultadoWCF resultadoWCF = new clsResultadoWCF();
            clsOrdenTrabajoSubir RegistroOrden;
            clsMaterial RegistroMaterial;
            ArrayList<clsDetMaterial> LisDetMateriales;

            IParametro parService = new ParametroDomainObject();
            String strKey = parService.GetValorParametro(getString(R.string.parametro_key_wcf));

            int intIdTrabajo = ordenSeleccionada.getId_trabajo();

            ListId_OtSubidos.clear();
            ListId_OtNoSubidos.clear();

            IOprOrdenTrabajo OTService = new OprOrdenTrabajoDomainObject();
            IDetMaterial DetMatService = new DetMaterialDomainObject();
            IMaterial MatService = new MaterialDomainObject();

            //Actualizo el id_captoro en las ordenes de trabajo antes de subirlas
            OTService.ActualizaCapturistaOrden(capturistaActual.getId_usuario());

            ArrayList<clsOrdenTrabajo> listOrdenTrabajo = OTService.OprOrdenTrabajoGetList(intIdTrabajo);

            if(listOrdenTrabajo.size() > 0){
                for (clsOrdenTrabajo ordenTrabajo: listOrdenTrabajo){
                    RegistroOrden = OTService.OprOrdenTrabajoSubirGetById(ordenTrabajo.getId());
                    RegistroMaterial = MatService.MaterialGetById(RegistroOrden.getId_orden());
                    LisDetMateriales = DetMatService.DetMaterialesGetList(RegistroMaterial.getId_mat());


                    ApiManager apiManager = ApiManager.getInstance();

                    PostOrdenes postOrdenes = new PostOrdenes(strKey
                            ,RegistroOrden
                            ,RegistroMaterial
                            ,LisDetMateriales);

                    resultadoWCF = apiManager.SubirOrdenesOffLine(postOrdenes);

                    publishProgress(resultadoWCF);

                    if (!(resultadoWCF.getError_number() > 0)){
                        ListId_OtSubidos.add(RegistroOrden.getId_orden());
                    }else {
                        ListId_OtNoSubidos.add(RegistroOrden.getId_orden());
                    }
                }
            }
            return resultadoWCF;
        }

        @Override
        protected void onProgressUpdate(clsResultadoWCF... values) {
            super.onProgressUpdate(values);
            if (!(values[0].getError_number() > 0) ) {
                lblRegSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosSubidos.setText(String.valueOf(Integer.parseInt(txtContadorRegistrosSubidos.getText().toString()) + 1));
                Log.e("Exito?", values[0].getError_message());
            } else {
                lblRegNoSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosNoSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosNoSubidos.setText(String.valueOf(Integer.parseInt(txtContadorRegistrosNoSubidos.getText().toString()) + 1));
                Log.e("Error", values[0].getError_message());
            }
        }

        @Override
        protected void onPostExecute(clsResultadoWCF clsResultadoWCF) {
            super.onPostExecute(clsResultadoWCF);
            if (rgAfterUpload.getCheckedRadioButtonId() == R.id.rb2EliminarDatos) {
                if (ListId_OtSubidos.size() > 0) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setMessage(R.string.msj_desea_eliminar_ordenes);
                    alertDialog.setTitle(R.string.se_requiere_confirmacion);
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setCancelable(false);
                    alertDialog.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //c�digo java si se ha pulsado no
                            ActualizarListaOrdenesCargadas();
                        }
                    });
                    alertDialog.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            IOprOrdenTrabajo OTService = new OprOrdenTrabajoDomainObject();
                            if (ListId_OtSubidos.size() > 0)
                                for(int x=0;x<ListId_OtSubidos.size();x++) {

                                    OTService.EliminaOrdenesDespuesSubir(ListId_OtSubidos.get(x));

                                }
                            ActualizarListaOrdenesCargadas();
                            Rutinas.SnackBarCustom(getContext(), getString(R.string.msj_operacion_realizada),tipo_snackbar.POSITIVE_SNACKBAR);
                        }
                    });
                    alertDialog.show();


                } else{
                    if (ListId_OtNoSubidos.size() > 0) {
                        ActualizarListaOrdenesCargadas();
                        Rutinas.SnackBarCustom(getContext()
                                ,String.format(getString(R.string.formato_reg_no_subieron), ListId_OtNoSubidos.size())
                                ,tipo_snackbar.INFORMATIVE_SNACKBAR);

                    }
                }

            } else {

                if (ListId_OtNoSubidos.size() > 0) {
                    ActualizarListaOrdenesCargadas();
                    Rutinas.SnackBarCustom(getContext()
                            ,String.format(getString(R.string.formato_reg_no_subieron), ListId_OtNoSubidos.size())
                            ,tipo_snackbar.INFORMATIVE_SNACKBAR);
                }
            }

            img_Subir.setVisibility(View.VISIBLE);
            pb_Estado.setVisibility(View.INVISIBLE);
        }
    }

*/
    public void onPause() {
        if(subirRutasHttp !=null)
            subirRutasHttp.cancel(true);

        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        root = null;
    }
}