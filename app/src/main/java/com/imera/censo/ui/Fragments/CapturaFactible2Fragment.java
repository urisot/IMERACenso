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

public class CapturaFactible2Fragment extends Fragment implements DataUpdate {

    InterfaceTiposWizard Listener;
    Switch Registro_Banqueta;
    TextView Picker_MaterialBanqueta;
    TextView Picker_MaterialCalle;
    String Id_MaterialBanqueta = "";
    String Id_MaterialCalle = "";
    Spinner et_FactibilidadT;

    OPR_USUARIOS Data;
    View view;

    public CapturaFactible2Fragment() {
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
        view = inflater.inflate(R.layout.fragment_captura_factible2, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {

            Listener = (InterfaceTiposWizard) getActivity();

        } catch (ClassCastException e) {

        }

        et_FactibilidadT=(Spinner)getView().findViewById(R.id.picker_factibilidadTecnica);
        Picker_MaterialBanqueta = (TextView)getView().findViewById(R.id.picker_MaterialBanqueta);
        Picker_MaterialCalle = (TextView)getView().findViewById(R.id.picker_MaterialCalle);

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

                    //Cerramos la base de datos
                    db.close();
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione Material");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 2);
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
        picker_vacios();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Registro_Banqueta.getWindowToken(), 0);
    }

    @Override
    public void setData(OPR_USUARIOS data) {
        this.Data = data;
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

        et_FactibilidadT.setSelection(1);
        if(!et_FactibilidadT.getSelectedItem().equals(Data.getFac_tecnica()))
            et_FactibilidadT.setSelection(0);

        if(Data.getRegistro_banqueta()!=null)
            if(!Data.getRegistro_banqueta().equals(""))
            {
                if (Integer.parseInt(Data.getRegistro_banqueta())==1)
                    Registro_Banqueta.setChecked(true);
            }
    }


    public void picker_vacios(){
        if(Id_MaterialBanqueta.equals(""))
            Picker_MaterialBanqueta.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_MaterialBanqueta.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

        if(Id_MaterialCalle.equals(""))
            Picker_MaterialCalle.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_MaterialCalle.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));
    }

    @Override
    public OPR_USUARIOS getData() {
        Data.setId_mat_banqueta(Id_MaterialBanqueta);
        Data.setId_mat_calle(Id_MaterialCalle);
        Data.setFac_tecnica(et_FactibilidadT.getSelectedItem().toString());

        if(Registro_Banqueta.isChecked())Data.setRegistro_banqueta("1");else Data.setRegistro_banqueta("0");

        return this.Data;
    }
}