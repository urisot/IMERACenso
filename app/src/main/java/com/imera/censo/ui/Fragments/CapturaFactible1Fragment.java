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
import android.widget.Switch;
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

public class CapturaFactible1Fragment extends Fragment implements DataUpdate {

    InterfaceTiposWizard Listener;

    TextInputLayout tilClaveLOC;
    EditText Et_ClaveLOC;
    TextView Et_Colonia;
    TextView Et_Calleppal;
    TextInputLayout tilNumExt;
    EditText Et_Numext;
    TextInputLayout tilNumInt;
    EditText Et_Numint;
    TextView Et_Calle1;
    TextView Et_Calle2;

    String Id_Colonia = "";
    String Id_Callepal = "";
    String Id_Calle1 = "";
    String Id_Calle2 = "";
    String Where = "";

    OPR_USUARIOS Data;

    View root;

    public CapturaFactible1Fragment() {
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
        root = inflater.inflate(R.layout.fragment_captura_factible1, container, false);

        tilClaveLOC = (TextInputLayout)root.findViewById(R.id.tilClaveLoc);
        Et_ClaveLOC = (EditText)root.findViewById(R.id.etClaveLoc);
        Et_Colonia = (TextView)root.findViewById(R.id.et_Colonia);
        Et_Calleppal = (TextView)root.findViewById(R.id.et_CallePal);
        tilNumExt = (TextInputLayout)root.findViewById(R.id.tilNumE);
        Et_Numext = (EditText)root.findViewById(R.id.etNumE);
        tilNumInt = (TextInputLayout)root.findViewById(R.id.tilNumI);
        Et_Numint = (EditText)root.findViewById(R.id.etNumI);
        Et_Calle1 = (TextView)root.findViewById(R.id.et_Calle1);
        Et_Calle2 = (TextView)root.findViewById(R.id.et_Calle2);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            Listener = (InterfaceTiposWizard) getActivity();
        } catch (ClassCastException e) {
            //
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
                manejadordp.putString("Titulo", "Seleccione una Calle");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 10);
            }
        });
    }

    @Override
    public void onActivityResult(int requestc, int resultc, @Nullable Intent data) {
        super.onActivityResult(requestc, resultc, data);

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
                String Calle = data.getStringExtra("Opcion");
                Et_Calle2.setText(Calle);
                Id_Calle2 = Catalogo.GetId("Cat_Calles",Calle,"id_calle");
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
        }else{
            Et_ClaveLOC.setText(getClaveAnterior(Data.getId()));
        }

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

    //Informar No Seleccion
    public void picker_vacios(){
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