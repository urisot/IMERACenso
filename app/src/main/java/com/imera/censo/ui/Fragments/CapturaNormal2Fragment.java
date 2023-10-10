package com.imera.censo.ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Contracts.Models.Catalogo;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Services.DataUpdate;
import com.imera.censo.Contracts.Services.InterfaceTiposWizard;
import com.imera.censo.R;
import com.imera.censo.ui.Activities.PickerActivity;

import java.util.ArrayList;


public class CapturaNormal2Fragment extends Fragment implements DataUpdate {

    InterfaceTiposWizard Listener;

    CheckBox cbx_IEOpcion1;
    CheckBox cbx_IEOpcion2;
    CheckBox cbx_IEOpcion3;
    CheckBox cbx_IEOpcion4;
    CheckBox cbx_IEOpcion5;

    Switch Registro_Banqueta;

    TextView Picker_MaterialBanqueta;
    TextView Picker_MaterialCalle;
    TextView Picker_Anomalia;
    Spinner et_FactibilidadT;

    String Id_MaterialBanqueta = "";
    String Id_MaterialCalle = "";
    String Id_Anomalia = "";

    OPR_USUARIOS Data;

    public CapturaNormal2Fragment() {
        // Required empty public constructor
    }


    private View root;


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
        root = inflater.inflate(R.layout.fragment_captura_normal2, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            Listener = (InterfaceTiposWizard) getActivity();
        } catch (ClassCastException e) {
        }

        Picker_MaterialBanqueta = (TextView)getView().findViewById(R.id.picker_MaterialBanqueta);
        Picker_MaterialCalle = (TextView)getView().findViewById(R.id.picker_MaterialCalle);
        Picker_Anomalia = (TextView)getView().findViewById(R.id.picker_AnomaliaEncontrada);

        /*******************************************************************************************************/
        et_FactibilidadT=(Spinner)getView().findViewById(R.id.picker_factibilidadTecnica);

        cbx_IEOpcion1 = (CheckBox)getView().findViewById(R.id.cb_Tinaco);
        cbx_IEOpcion2 = (CheckBox)getView().findViewById(R.id.cb_Cisterna);
        cbx_IEOpcion3 = (CheckBox)getView().findViewById(R.id.cb_Alberca);
        cbx_IEOpcion4 = (CheckBox)getView().findViewById(R.id.cb_Pozo);
        cbx_IEOpcion5 = (CheckBox)getView().findViewById(R.id.cb_Otros);

        Registro_Banqueta = (Switch)getView().findViewById(R.id.sw_RegistroBanqueta);

        Cargar();
        picker_vacios();

        Picker_MaterialBanqueta.setOnClickListener(new View.OnClickListener() {
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
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Materiales", null);

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
                manejadordp.putString("Titulo", "Seleccione Material");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 1);
            }
        });

        Picker_MaterialCalle.setOnClickListener(new View.OnClickListener() {
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
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Materiales", null);

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
                manejadordp.putString("Titulo", "Seleccione Material");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 2);
            }
        });

        Picker_Anomalia.setOnClickListener(new View.OnClickListener() {
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
                    Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Anomalias", null);

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
                manejadordp.putString("Titulo", "Seleccione una Anomalia");
                intento.putExtras(manejadordp);
                startActivityForResult(intento,3);
            }
        });
    }

    @Override
    public void onActivityResult(int requestc, int resultc, @Nullable Intent data) {
        super.onActivityResult(requestc, resultc, data);
        if(requestc==1){
            if(resultc ==1 ){
                String MBanqueta = data.getStringExtra("Opcion");
                Picker_MaterialBanqueta.setText(MBanqueta);
                Id_MaterialBanqueta = Catalogo.GetId("Cat_Materiales",MBanqueta,"id_material");
            }
        }
        if(requestc==2){
            if(resultc ==1 ){
                String MCalle = data.getStringExtra("Opcion");
                Picker_MaterialCalle.setText(MCalle);
                Id_MaterialCalle = Catalogo.GetId("Cat_Materiales",MCalle,"id_material");
            }
        }
        if(requestc==3){
            if(resultc ==1 ){
                String Anomalia = data.getStringExtra("Opcion");
                Picker_Anomalia.setText(Anomalia);
                Id_Anomalia = Catalogo.GetId("Cat_Anomalias",Anomalia,"id_anomalia");
            }
        }
        picker_vacios();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(et_FactibilidadT.getWindowToken(), 0);
    }

    public void Cargar(){
        if(Data.getId_mat_banqueta()!=null)
            if(!Data.getId_mat_banqueta().equals("")){
                Picker_MaterialBanqueta.setText(Catalogo.GetText("Cat_Materiales",Data.getId_mat_banqueta(),"id_material"));
                Id_MaterialBanqueta = Data.getId_mat_banqueta();
            }

        if(Data.getId_mat_calle()!=null)
            if(!Data.getId_mat_calle().equals("")){
                Picker_MaterialCalle.setText(Catalogo.GetText("Cat_Materiales",Data.getId_mat_calle(),"id_material"));
                Id_MaterialCalle = Data.getId_mat_calle();
            }

        if(Data.getId_anomalia()!=null)
            if(!Data.getId_anomalia().equals("")){
                Picker_Anomalia.setText(Catalogo.GetText("Cat_Anomalias",Data.getId_anomalia(), "id_anomalia"));
                Id_Anomalia = Data.getId_anomalia();
            }

        et_FactibilidadT.setSelection(1);
        if(!et_FactibilidadT.getSelectedItem().equals(Data.getFac_tecnica()))
            et_FactibilidadT.setSelection(0);

        if(Data.getTinaco()!=null)
            if(!Data.getTinaco().equals(""))
            {
                if(Integer.parseInt(Data.getTinaco())==1)
                    cbx_IEOpcion1.setChecked(true);
            }

        if(Data.getCisterna()!=null)
            if(!Data.getCisterna().equals(""))
            {
                if(Integer.parseInt(Data.getCisterna())==1)
                    cbx_IEOpcion2.setChecked(true);
            }

        if(Data.getAlberca()!=null)
            if(!Data.getAlberca().equals(""))
            {
                if(Integer.parseInt(Data.getAlberca())==1)
                    cbx_IEOpcion3.setChecked(true);
            }

        if(Data.getPozo()!=null)
            if(!Data.getPozo().equals(""))
            {
                if(Integer.parseInt(Data.getPozo())==1)
                    cbx_IEOpcion4.setChecked(true);
            }

        if(Data.getOtro()!=null)
            if(!Data.getOtro().equals(""))
            {
                if(Integer.parseInt(Data.getOtro())==1)
                    cbx_IEOpcion5.setChecked(true);
            }
        if(Data.getRegistro_banqueta()!=null)
            if(!Data.getRegistro_banqueta().equals(""))
            {
                if (Integer.parseInt(Data.getRegistro_banqueta())==1)
                    Registro_Banqueta.setChecked(true);
            }
    }

    //Informar No Seleccion
    public void picker_vacios(){
        if(Id_MaterialBanqueta.equals(""))
            Picker_MaterialBanqueta.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_MaterialBanqueta.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_MaterialCalle.equals(""))
            Picker_MaterialCalle.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_MaterialCalle.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_Anomalia.equals(""))
            Picker_Anomalia.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_Anomalia.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

    }

    @Override
    public void setData(OPR_USUARIOS data) {
        this.Data = data;
    }

    @Override
    public OPR_USUARIOS getData() {
        Data.setFac_tecnica(et_FactibilidadT.getSelectedItem().toString());

        Data.setId_mat_banqueta(Id_MaterialBanqueta);
        Data.setId_mat_calle(Id_MaterialCalle);
        Data.setId_anomalia(Id_Anomalia);

        if(cbx_IEOpcion1.isChecked())Data.setTinaco("1");else Data.setTinaco("0");

        if(cbx_IEOpcion2.isChecked())Data.setCisterna("1");else Data.setCisterna("0");

        if(cbx_IEOpcion3.isChecked())Data.setAlberca("1");else Data.setAlberca("0");

        if(cbx_IEOpcion4.isChecked())Data.setPozo("1");else Data.setPozo("0");

        if(cbx_IEOpcion5.isChecked())Data.setOtro("1");else Data.setOtro("0");

        if(Registro_Banqueta.isChecked())Data.setRegistro_banqueta("1");else Data.setRegistro_banqueta("0");

        return this.Data;
    }
}