package com.imera.censo.ui.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Contracts.Services.OnRutaSelectListener;
import com.imera.censo.R;
import com.imera.censo.ui.Adapters.AdaptadorRutas;

import java.util.ArrayList;

public class ListadoRutasFragment extends Fragment {
    private android.widget.Button btn_Tab1;
    private android.widget.Button btn_Tab2;
    private android.widget.Button btn_Tab3;
    private FloatingActionButton btn_Iniciar_Censo;

    private ListView lv_Rutas;
    private ImageView img_No_Hay;
    private TextView txt_No_Hay;

    private ArrayList<AdaptadorRutas.ItemListaRutas> AL_Rutas = new ArrayList();
    private ArrayList<String>Ids_OPR_USUARIOS = new ArrayList<>();
    private AdaptadorRutas Adaptador;

    private boolean HayRutas = false;

    private OnRutaSelectListener listener;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_listado_rutas, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            listener = (OnRutaSelectListener)getActivity();
        } catch (ClassCastException e) {}

        lv_Rutas = (ListView)getView().findViewById(R.id.lv_avances_ruta);
        img_No_Hay = (ImageView)getView().findViewById(R.id.img_no_data);
        txt_No_Hay = (TextView)getView().findViewById(R.id.txt_no_data);

        btn_Tab1 = (android.widget.Button)getView().findViewById(R.id.btn_tab1);
        btn_Tab2 = (android.widget.Button)getView().findViewById(R.id.btn_tab2);
        btn_Tab3 = (android.widget.Button)getView().findViewById(R.id.btn_tab3);

        btn_Iniciar_Censo = (FloatingActionButton) getView().findViewById(R.id.buttonFloat);

        Adaptador = new AdaptadorRutas(getActivity(),AL_Rutas);
        lv_Rutas.setAdapter(Adaptador);

        Cargar_Rutas("101", btn_Tab1);
        if(AL_Rutas.size()>0)
            HayRutas = true;
        else
            HayRutas = false;
        HayRutas();

        btn_Tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cargar_Rutas("101", btn_Tab1);
            }
        });

        btn_Tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cargar_Rutas("102",btn_Tab2);
                if(HayRutas && AL_Rutas.size()==0)
                    txt_No_Hay.setText("No hay rutas censadas");

            }
        });

        btn_Tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cargar_Rutas("Todos",btn_Tab3);
            }
        });

        lv_Rutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.OnItemClick(Ids_OPR_USUARIOS.get(position));
            }
        });

        btn_Iniciar_Censo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick("1");
            }
        });

        img_No_Hay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!HayRutas && AL_Rutas.size()==0)
                    listener.img_nohayl();
            }
        });

    }

    public void Cargar_Rutas(String criterio,android.widget.Button btn){
        AL_Rutas.clear();
        Ids_OPR_USUARIOS.clear();
        Adaptador.notifyDataSetChanged();

        btn_Tab1.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_tabhost));
        btn_Tab2.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_tabhost));
        btn_Tab3.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_tabhost));

        btn.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_style2));
        String input = btn.getText().toString();

        BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        if(criterio.equals("Todos"))
            criterio = "";
        else{
            criterio = " WHERE id_estatus = "+criterio;
        }

        if (db != null) {//6
            Cursor c = db.rawQuery("SELECT id,direccion,nombre,id_carga,clave_loc,medidor_ins FROM OPR_USUARIOS"+criterio+";", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    Ids_OPR_USUARIOS.add(c.getString(0));
                    AL_Rutas.add(new AdaptadorRutas.ItemListaRutas(""+c.getString(1)
                            ,""+c.getString(2)
                            ,"Ruta "+c.getString(3)
                            ,""+c.getString(4)
                            ,""+c.getString(5)));
                    Adaptador.notifyDataSetChanged();
                } while (c.moveToNext());
            }
            //Cerramos la base de datos
            db.close();
        }//6

        HayRutas();
    }

    public void HayRutas(){
        if(AL_Rutas.size()==0){
            lv_Rutas.setVisibility(View.GONE);
            img_No_Hay.setVisibility(View.VISIBLE);
            txt_No_Hay.setVisibility(View.VISIBLE);
        }else{
            lv_Rutas.setVisibility(View.VISIBLE);
            img_No_Hay.setVisibility(View.GONE);
            txt_No_Hay.setVisibility(View.GONE);
        }

        if(HayRutas)
            btn_Iniciar_Censo.setVisibility(View.VISIBLE);
        else
            btn_Iniciar_Censo.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        root = null;
    }
}