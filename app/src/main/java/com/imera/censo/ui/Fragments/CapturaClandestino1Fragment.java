package com.imera.censo.ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Contracts.Models.Catalogo;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Services.DataUpdate;
import com.imera.censo.Contracts.Services.InterfaceTiposWizard;
import com.imera.censo.R;
import com.imera.censo.ui.Activities.PickerActivity;

import java.util.ArrayList;


public class CapturaClandestino1Fragment extends Fragment implements DataUpdate {
    InterfaceTiposWizard Listener;

    TextView Et_Colonia;
    TextView Et_Calleppal;
    TextInputLayout tilClaveLOC;
    EditText Et_ClaveLOC;
    TextInputLayout tilNumExt;
    EditText Et_Numext;
    TextInputLayout tilNumInt;
    EditText Et_Numint;
    TextView Et_Calle1;
    TextView Et_Calle2;

    TextView Picker_TServicio;
    TextView Picker_TUsuario;

    TextView Picker_Diametro;
    TextView Picker_MaterialToma;

    TextView Picker_TipoToma;
    TextView Picker_Globo;

    String Id_TServicio = "";
    String Id_TUsuario = "";

    String Id_Diametro = "";
    String Id_MaterialToma = "";

    String Id_TipoToma = "";
    String Id_Globo = "";
    /*******************************************/
    String Id_Colonia = "";
    String Id_Callepal = "";
    String Id_Calle1 = "";
    String Id_Calle2 = "";
    String Where = "";

    OPR_USUARIOS Data;

    View vista;

    public CapturaClandestino1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        Data = bundle.getParcelable("Data");
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_captura_clandestino1, container, false);

        Picker_TServicio = (TextView)vista.findViewById(R.id.picker_TipoServicio);
        Picker_TUsuario = (TextView)vista.findViewById(R.id.picker_TipoUsuario);

        Picker_Diametro = (TextView)vista.findViewById(R.id.picker_DiametroToma);
        Picker_MaterialToma = (TextView)vista.findViewById(R.id.picker_MaterialToma);

        Picker_TipoToma = (TextView)vista.findViewById(R.id.picker_TipoToma);
        Picker_Globo = (TextView)vista.findViewById(R.id.picker_TipoGlobo);

        tilClaveLOC = (TextInputLayout)vista.findViewById(R.id.tilClaveLoc);
        Et_ClaveLOC = (EditText)vista.findViewById(R.id.etClaveLoc);
        Et_Colonia = (TextView)vista.findViewById(R.id.et_Colonia);
        Et_Calleppal = (TextView)vista.findViewById(R.id.et_CallePal);
        tilNumExt = (TextInputLayout)vista.findViewById(R.id.tilNumE);
        Et_Numext = (EditText)vista.findViewById(R.id.etNumE);
        tilNumInt = (TextInputLayout)vista.findViewById(R.id.tilNumI);
        Et_Numint = (EditText)vista.findViewById(R.id.etNumI);
        Et_Calle1 = (TextView)vista.findViewById(R.id.et_Calle1);
        Et_Calle2 = (TextView)vista.findViewById(R.id.et_Calle2);
        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Listener = (InterfaceTiposWizard) getActivity();
        } catch (ClassCastException e) {
        }

        //##-##-##-####-#####-##-##-##   <-- Estructura clave localizacion
        Et_ClaveLOC.addTextChangedListener(new TextWatcher() {
            private static final char space = '-';

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                boolean espacio = false;
                switch (s.length()){
                    case 3:
                        espacio = true;
                        break;
                    case 6:
                        espacio = true;
                        break;
                    case 9:
                        espacio = true;
                        break;
                    case 14:
                        espacio = true;
                        break;
                    case 20:
                        espacio = true;
                        break;
                    case 23:
                        espacio = true;
                        break;
                    case 26:
                        espacio = true;
                        break;
                }

                /*if(s.length() > 0 && espacio){
                    char c = s.charAt(s.length() - 1);

                    if(space == c)
                        s.delete(s.length() - 1, s.length());

                }*/

                if(s.length() > 0 && espacio){
                    char c = s.charAt(s.length() - 1);

                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 7) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        });

        Cargar();
        picker_vacios();

        Picker_TServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la base de datos 'DBUsuarios' en modo Lectura
                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                //Si hemos abierto correctamente la base de datos
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Servicios", null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione un Tipo de Servicio");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 1);
            }
        });

        Picker_TUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la base de datos 'DBUsuarios' en modo Lectura
                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                //Si hemos abierto correctamente la base de datos
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_TiposUsuario", null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione un Tipo de Usuario");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 2);
            }
        });

        Picker_Diametro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la base de datos 'DBUsuarios' en modo Lectura
                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                //Si hemos abierto correctamente la base de datos
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Diametros", null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione un Diametro de Toma");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 3);
            }
        });

        Picker_MaterialToma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la base de datos 'DBUsuarios' en modo Lectura
                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                //Si hemos abierto correctamente la base de datos
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_MaterialToma", null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione un Material de Toma");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 4);
            }
        });

        Picker_TipoToma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la base de datos 'DBUsuarios' en modo Lectura
                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                //Si hemos abierto correctamente la base de datos
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_TiposToma", null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione un Tipo de Toma");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 5);
            }
        });

        Picker_Globo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la base de datos 'DBUsuarios' en modo Lectura
                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                //Si hemos abierto correctamente la base de datos
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_TiposGlobo", null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione un Tipo de Globo");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 6);
            }
        });

        /*****************************************************************************************************************************************/
        Et_Colonia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la base de datos 'DBUsuarios' en modo Lectura
                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

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

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione una Colonia");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 7);
            }
        });

        Et_Calleppal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la base de datos 'DBUsuarios' en modo Lectura
                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                //Si hemos abierto correctamente la base de datos
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Calles "+Where, null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione una Calle");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 8);
            }
        });

        Et_Calle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la base de datos 'DBUsuarios' en modo Lectura
                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                //Si hemos abierto correctamente la base de datos
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Calles "+Where, null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione una Calle");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 9);
            }
        });

        Et_Calle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la base de datos 'DBUsuarios' en modo Lectura
                BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                //Si hemos abierto correctamente la base de datos
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Calles "+Where, null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo","Seleccione una Calle");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 10);
            }
        });

    }

    @Override
    public void onActivityResult(int requestc, int resultc, @Nullable Intent data) {
        super.onActivityResult(requestc, resultc, data);
        if(requestc==1){
            if(resultc ==1 ){
                String Servicio = data.getStringExtra("Opcion");
                Picker_TServicio.setText(Servicio);
                Id_TServicio = Catalogo.GetId("Cat_Servicios",Servicio,"id_Servicio");
            }
        }
        if(requestc==2){
            if(resultc ==1 ){
                String Usuario = data.getStringExtra("Opcion");
                Picker_TUsuario.setText(Usuario);
                Id_TUsuario = Catalogo.GetId("Cat_TiposUsuario",Usuario,"id_tipousuario");
            }
        }
        if(requestc==3){
            if(resultc ==1 ){
                String Diametro = data.getStringExtra("Opcion");
                Picker_Diametro.setText(Diametro);
                Id_Diametro = Catalogo.GetId("Cat_Diametros",Diametro,"id_diametro");
            }
        }
        if(requestc==4){
            if(resultc ==1 ){
                String Material = data.getStringExtra("Opcion");
                Picker_MaterialToma.setText(Material);
                Id_MaterialToma = Catalogo.GetId("Cat_MaterialToma",Material,"id_materialtoma");
            }
        }
        if(requestc==5){
            if(resultc ==1 ){
                String TipoToma = data.getStringExtra("Opcion");
                Picker_TipoToma.setText(TipoToma);
                Id_TipoToma = Catalogo.GetId("Cat_TiposToma",TipoToma,"id_TipoToma");
            }
        }
        if(requestc==6){
            if(resultc ==1 ){
                if(resultc ==1 ){
                    String TipoGlobo = data.getStringExtra("Opcion");
                    Picker_Globo.setText(TipoGlobo);
                    Id_Globo = Catalogo.GetId("Cat_TiposGlobo",TipoGlobo,"id_tipoglobo");
                }
            }
        }

        /***********************************************************/
        if(requestc==7){
            if(resultc ==1 ){
                String Colonia = data.getStringExtra("Opcion");
                Et_Colonia.setText(Colonia);
                Id_Colonia = Catalogo.GetId("Cat_Colonias",Colonia,"id_colonia");
                Where = "WHERE id_colonia ="+Id_Colonia;
                ResetearCalles();
                picker_vacios();
            }
        }
        if(requestc==8){
            if(resultc ==1 ){
                String Calle = data.getStringExtra("Opcion");
                Et_Calleppal.setText(Calle);
                Id_Callepal = Catalogo.GetId("Cat_Calles",Calle,"id_calle");
            }
        }
        if(requestc==9){
            if(resultc ==1 ){
                String Calle = data.getStringExtra("Opcion");
                Et_Calle1.setText(Calle);
                Id_Calle1 = Catalogo.GetId("Cat_Calles",Calle,"id_calle");
            }
        }
        if(requestc==10){
            if(resultc ==1 ){
                if(resultc ==1 ){
                    String Calle = data.getStringExtra("Opcion");
                    Et_Calle2.setText(Calle);
                    Id_Calle2 = Catalogo.GetId("Cat_Calles",Calle,"id_calle");
                }
            }
        }

        picker_vacios();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Et_ClaveLOC.getWindowToken(), 0);

    }

    public void Cargar(){

        if(Data.getClaveloc()!=null){
            if(!Data.getClaveloc().equals("")){
                Et_ClaveLOC.setText(Data.getClaveloc());
            }else{
                Et_ClaveLOC.setText(getClaveAnterior(Data.getId()));
            }
        }
        else{
            Et_ClaveLOC.setText(getClaveAnterior(Data.getId()));
        }


        if(Data.getId_servicio()!=null)
            if(!Data.getId_servicio().equals("")){
                Picker_TServicio.setText(Catalogo.GetText("Cat_Servicios",Data.getId_servicio(),"id_Servicio"));
                Id_TServicio = Data.getId_servicio();
            }

        if(Data.getId_tipousuario()!=null)
            if(!Data.getId_tipousuario().equals("")){
                Picker_TUsuario.setText(Catalogo.GetText("Cat_TiposUsuario",Data.getId_tipousuario(),"id_tipousuario"));
                Id_TUsuario = Data.getId_tipousuario();
            }

        if(Data.getId_diametro()!=null)
            if(!Data.getId_diametro().equals("")){
                Picker_Diametro.setText(Catalogo.GetText("Cat_Diametros",Data.getId_diametro(),"id_diametro"));
                Id_Diametro = Data.getId_diametro();
            }

        if(Data.getId_materialtoma()!=null)
            if(!Data.getId_materialtoma().equals("")){
                Picker_MaterialToma.setText(Catalogo.GetText("Cat_MaterialToma",Data.getId_materialtoma(),"id_materialtoma"));
                Id_MaterialToma = Data.getId_materialtoma();
            }

        if(Data.getId_tipotoma()!=null)
            if(!Data.getId_tipotoma().equals("")){
                Picker_TipoToma.setText(Catalogo.GetText("Cat_TiposToma",Data.getId_tipotoma(),"id_TipoToma"));
                Id_TipoToma = Data.getId_tipotoma();
            }

        if(Data.getId_tipoglobo()!=null)
            if(!Data.getId_tipoglobo().equals("")){
                Picker_Globo.setText(Catalogo.GetText("Cat_TiposGlobo",Data.getId_tipoglobo(),"id_tipoglobo"));
                Id_Globo=Data.getId_tipoglobo();
            }

        /*****************************************************************************************************/
        if(Data.getId_colonia()!=null)
            if(!Data.getId_colonia().equals("")){
                Et_Colonia.setText(Catalogo.GetText("Cat_Colonias", Data.getId_colonia(), "id_colonia"));
                Id_Colonia = Data.getId_colonia();
            }

        if(Data.getId_calle_ppal()!=null)
            if(!Data.getId_calle_ppal().equals("")){
                Et_Calleppal.setText(Catalogo.GetText("Cat_Calles",Data.getId_calle_ppal(),"id_calle"));
                Id_Callepal= Data.getId_calle_ppal();
            }

        if(Data.getId_calle_lat1()!=null)
            if(!Data.getId_calle_lat1().equals("")){
                Et_Calle1.setText(Catalogo.GetText("Cat_Calles",Data.getId_calle_lat1(),"id_calle"));
                Id_Calle1= Data.getId_calle_lat1();
            }

        if(Data.getId_calle_lat2()!=null)
            if(!Data.getId_calle_lat2().equals("")){
                Et_Calle2.setText(Catalogo.GetText("Cat_Calles",Data.getId_calle_lat2(),"id_calle"));
                Id_Calle2= Data.getId_calle_lat2();
            }

        Et_Numext.setText(Data.getNum_ext());
        Et_Numint.setText(Data.getNum_int());

    }


    public void picker_vacios(){
        if(Id_TServicio.equals(""))
            Picker_TServicio.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_TServicio.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_TUsuario.equals(""))
            Picker_TUsuario.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_TUsuario.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_Diametro.equals(""))
            Picker_Diametro.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_Diametro.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_MaterialToma.equals(""))
            Picker_MaterialToma.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_MaterialToma.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_TipoToma.equals(""))
            Picker_TipoToma.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_TipoToma.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_Globo.equals(""))
            Picker_Globo.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_Globo.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        ///////////////////////////////////////////////////////////////////////////////////////////////////

        if(Id_Colonia.equals(""))
            Et_Colonia.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Et_Colonia.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_Callepal.equals(""))
            Et_Calleppal.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Et_Calleppal.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_Calle1.equals(""))
            Et_Calle1.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Et_Calle1.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        /*if(Id_Calle2.equals(""))
            Et_Calle2.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Et_Calle2.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));*/

    }


    public void ResetearCalles(){
        Id_Callepal = "";
        Id_Calle1 = "";
        Id_Calle2 = "";
        Et_Calleppal.setText("Calle Principal");
        Et_Calle1.setText("Entre Calle 1");
        Et_Calle2.setText("Entre Calle 2");
    }

    public String getClaveAnterior(String Id){
        //Id = ""+(Integer.parseInt(Id)-1);
        //Abrimos la base de datos 'DBUsuarios' en modo Lectura
        BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT clave_loc FROM OPR_USUARIOS WHERE id="+Id, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                db.close();
                return c.getString(0);
            }
            //Cerramos la base de datos
        }
        return "";
    }


    @Override
    public void setData(OPR_USUARIOS data) {
        this.Data = data;
    }

    @Override
    public OPR_USUARIOS getData() {
        Data.setId_servicio(Id_TServicio);
        Data.setId_tipousuario(Id_TUsuario);
        Data.setId_diametro(Id_Diametro);
        Data.setId_materialtoma(Id_MaterialToma);
        Data.setId_tipotoma(Id_TipoToma);
        Data.setId_tipoglobo(Id_Globo);

        Data.setId_colonia(Id_Colonia);
        Data.setId_calle_ppal(Id_Callepal);
        Data.setId_calle_lat1(Id_Calle1);
        Data.setId_calle_lat2(Id_Calle2);

        Data.setNum_ext(Et_Numext.getText().toString());
        Data.setNum_int(Et_Numint.getText().toString());
        Data.setClaveloc(Et_ClaveLOC.getText().toString());

        return this.Data;
    }
}