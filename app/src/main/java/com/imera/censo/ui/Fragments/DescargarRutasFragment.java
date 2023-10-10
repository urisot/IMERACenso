package com.imera.censo.ui.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.BosinessCore.DomainObject.ParametroDomainObject;
import com.imera.censo.BosinessCore.DomainObject.TipoUsuarioDomainObject;
import com.imera.censo.Contracts.Enums.tipo_mensajes_error;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Models.TipoUsuario;
import com.imera.censo.Contracts.Models.UsuarioPadron;
import com.imera.censo.Contracts.Services.IParametro;
import com.imera.censo.Contracts.Services.ITipoUsuario;
import com.imera.censo.R;
import com.imera.censo.Rutinas;
import com.imera.censo.Services.ApiManager;
import com.imera.censo.ui.Activities.PickerActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescargarRutasFragment extends Fragment {
    private View root;
    private ArrayList<String> AL_Rutas = new ArrayList<String>();
    private ArrayAdapter<String> Adaptador;
    private String Id_Colonia = "0";
    private ArrayList<UsuarioPadron> Usuarios = new ArrayList<UsuarioPadron>();
    private ProgressBar pb_Estado,pb_descargando;
    private TextView et_Colonia;
    //private Button  btn_Buscar;
    private Button  btn_Descargar;
    private Button  btn_Anadir;

    TextInputLayout tilGrupo;
    EditText et_Grupo;
    TextInputLayout tilSector;
    EditText et_Sector;
    TextInputLayout tilRango1;
    EditText et_Rango1;
    TextInputLayout tilRango2;
    EditText et_Rango2;
    AutoCompleteTextView actNumber;
    String et_Grupo_uv = "0";
    String et_Sector_uv = "0";
    String et_Rango1_uv = "0";
    String et_Rango2_uv = "0";

    ListView lv_Rutas;

    TextView resultado;
    ArrayList<String> ListNumber = new ArrayList<String>();
    ArrayAdapter<String> numberAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_descargar_rutas, container, false);
        btn_Descargar = (Button) root.findViewById(R.id.btn_LoadResults);
        btn_Anadir = (Button) root.findViewById(R.id.btn_add);
//        et_Colonia = (TextView) root.findViewById(R.id.et_Colonia);
        tilGrupo = (TextInputLayout)root.findViewById(R.id.tilGrupo);
        et_Grupo = (EditText) root.findViewById(R.id.etGrupo);
        tilSector = (TextInputLayout) root.findViewById(R.id.tilSector);
        et_Sector = (EditText) root.findViewById(R.id.etSector);
        tilRango1 = (TextInputLayout) root.findViewById(R.id.tilRango1);
        et_Rango1 = (EditText) root.findViewById(R.id.etRango1);
        tilRango2 = (TextInputLayout) root.findViewById(R.id.tilRango2);
        et_Rango2 = (EditText) root.findViewById(R.id.etRango2);
        resultado = (TextView) root.findViewById(R.id.et_resultado);
        pb_Estado  = (ProgressBar) root.findViewById(R.id.pb_estado_bajada);
        pb_descargando  = (ProgressBar) root.findViewById(R.id.pb_descargando);
        lv_Rutas = (ListView) root.findViewById(R.id.lv_Rutas);
        actNumber = (AutoCompleteTextView) root.findViewById(R.id.actNumber);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Adaptador = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,AL_Rutas);
        lv_Rutas.setAdapter(Adaptador);

        Actualizar_Lista();
        picker_vacios();

        et_Grupo.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if(et_Grupo.getText().equals("0")){
                        Toast.makeText(getActivity(), "Tiene Focus", Toast.LENGTH_SHORT).show();
                        et_Grupo.setText("");
                    }
                }else{
                    if(et_Grupo.getText().equals(""))
                    {
                        Toast.makeText(getActivity(), "No Tiene Focus", Toast.LENGTH_SHORT).show();
                        et_Grupo.setText("0");
                    }

                }
            }
        });

        et_Sector.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if(et_Sector.getText().equals("0")){
                        et_Sector.setText("");
                    }
                }else{
                    if(et_Sector.getText().equals(""))
                    {
                        et_Sector.setText("0");
                    }

                }
            }
        });

        et_Rango1.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if(et_Rango1.getText().equals("0")){
                        et_Rango1.setText("");
                    }
                }else{
                    if(et_Rango1.getText().equals(""))
                    {
                        et_Rango1.setText("0");
                    }

                }
            }
        });

        et_Rango2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if(et_Rango2.getText().equals("0")){
                        et_Rango2.setText("");
                    }
                }else{
                    if(et_Rango2.getText().equals(""))
                    {
                        et_Rango2.setText("0");
                    }

                }
            }
        });

        btn_Descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean HayErrores = false;
                String Error = "";

                Usuarios.clear();

                et_Grupo_uv = et_Grupo.getText().toString();
                et_Sector_uv = et_Sector.getText().toString();
                et_Rango1_uv = et_Rango1.getText().toString();
                et_Rango2_uv = et_Rango2.getText().toString();

                //Validaciones
                if(et_Grupo_uv.equals("")){
                    /*HayErrores = true;
                    Error = "El Campo Grupo no ha sido Especificado";*/
                    et_Grupo_uv = "0";
                }

                if(et_Sector_uv.equals("")){
                    /*HayErrores = true;
                    Error = "El Campo Sector no ha sido Especificado";*/
                    et_Sector_uv = "0";
                }

                if(et_Rango1_uv.equals("")){
                    /*HayErrores = true;
                    Error = "El Rango inicial no ha sido Especificado";*/
                    et_Rango1_uv = "0";
                }

                if(et_Rango2_uv.equals("")){
                    /*HayErrores = true;
                    Error = "El Rango final no ha sido Especificado";*/
                    et_Rango2_uv = "0";
                }

                if(Id_Colonia.equals("")){
                    HayErrores = true;
                    Error = "No se ha seleccionado Colonia alguna";
                }
                //Fin Validaciones

                if(!HayErrores){

                    resultado.setVisibility(View.INVISIBLE);
                    pb_Estado.setVisibility(View.VISIBLE);
                    btn_Anadir.setEnabled(false);
                    btn_Descargar.setEnabled(false);

                    IParametro parService = new ParametroDomainObject();
                    String strKey = parService.GetValorParametro(getString(R.string.parametro_key_wcf));
                    ObtenerUsuarios(et_Grupo_uv,et_Sector_uv,et_Rango1_uv,et_Rango2_uv,Id_Colonia,strKey);
                }else{

                    Toast.makeText(getActivity(),"Error!: "+Error,Toast.LENGTH_LONG).show();
                    /*SnackBar snackbar = new SnackBar(getActivity(), "Error!: "+Error, null, null);
                    snackbar.show();*/
                }

            }
        });

        btn_Anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int duplicados = 0;
                String Ultima_Ruta = "1";
                String Censista_Activo="";
                try{
                    if(Usuarios.size()==0){

                        Toast.makeText(getActivity(),"No se ha descargado ruta alguna.",Toast.LENGTH_LONG).show();


                    }else{
                        pb_descargando.setVisibility(View.VISIBLE);
                        BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                        SQLiteDatabase db = usdbh.getWritableDatabase();

                        //Si hemos abierto correctamente la base de datos
                        if (db != null) {//6


                            Cursor c = db.rawQuery("SELECT MAX(id_carga) FROM OPR_USUARIOS;", null);

                            //Nos aseguramos de que existe al menos un registro
                            if (c.moveToFirst()) {
                                //Recorremos el cursor hasta que no haya mas registros
                                do {
                                    if(c.getString(0)!=null)
                                        Ultima_Ruta = ""+(1+Integer.parseInt(c.getString(0)));
                                } while (c.moveToNext());
                            }

                            if(!c.isClosed())c.close();

                            c = db.rawQuery("SELECT id_Personal FROM Cat_Censistas WHERE estado_activo=1;", null);
                            if(c.moveToFirst()){
                                Censista_Activo = c.getString(0);
                            }

                            if(!c.isClosed())c.close();

                            for (int i = 0; i < Usuarios.size(); i++) {//7

                                OPR_USUARIOS info = new OPR_USUARIOS();  //Hecho asi por el Gson
                                info.setId_padron(Usuarios.get(i).getId_padron());
                                info.setId_cuenta(Usuarios.get(i).getId_cuenta());
                                info.setId_carga(Ultima_Ruta);
                                info.setId_censista(Censista_Activo);
                                info.setMedidor_ins(Usuarios.get(i).getMedidor());
                                info.setId_calle_ppal(Usuarios.get(i).getId_calle_ppal());
                                info.setId_estatus("101");
                                info.setId_colonia(Usuarios.get(i).getId_colonia());
                                info.setDireccion(Usuarios.get(i).getDireccion());
                                info.setNombre(Usuarios.get(i).getNombre());
                                info.setClaveloc(Usuarios.get(i).getClave());

                                if(info.getId_cuenta().equals("0")){
                                    OPR_USUARIOS.insertarNormal(info);
                                }else{

                                    c= db.rawQuery("SELECT id_cuenta FROM OPR_USUARIOS WHERE id_cuenta = "+info.getId_cuenta(),null);
                                    if (c.moveToFirst()) {
                                        duplicados++;
                                    }else
                                        OPR_USUARIOS.insertarNormal(info);
                                }

                            }//7

                            if(!c.isClosed())c.close();

                            //Cerramos la base de datos
                            db.close();
                        }//6
                        pb_descargando.setVisibility(View.INVISIBLE);
                        Actualizar_Lista();

                        if(duplicados>0){

                            Toast.makeText(getActivity(),"Hubo "+duplicados+" registros duplicados que no fueron insertados.",Toast.LENGTH_LONG).show();

                        /*SnackBar snackbar = new SnackBar(getActivity(), "Hubo "+duplicados+" registros duplicados que no fueron insertados.", null, null);
                        snackbar.show();*/
                        }

                    }}catch (Exception e){
                    Log.e("DEBUGG(error)->",e.toString());

                    Toast.makeText(getActivity(),"Error!:  "+e.toString(),Toast.LENGTH_LONG).show();

                    /*SnackBar snackbar = new SnackBar(getActivity(), "Error!:  "+e.toString(), null, null);
                    snackbar.show();*/
                }

                reset();

            }
        });

//        et_Colonia.setOnClickListener(PickerColonia);
        //btn_Buscar.setOnClickListener(PickerColonia);

/*
        ListNumber.add("pedregal");
        ListNumber.add("cuatrocieneges");
        ListNumber.add("estancias");
        ListNumber.add("piedras");
*/

        BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();
//        ArrayList<String> Al_DP_Data = new ArrayList<String>();

//        ListNumber.add("Sin Especificar");

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Colonias", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    ListNumber.add(c.getString(0));
                } while (c.moveToNext());
            }

            if(!c.isClosed())c.close();

            //Cerramos la base de datos
            db.close();
        }

        numberAdapter = new ArrayAdapter<String>( getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,ListNumber);
        actNumber.setAdapter(numberAdapter);
        actNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getContext(),numberAdapter.getItem(position),Toast.LENGTH_LONG).show();

                String Colonia = numberAdapter.getItem(position);

                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();

                if(!Colonia.equals("Sin Especificar")){

                    //Si hemos abierto correctamente la base de datos
                    if (db != null) {
                        Cursor c = db.rawQuery("SELECT id_colonia FROM Cat_Colonias WHERE descripcion LIKE '"+Colonia+"' ", null);

                        //Nos aseguramos de que existe al menos un registro
                        if (c.moveToFirst()) {
                            //Recorremos el cursor hasta que no haya mas registros
                            do {
                                Id_Colonia = c.getString(0);
                            } while (c.moveToNext());
                        }

                        if(!c.isClosed())c.close();

                        //Cerramos la base de datos
                        db.close();
                    }
//                    et_Colonia.setText(Colonia);

                }else{
                    Id_Colonia = "0";
                    picker_vacios();
//                    et_Colonia.setText(Colonia);
                }

            }
        });


    }

    private void ObtenerUsuarios(String nGrupo, String nSec, String nManIni, String nManFin, String nIdColonia, String key){

        ApiManager apiManager = ApiManager.getInstance();
        apiManager.PadronHttpGetList(nGrupo, nSec, nManIni, nManFin, nIdColonia, key, new Callback<ArrayList<UsuarioPadron>>() {
            @Override
            public void onResponse(Call<ArrayList<UsuarioPadron>> call, Response<ArrayList<UsuarioPadron>> response) {
                ArrayList<UsuarioPadron> PadronList = response.body();
                if (response.isSuccessful() && PadronList != null)
                {
                    Usuarios = PadronList;
                    resultado.setText("Ruta Encontrada " + Usuarios.size() + " Usuarios");
                }else {

                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }
                MuestraOculata(true);
                btn_Anadir.setEnabled(true);
                btn_Descargar.setEnabled(true);
            }
            @Override
            public void onFailure(Call<ArrayList<UsuarioPadron>> call, Throwable t) {
                MuestraOculata(true);
                btn_Anadir.setEnabled(true);
                btn_Descargar.setEnabled(true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity(), tipo_mensajes_error.TOAST,pb_Estado);
            }
        });
    }

    private void MuestraOculata(boolean boolMuestra){
        if(boolMuestra){
            pb_Estado.setVisibility(View.INVISIBLE);
            resultado.setVisibility(View.VISIBLE);
        }else{
            pb_Estado.setVisibility(View.VISIBLE);
            resultado.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==111){
            if(resultCode ==1 ){
                String Colonia = data.getStringExtra("Opcion");

                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();

                if(!Colonia.equals("Sin Especificar")){

                    //Si hemos abierto correctamente la base de datos
                    if (db != null) {
                        Cursor c = db.rawQuery("SELECT id_colonia FROM Cat_Colonias WHERE descripcion LIKE '"+Colonia+"' ", null);

                        //Nos aseguramos de que existe al menos un registro
                        if (c.moveToFirst()) {
                            //Recorremos el cursor hasta que no haya mas registros
                            do {
                                Id_Colonia = c.getString(0);
                            } while (c.moveToNext());
                        }

                        if(!c.isClosed())c.close();

                        //Cerramos la base de datos
                        db.close();
                    }
//                    et_Colonia.setText(Colonia);

                }else{
                    Id_Colonia = "0";
                    picker_vacios();
//                    et_Colonia.setText(Colonia);
                }
            }
        }
        picker_vacios();
    }
    public void onPause() {

        super.onPause();
    }


    public void Actualizar_Lista(){

        AL_Rutas.clear();
        Adaptador.notifyDataSetChanged();

        BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        if (db != null) {//6
            Cursor c = db.rawQuery("SELECT MAX(id_carga),count(id_carga) FROM OPR_USUARIOS GROUP BY id_carga ORDER BY count(id_carga) DESC;", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    AL_Rutas.add("Ruta " + c.getString(0) + ", " +c.getString(1)+" Usuarios Asignados");
                    Adaptador.notifyDataSetChanged();
                } while (c.moveToNext());
            }

            if(!c.isClosed())c.close();

            //Cerramos la base de datos
            db.close();
        }//6

    }

    //Informar No Seleccion
    public void picker_vacios(){

 /*       if(Id_Colonia.equals(""))
            et_Colonia.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else if(Id_Colonia.equals("0"))
            et_Colonia.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_orange_light));
        else
            et_Colonia.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        root = null;
    }

    public void reset(){
        et_Grupo_uv = "0";
        et_Sector_uv = "0";
        et_Rango1_uv = "0";
        et_Rango2_uv = "0";

        et_Grupo.setText(et_Grupo_uv);
        et_Sector.setText(et_Sector_uv);
        et_Rango1.setText(et_Rango1_uv);
        et_Rango2.setText(et_Rango2_uv);

        Id_Colonia = "0";
//        et_Colonia.setText("Colonia");

        resultado.setText("");
        Usuarios.clear();
        picker_vacios();
    }

    private View.OnClickListener PickerColonia = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Abrimos la base de datos 'DBUsuarios' en modo Lectura
            BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
            SQLiteDatabase db = usdbh.getReadableDatabase();
            Intent intento = new Intent(getActivity(), PickerActivity.class);
            Bundle manejadordp = new Bundle();
            ArrayList<String> Al_DP_Data = new ArrayList<String>();

            Al_DP_Data.add("Sin Especificar");

            //Si hemos abierto correctamente la base de datos
            if (db != null) {
                Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Colonias", null);

                //Nos aseguramos de que existe al menos un registro
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya mas registros
                    do {
                        Al_DP_Data.add(c.getString(0));
                    } while (c.moveToNext());
                }

                if(!c.isClosed())c.close();

                //Cerramos la base de datos
                db.close();
            }

            manejadordp.putStringArrayList("data", Al_DP_Data);
            manejadordp.putString("Titulo", "Seleccione una Colonia");
            intento.putExtras(manejadordp);
            getActivity().startActivityForResult(intento, 111);

        }
    };
}