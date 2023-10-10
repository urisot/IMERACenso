package com.imera.censo.ui.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.BosinessCore.DomainObject.ParametroDomainObject;
import com.imera.censo.Contracts.Enums.tipo_snackbar;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Services.IParametro;
import com.imera.censo.Contracts.Services.OnRutaSelectListener;
import com.imera.censo.R;
import com.imera.censo.Rutinas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ConfiguracionFragment extends Fragment {
    private Spinner Sp_Censistas;
    private Spinner Sp_Rutas;
    private ArrayList<String> Censistas = new ArrayList<String>();
    private ArrayList<String> Ids_Censistas = new ArrayList<String>();
    private ArrayAdapter Adaptador;

    private ArrayAdapter Adaptador2;
    private ArrayList<String> Rutas= new ArrayList<>();
    private ArrayList<String> Ids_Rutas = new ArrayList<>();

    private TextInputLayout tilUsuario;
    private EditText Usuario;
    private TextInputLayout tilContrasena;
    private EditText Contrasena;
    private TextInputLayout tilRContrasena;
    private EditText RContrasena;
    private ImageButton btn_guarda_wcf;
    private Button btn_Guardar_Rutas;
    private ImageButton btn_Borrar_Rutas;
    private OnRutaSelectListener listener;
    private String Id_RSeleccionada = "";
    private View root;
    private EditText ET_DireccionWCF;
    private String DirWCF="";
    private Switch sw_DescargarOrdenesOnline,sw_Autolocalizacion,sw_MuestraOcultaMenuLateral
            ,sw_ModoCapturaOnline,sw_PermiteCancelarOrdenes,sw_PermiteEliminarOrdenes
            ,sw_PermiteDescargarCatalogos,sw_PermiteDescargar,sw_PermiteSubir
            ,sw_MostrarListadoAgrupado;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_configuracion, container, false);
        Sp_Rutas = (Spinner) root.findViewById(R.id.sp_borrar);
        Sp_Censistas = (Spinner) root.findViewById(R.id.sp_censistas);
        tilUsuario = (TextInputLayout) root.findViewById(R.id.tilUsuario);
        Usuario = (EditText) root.findViewById(R.id.etUsuario);
        tilContrasena = (TextInputLayout) root.findViewById(R.id.tilContrasena);
        Contrasena = (EditText) root.findViewById(R.id.etContrasena);
        btn_Guardar_Rutas = (Button) root.findViewById(R.id.btn_guardarRutas);
        btn_Borrar_Rutas = (ImageButton) root.findViewById(R.id.btn_borrarRutas);
        sw_MuestraOcultaMenuLateral = (Switch)root.findViewById(R.id.sw_MuestraOcultaMenuLateral);
        sw_PermiteDescargarCatalogos = (Switch)root.findViewById(R.id.sw_PermiteDescargarCatalogos);
        sw_PermiteDescargar = (Switch)root.findViewById(R.id.sw_PermiteDescargarOrdenes);
        sw_PermiteSubir = (Switch)root.findViewById(R.id.sw_PermiteSubirOrdenes);
        btn_guarda_wcf = (ImageButton)root.findViewById(R.id.btn_guarda_wcf);
        ET_DireccionWCF = (EditText)root.findViewById(R.id.et_DireccionWcf);
        /*sw_ModoCapturaOnline = (Switch)root.findViewById(R.id.sw_ModoCapturaOnline);
        sw_DescargarOrdenesOnline = (Switch)root.findViewById(R.id.sw_DescargarOrdenesOnline);
        sw_PermiteCancelarOrdenes = (Switch)root.findViewById(R.id.sw_PermiteCancelarOrdenes);
        sw_PermiteEliminarOrdenes = (Switch)root.findViewById(R.id.sw_PermiteEliminarOrdenes);
          tilRContrasena = (TextInputLayout) root.findViewById(R.id.tilContrasena2);
        RContrasena = (EditText) root.findViewById(R.id.etContrasena2);
        btn_Guardar = (Button) root.findViewById(R.id.btn_guardarcuenta);
        btn_Borrar_Imagenes = (Button) root.findViewById(R.id.btn_borrarImagenes);*/

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            listener = (OnRutaSelectListener)getActivity();
        } catch (ClassCastException e) {}


        LoadCensistas();
   /*     LoadCurrentProfileInfo();*/

        Adaptador = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,Censistas);
        Sp_Censistas.setAdapter(Adaptador);
        Adaptador.notifyDataSetChanged();

        Adaptador2 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,Rutas);
        Sp_Rutas.setAdapter(Adaptador2);
        Adaptador2.notifyDataSetChanged();

        Actualizar_Lista();

        Cargar();

        btn_Guardar_Rutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guardar_Rutas();
            }
        });

        btn_Borrar_Rutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Id_RSeleccionada.equals("")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);

                    if(Id_RSeleccionada.equals("todas"))
                        alertDialog.setMessage("de eliminar todas las rutas.");
                    else
                        alertDialog.setMessage("de eliminar esta ruta.");

                    alertDialog.setTitle("¿Estas Seguro?");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setCancelable(false);
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //c�digo java si se ha pulsado no
                        }
                    });
                    alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Borrar_Rutas();

                            if(!Id_RSeleccionada.equals("todas"))
                                OPR_USUARIOS.resetearRutas();

                            Actualizar_Lista();


                            Toast.makeText(getActivity(),"Operacion Realizada.",Toast.LENGTH_LONG).show();
                        /*SnackBar snackbar = new SnackBar(getActivity(), "Operacion Realizada.", null, null);
                        snackbar.show();*/


                        }
                    });
                    alertDialog.show();

                }else{

                    Toast.makeText(getActivity(),"Selecciona una ruta primero.",Toast.LENGTH_LONG).show();
                    /*SnackBar snackbar = new SnackBar(getActivity(), "Selecciona una ruta primero.", null, null);
                    snackbar.show();*/
                }


            }
        });

        Sp_Rutas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Id_RSeleccionada = "" + Ids_Rutas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Sp_Censistas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BDManager Manejador = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = Manejador.getWritableDatabase();

                if (db != null) {
                    db.execSQL("UPDATE Cat_Censistas SET estado_activo = 0;");
                    db.execSQL("UPDATE Cat_Censistas SET estado_activo = 1 WHERE id_Personal = " + Ids_Censistas.get(position) + ";");
                    db.execSQL("UPDATE OPR_USUARIOS SET id_censista = " + Ids_Censistas.get(position) + ";");
                    db.close();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_guarda_wcf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DirWCF != null){
                    if(DirWCF.trim() != ET_DireccionWCF.getText().toString().trim()){

                        IParametro paraService = new ParametroDomainObject();
                        paraService.ParametroUpgrade(getString(R.string.parametro_direccion_wcf),ET_DireccionWCF.getText().toString());

                        Rutinas.SnackBarCustom(getActivity(),btn_guarda_wcf,"Aviso",
                                getString(R.string.msj_actualizo_wcf_con_exito),
                                tipo_snackbar.POSITIVE_SNACKBAR);

                    }
                }
            }
        });

        sw_MuestraOcultaMenuLateral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ActualizaParametro(getString(R.string.parametro_muestra_menu_lateral),isChecked);

                listener.muestra_oculta_menu_lateral(isChecked);
            }
        });
        sw_PermiteDescargarCatalogos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ActualizaParametro(getString(R.string.parametro_permite_descargar_catalogos),isChecked);
            }
        });

        sw_PermiteDescargar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ActualizaParametro(getString(R.string.parametro_permite_descargar_censo),isChecked);
            }
        });

        sw_PermiteSubir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ActualizaParametro(getString(R.string.parametro_permite_subir_censo),isChecked);
            }
        });


        sw_MuestraOcultaMenuLateral.setChecked(Rutinas.ObtenerValorBooleanoParametro(getString(R.string.parametro_muestra_menu_lateral)));
        sw_PermiteDescargarCatalogos.setChecked(Rutinas.ObtenerValorBooleanoParametro(getString(R.string.parametro_permite_descargar_catalogos)));
        sw_PermiteDescargar.setChecked(Rutinas.ObtenerValorBooleanoParametro(getString(R.string.parametro_permite_descargar_censo)));
        sw_PermiteSubir.setChecked(Rutinas.ObtenerValorBooleanoParametro(getString(R.string.parametro_permite_subir_censo)));
        LoadDireccionWCF();
    }

    private void ActualizaParametro(String strParametro, boolean isChecked){

        String strValorParametro = isChecked ? "1":"0";
        IParametro paraService = new ParametroDomainObject();
        paraService.ParametroUpgrade(strParametro,strValorParametro);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        root = null;
    }

    public void Actualizar_Lista(){

        Rutas.clear();
        Ids_Rutas.clear();
        Adaptador2.notifyDataSetChanged();

        BDManager Manejador = new BDManager(getActivity(), "DBCenso", null, 1);
        SQLiteDatabase db = Manejador.getReadableDatabase();

        if (db != null) {//6
            Cursor c = db.rawQuery("SELECT MAX(id_carga),count(id_carga) FROM OPR_USUARIOS GROUP BY id_carga ORDER BY count(id_carga) DESC;", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    Rutas.add("Ruta " + c.getString(0) + ", " +c.getString(1)+" Usuarios");
                    Ids_Rutas.add(c.getString(0));
                    Adaptador2.notifyDataSetChanged();
                } while (c.moveToNext());
            }


            if(!c.isClosed())c.close();
            //Cerramos la base de datos
            db.close();
        }//6

        Rutas.add("Todas las Rutas");
        Ids_Rutas.add("todas");
        Adaptador2.notifyDataSetChanged();

    }

    public void LoadCensistas(){
        Censistas.clear();
        Ids_Censistas.clear();

        BDManager Manejador = new BDManager(getActivity(), "DBCenso", null, 1);
        SQLiteDatabase db = Manejador.getReadableDatabase();

        if(db!=null){
            Cursor c = db.rawQuery("SELECT id_Personal,nombre FROM Cat_Censistas;",null);
            if(c.moveToFirst()){
                do{
                    Ids_Censistas.add(c.getString(0));
                    Censistas.add(c.getString(1)) ;
                }while (c.moveToNext());
            }

            if(!c.isClosed())c.close();
            db.close();
        }
    }

    public void Cargar(){
        BDManager Manejador = new BDManager(getActivity(), "DBCenso", null, 1);
        SQLiteDatabase db = Manejador.getReadableDatabase();

        if(db!=null){
            Cursor c = db.rawQuery("SELECT nombre FROM Cat_Censistas WHERE estado_activo=1;",null);
            if(c.moveToFirst()){
                Sp_Censistas.setSelection(Censistas.indexOf(c.getString(0)));
            }

            if(!c.isClosed())c.close();
            db.close();
        }
    }

    public void Guardar_Rutas(){
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            String packageName  = "com.demo.pruebas.censo";
            String sourceDBName = "DBCenso";
            String targetDBName = "DBCenso";
            if (sd.canWrite()) {
                Date now = new Date();
                String currentDBPath = "data/" + packageName + "/databases/" + sourceDBName;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                String backupDBPath = targetDBName + dateFormat.format(now) + ".db";

                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                Log.i("backup","backupDB=" + backupDB.getAbsolutePath());
                Log.i("backup","sourceDB=" + currentDB.getAbsolutePath());

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();


                Toast.makeText(getActivity(),"Operacion Realizada.",Toast.LENGTH_LONG).show();

                /*SnackBar snackbar = new SnackBar(getActivity(), "Operacion Realizada.", null, null);
                snackbar.show();*/
            }
        } catch (Exception e) {
            Log.i("Backup", e.toString());

            Toast.makeText(getActivity(),"Error: "+e.toString(),Toast.LENGTH_LONG).show();

            /*SnackBar snackbar = new SnackBar(getActivity(), "Error: "+e.toString(), null, null);
            snackbar.show();*/
        }
    }

    public void Borrar_Rutas(){
        BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            if(Id_RSeleccionada.equals("todas"))
                db.execSQL("DROP TABLE IF EXISTS OPR_USUARIOS");
            else
                db.execSQL("DELETE FROM OPR_USUARIOS WHERE id_carga =" + Id_RSeleccionada + " ;");

            db.execSQL(BDManager.sqlCreateOprUsuarios);

            //Cerramos la base de datos
            db.close();

            Toast.makeText(getActivity(),"Operacion Realizada.",Toast.LENGTH_LONG).show();

            /*SnackBar snackbar = new SnackBar(getActivity(), "Operacion Realizada.", null, null);
            snackbar.show();*/
        }

        Id_RSeleccionada = "";
    }
    public void LoadDireccionWCF(){
        IParametro parService = new ParametroDomainObject();

        DirWCF = parService.GetValorParametro(getString(R.string.parametro_direccion_wcf));
        ET_DireccionWCF.setText(DirWCF);

    }

   /* public void LoadCurrentProfileInfo(){
        BDManager Manejador = new BDManager(getActivity(), "DBCenso", null, 1);
        SQLiteDatabase db = Manejador.getReadableDatabase();

        if(db!=null){
            Cursor c = db.rawQuery("SELECT usuario,contrasena FROM Usuarios_Password;",null);
            if(c.moveToFirst()){

                Usuario.setText(c.getString(0));
                Contrasena.setText(c.getString(1));
                RContrasena.setText(c.getString(1));
            }
            if(!c.isClosed())c.close();
            db.close();
        }

    }*/
}