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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.imera.censo.App;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.BosinessCore.DomainObject.CondicionPredioDomainObject;
import com.imera.censo.BosinessCore.DomainObject.TiposCasaDomainObject;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.CondicionPredioEnum;
import com.imera.censo.Contracts.Enums.DiametroEnum;
import com.imera.censo.Contracts.Enums.MarcaEnum;
import com.imera.censo.Contracts.Enums.MaterialEnum;
import com.imera.censo.Contracts.Enums.MaterialTomaEnum;
import com.imera.censo.Contracts.Enums.ModeloEnum;
import com.imera.censo.Contracts.Enums.ServicioEnum;
import com.imera.censo.Contracts.Enums.TipoCasaEnum;
import com.imera.censo.Contracts.Enums.TipoGloboEnum;
import com.imera.censo.Contracts.Enums.TipoTomaEnum;
import com.imera.censo.Contracts.Enums.TipoUsuarioEnum;
import com.imera.censo.Contracts.Models.Catalogo;
import com.imera.censo.Contracts.Models.Diametro;
import com.imera.censo.Contracts.Models.MaterialToma;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Models.TipoCasa;
import com.imera.censo.Contracts.Services.DataUpdate;
import com.imera.censo.Contracts.Services.ICondicionPredio;
import com.imera.censo.Contracts.Services.ITipoCasa;
import com.imera.censo.Contracts.Services.InterfaceTiposWizard;
import com.imera.censo.R;
import com.imera.censo.ui.Activities.EdicionNormalActivity;
import com.imera.censo.ui.Activities.PickerActivity;

import java.util.ArrayList;

public class CapturaNormal1Fragment extends Fragment implements DataUpdate {

    InterfaceTiposWizard Listener;

    TextInputLayout tilClaveLoc;
    EditText Et_ClaveLOC;
    TextInputLayout tilMedidorIns;
    EditText Et_Medidor_Ins;
    TextInputLayout tilLectura;
    EditText Et_Lectura;

    TextView Picker_TServicio;
    TextView Picker_TUsuario;

    TextView Picker_CPredio;
    TextView Picker_TCasa;

    TextView Picker_Diametro;
    TextView Picker_MaterialToma;

    TextView Picker_TipoToma;
    TextView Picker_Globo;

    TextView Picker_Marca;
    TextView Picker_Modelo;

    TextView Label_Info_Toma;

    TextView Label_Diametro;
    TextView Label_MaterialToma;

    TextView Label_TipoToma;
    TextView Label_Globo;

    TextView Label_Marca;
    TextView Label_Modelo;

    Switch Hay_Medidor;

    String Id_TServicio = "";
    String Id_TUsuario = "";
    String Id_CPredio = "";
    String Id_TCasa = "";

    String Id_Diametro = "";
    String Id_MaterialToma = "";

    String Id_TipoToma = "";
    String Id_Globo = "";

    String Id_Marca = "";
    String Id_Modelo = "";

    OPR_USUARIOS Data;
    private View root;

    public CapturaNormal1Fragment() {
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
        root = inflater.inflate(R.layout.fragment_captura_normal, container, false);
        //Instanciando Controles
        Picker_TServicio = (TextView)root.findViewById(R.id.picker_TipoServicio);
        Picker_TUsuario = (TextView)root.findViewById(R.id.picker_TipoUsuario);

        Picker_CPredio = (TextView)root.findViewById(R.id.picker_CondicionPredio);
        Picker_TCasa = (TextView)root.findViewById(R.id.picker_TipoCasa);

        Picker_Diametro = (TextView)root.findViewById(R.id.picker_DiametroToma);
        Picker_MaterialToma = (TextView)root.findViewById(R.id.picker_MaterialToma);

        Picker_TipoToma = (TextView)root.findViewById(R.id.picker_TipoToma);
        Picker_Globo = (TextView)root.findViewById(R.id.picker_TipoGlobo);

        Picker_Marca = (TextView)root.findViewById(R.id.et_Marca);
        Picker_Modelo  = (TextView)root.findViewById(R.id.et_Modelo);

        Label_Info_Toma = (TextView)root.findViewById(R.id.txt_subtitulo4);

        Label_Diametro = (TextView)root.findViewById(R.id.lbl_DiametroToma);
        Label_MaterialToma = (TextView)root.findViewById(R.id.lbl_MaterialToma);

        Label_TipoToma = (TextView)root.findViewById(R.id.lbl_TipoToma);
        Label_Globo = (TextView)root.findViewById(R.id.lbl_TipoGlobo);

        Label_Marca = (TextView)root.findViewById(R.id.lbl_Marca);
        Label_Modelo  = (TextView)root.findViewById(R.id.lbl_Modelo);

        Hay_Medidor = (Switch)root.findViewById(R.id.sw_medidor);

        /****************************************************************************************************/
        tilClaveLoc = (TextInputLayout)root.findViewById(R.id.tilClaveLoc);
        Et_ClaveLOC = (EditText)root.findViewById(R.id.etClaveLoc);
        tilMedidorIns  = (TextInputLayout)root.findViewById(R.id.tilMediorIns);
        Et_Medidor_Ins= (EditText)root.findViewById(R.id.etMediorIns);
        tilLectura = (TextInputLayout)root.findViewById(R.id.tilLectura);
        Et_Lectura = (EditText)root.findViewById(R.id.etLectura);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            Listener = (InterfaceTiposWizard) getActivity();
            EdicionNormalActivity EN = (EdicionNormalActivity)getActivity();


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
                    final char c = s.charAt(s.length());

                    if(space == c)
                    s.delete(s.length(), s.length()+1);

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

        Hay_Medidor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    Et_Medidor_Ins.setVisibility(View.VISIBLE);
                    Picker_Marca.setVisibility(View.VISIBLE);
                    Picker_Modelo.setVisibility(View.VISIBLE);

                    Label_Marca.setVisibility(View.VISIBLE);
                    Label_Modelo.setVisibility(View.VISIBLE);

                    if (Data.getId_marca_ins() != null)
                        if (!Data.getId_marca_ins().equals("")) {
                            Picker_Marca.setText(Data.getId_marca_ins() + " " + Catalogo.GetText("Cat_Marcas", Data.getId_marca_ins(), "id_marca"));
                            Id_Marca = Data.getId_marca_ins();
                        }

                    if (Data.getId_modelo_ins() != null)
                        if (!Data.getId_modelo_ins().equals("")) {
                            Picker_Modelo.setText(Data.getId_modelo_ins() + " " + Catalogo.GetText("Cat_Modelos", Data.getId_modelo_ins(), "id_modelo"));
                            Id_Modelo = Data.getId_modelo_ins();
                        }

                    Et_Medidor_Ins.setText(Data.getMedidor_ins());

                    picker_vacios();
                    Data.setMedidor(true);

                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(Et_Medidor_Ins.getWindowToken(), 0);

                    Et_Medidor_Ins.setVisibility(View.GONE);
                    Picker_Marca.setVisibility(View.GONE);
                    Picker_Modelo.setVisibility(View.GONE);

                    Label_Marca.setVisibility(View.GONE);
                    Label_Modelo.setVisibility(View.GONE);

                    /*Et_Medidor_Ins.setText("");
                    Id_Marca = "";
                    Id_Modelo = "";

                    Picker_Marca.setText("Marca");
                    Picker_Modelo.setText("Modelo");*/

                    Data.setMedidor(false);
                }
            }
        });

        Cargar();
        picker_vacios();

        Picker_TCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ITipoCasa tCasaService = new TiposCasaDomainObject();

                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                Al_DP_Data = tCasaService.TipoCasaGetListPicker();

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo","Seleccione un Tipo de Casa");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, Constants.SOLICITUD_TIPO_CASA);
            }
        });

        Picker_CPredio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ICondicionPredio cPredioServ = new CondicionPredioDomainObject();

                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                Al_DP_Data = cPredioServ.CondicionPredioGetListPicker();

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo","Seleccione una condicion del predio");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, Constants.SOLICITUD_CONDICION_PREDIO);
            }
        });

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
                manejadordp.putString("Titulo","Seleccione un Tipo de Servicio");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, Constants.SOLICITUD_TIPO_SERVICIO);
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
                startActivityForResult(intento, Constants.SOLICITUD_TIPO_USUARIO);
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
                startActivityForResult(intento, Constants.SOLICITUD_DIAMETRO);
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
                startActivityForResult(intento, Constants.SOLICITUD_MATERIAL_TOMA);
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
                startActivityForResult(intento, Constants.SOLICITUD_TIPO_TOMA);
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
                startActivityForResult(intento, Constants.SOLICITUD_TIPO_GLOBO);
            }
        });

        Picker_Marca.setOnClickListener(new View.OnClickListener() {
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
                    Cursor c = db.rawQuery("SELECT id_marca,descripcion FROM Cat_Marcas", null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0)+" "+c.getString(1));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione una Marca");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, Constants.SOLICITUD_MARCA);
            }
        });

        Picker_Modelo.setOnClickListener(new View.OnClickListener() {
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
                    Cursor c = db.rawQuery("SELECT id_modelo,descripcion FROM Cat_Modelos", null);

                    //Nos aseguramos de que existe al menos un registro
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya mas registros
                        do {
                            Al_DP_Data.add(c.getString(0)+" "+c.getString(1));
                        } while (c.moveToNext());
                    }

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo","Seleccione un Modelo");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, Constants.SOLICITUD_MODELO);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.SOLICITUD_TIPO_SERVICIO){
            if(resultCode == Constants.RESULTADO_OK ){
                String Servicio = data.getStringExtra("Opcion");
                Picker_TServicio.setText(Servicio);
                Id_TServicio = Catalogo.GetId("Cat_Servicios",Servicio,"id_Servicio");
            }
        }
        if(requestCode==Constants.SOLICITUD_TIPO_CASA){
            if(resultCode == Constants.RESULTADO_OK ){
                String Servicio = data.getStringExtra("Opcion");
                Picker_TCasa.setText(Servicio);
                Id_TCasa = Catalogo.GetId(getContext().getString(R.string.cat_tipocasa),Servicio, TipoCasaEnum.id_tipocasa.toString());
            }
        }
        if(requestCode==Constants.SOLICITUD_CONDICION_PREDIO){
            if(resultCode == Constants.RESULTADO_OK ){
                String Servicio = data.getStringExtra("Opcion");
                Picker_CPredio.setText(Servicio);
                Id_CPredio = Catalogo.GetId(getContext().getString(R.string.cat_condicionpredio),Servicio, CondicionPredioEnum.id_condicionpredio.toString());
            }
        }
        if(requestCode==Constants.SOLICITUD_TIPO_USUARIO){
            if(resultCode ==Constants.RESULTADO_OK ){
                String Usuario = data.getStringExtra("Opcion");
                Picker_TUsuario.setText(Usuario);
                Id_TUsuario = Catalogo.GetId("Cat_TiposUsuario",Usuario,"id_tipousuario");
            }
        }
        if(requestCode==Constants.SOLICITUD_DIAMETRO){
            if(resultCode == Constants.RESULTADO_OK ){
                String Diametro = data.getStringExtra("Opcion");
                Picker_Diametro.setText(Diametro);
                Id_Diametro = Catalogo.GetId("Cat_Diametros",Diametro,"id_diametro");
            }
        }
        if(requestCode==Constants.SOLICITUD_MATERIAL_TOMA){
            if(resultCode == Constants.RESULTADO_OK ){
                String Material = data.getStringExtra("Opcion");
                Picker_MaterialToma.setText(Material);
                Id_MaterialToma = Catalogo.GetId("Cat_MaterialToma",Material,"id_materialtoma");
            }
        }
        if(requestCode==Constants.SOLICITUD_TIPO_TOMA){
            if(resultCode == Constants.RESULTADO_OK ){
                String TipoToma = data.getStringExtra("Opcion");
                Picker_TipoToma.setText(TipoToma);
                Id_TipoToma = Catalogo.GetId("Cat_TiposToma",TipoToma,"id_TipoToma");
            }
        }
        if(requestCode==Constants.SOLICITUD_TIPO_GLOBO){
            if(resultCode == Constants.RESULTADO_OK ){

                    String TipoGlobo = data.getStringExtra("Opcion");
                    Picker_Globo.setText(TipoGlobo);
                    Id_Globo = Catalogo.GetId("Cat_TiposGlobo",TipoGlobo,"id_tipoglobo");

            }
        }
        if(requestCode==Constants.SOLICITUD_MARCA){
            if(resultCode == Constants.RESULTADO_OK ){
                    String Marca = data.getStringExtra("Opcion");//Data
                    String[] parts = Marca.split(" ");
                    String part1 = parts[0]; // Id
                    String part2 = parts[1]; // Descripcion

                    Picker_Marca.setText(Marca);
                    Id_Marca = part1;

                    /*String Marca = data.getStringExtra("Opcion");
                    Picker_Marca.setText(Marca);
                    Id_Marca = GetId("Cat_Marcas",Marca,"id_marca");*/

            }
        }
        if(requestCode==Constants.SOLICITUD_MODELO){
            if(resultCode == Constants.RESULTADO_OK ){


                    String Modelo = data.getStringExtra("Opcion");//Data
                    String[] parts = Modelo.split(" ");
                    String part1 = parts[0]; // Id
                    String part2 = parts[1]; // Descripcion

                    Picker_Modelo.setText(Modelo);
                    Id_Modelo = part1;

                    /*String Modelo = data.getStringExtra("Opcion");
                    Picker_Modelo.setText(Modelo);
                    Id_Modelo = GetId("Cat_Modelos",Modelo,"id_modelo");*/

            }
        }

        picker_vacios();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Et_ClaveLOC.getWindowToken(), 0);
    }

    public void Cargar(){
        if(Data.getClaveloc()!=null)
            if(!Data.getClaveloc().equals("")){
                Et_ClaveLOC.setText(Data.getClaveloc());
            }

        if(Data.getId_servicio()!=null)
            if(!Data.getId_servicio().equals("")){
                Picker_TServicio.setText(Catalogo.GetText(getContext().getString(R.string.cat_servicios)
                        , Data.getId_servicio()
                        , ServicioEnum.id_Servicio.toString()));
                Id_TServicio = Data.getId_servicio();
            }

        if(Data.getId_tipousuario()!=null)
            if(!Data.getId_tipousuario().equals("")){
                Picker_TUsuario.setText(Catalogo.GetText(getContext().getString(R.string.cat_tiposusuario)
                        , Data.getId_tipousuario()
                        , TipoUsuarioEnum.id_tipousuario.toString()));
                Id_TUsuario = Data.getId_tipousuario();
            }

        if(Data.getId_tipocasa()!=null)
            if(!Data.getId_tipocasa().equals("")){
                Picker_TCasa.setText(Catalogo.GetText(getContext().getString(R.string.cat_tipocasa)
                        , Data.getId_tipocasa()
                        , TipoCasaEnum.id_tipocasa.toString()));
                Id_TCasa = Data.getId_tipocasa();
            }

        if(Data.getId_condicionpredio()!=null)
            if(!Data.getId_condicionpredio().equals("")){
                Picker_CPredio.setText(Catalogo.GetText(getContext().getString(R.string.cat_condicionpredio)
                        , Data.getId_condicionpredio()
                        , CondicionPredioEnum.id_condicionpredio.toString()));
                Id_CPredio = Data.getId_tipocasa();
            }

        //     Medidor

        if(Data.getMedidor_ins()!=null)
            if(!Data.getMedidor_ins().equals("")){
                //Et_Medidor_Ins.setText(Data.getMedidor_ins());
                Hay_Medidor.setChecked(true);
            }

        //     Fin Medidor

        if(Data.getId_marca_ins()!=null)
            if(!Data.getId_marca_ins().equals("")){
                Picker_Marca.setText(Data.getId_marca_ins()+" "+Catalogo.GetText(getContext().getString(R.string.cat_marcas)
                        ,Data.getId_marca_ins()
                        , MarcaEnum.id_marca.toString()));
                Picker_Marca.setText(Data.getId_marca_ins());
                Id_Marca = Data.getId_marca_ins();
            }

        if(Data.getId_modelo_ins()!=null)
            if(!Data.getId_modelo_ins().equals("")){
                Picker_Modelo.setText(Data.getId_modelo_ins()+" "+Catalogo.GetText(getContext().getString(R.string.cat_modelos)
                        ,Data.getId_modelo_ins()
                        , ModeloEnum.id_modelo.toString()));
                Id_Modelo = Data.getId_modelo_ins();
            }

        if(Data.getId_diametro()!=null)
            if(!Data.getId_diametro().equals("")){
                Picker_Diametro.setText(Catalogo.GetText(getContext().getString(R.string.cat_diametros)
                        , Data.getId_diametro()
                        , DiametroEnum.id_diametro.toString()));
                Id_Diametro = Data.getId_diametro();
            }

        if(Data.getId_materialtoma()!=null)
            if(!Data.getId_materialtoma().equals("")){
                Picker_MaterialToma.setText(Catalogo.GetText(getContext().getString(R.string.cat_materialtoma)
                        , Data.getId_materialtoma(), MaterialTomaEnum.id_materialtoma.toString()));
                Id_MaterialToma = Data.getId_materialtoma();
            }

        if(Data.getId_tipotoma()!=null)
            if(!Data.getId_tipotoma().equals("")){
                Picker_TipoToma.setText(Catalogo.GetText(getContext().getString(R.string.cat_tipostoma)
                        , Data.getId_tipotoma(), TipoTomaEnum.id_TipoToma.toString()));
                Id_TipoToma = Data.getId_tipotoma();
            }

        if(Data.getId_tipoglobo()!=null)
            if(!Data.getId_tipoglobo().equals("")){
                Picker_Globo.setText(Catalogo.GetText(getContext().getString(R.string.cat_tiposglobo)
                        , Data.getId_tipoglobo(), TipoGloboEnum.id_tipoglobo.toString()));
                Id_Globo=Data.getId_tipoglobo();
            }

        Et_Medidor_Ins.setText(Data.getMedidor_ins());
        Et_Lectura.setText(Data.getLectura_med_ins());

        Hay_Medidor.setChecked(!Data.isMedidor());
        Hay_Medidor.performClick();

    }

    //Informar No Seleccion
    public void picker_vacios(){
        if(Id_TServicio.equals(""))
            Picker_TServicio.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_TServicio.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_TUsuario.equals(""))
            Picker_TUsuario.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_TUsuario.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        /*if(Id_Marca.equals(""))
            Picker_Marca.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_Marca.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));


        if(Id_Modelo.equals(""))
            Picker_Modelo.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_Modelo.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));*/
        if(Id_CPredio.equals(""))
            Picker_CPredio.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_CPredio.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_TCasa.equals(""))
            Picker_TCasa.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_TCasa.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

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
    }

    @Override
    public void setData(OPR_USUARIOS data) {
        this.Data = data;
    }

    @Override
    public OPR_USUARIOS getData() {
        Data.setId_servicio(Id_TServicio);
        Data.setId_tipousuario(Id_TUsuario);
        Data.setId_marca_ins(Id_Marca);
        Data.setId_modelo_ins(Id_Modelo);
        Data.setId_diametro(Id_Diametro);
        Data.setId_tipocasa(Id_TCasa);
        Data.setId_condicionpredio(Id_CPredio);
        Data.setId_materialtoma(Id_MaterialToma);
        Data.setId_tipotoma(Id_TipoToma);
        Data.setId_tipoglobo(Id_Globo);
        Data.setMedidor_ins(Et_Medidor_Ins.getText().toString());
        Data.setLectura_med_ins(Et_Lectura.getText().toString());
        Data.setClaveloc(Et_ClaveLOC.getText().toString());

        if(!Data.isMedidor()){
            Data.setId_marca_ins("");
            Data.setId_modelo_ins("");
            Data.setMedidor_ins("");
        }

        return this.Data;
    }
}