package com.imera.censo.ui.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.BosinessCore.DomainObject.OprUsuarioDomainObject;
import com.imera.censo.BosinessCore.DomainObject.ParametroDomainObject;
import com.imera.censo.Contracts.Enums.tipo_snackbar;
import com.imera.censo.Contracts.Models.Censista;
import com.imera.censo.Contracts.Models.ItemComboRutas;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Models.WcfResult;
import com.imera.censo.Contracts.Services.IOprUsuario;
import com.imera.censo.Contracts.Services.IParametro;
import com.imera.censo.Contracts.Services.OnRutaSelectListener;
import com.imera.censo.R;
import com.imera.censo.Rutinas;
import com.imera.censo.Services.ApiManager;
import com.imera.censo.Services.PostCenso;
import com.imera.censo.Services.PostPadron;
import com.imera.censo.ui.Adapters.ComboRutasAdapter;

import java.util.ArrayList;


public class SubirRutasCapturadasFragment extends Fragment {
    private ArrayList<String> ListId_OtSubidos = new ArrayList<String>();
    private ArrayList<String> ListId_OtNoSubidos = new ArrayList<String>();
    private ImageView img_Subir;
    private ProgressBar pb_Estado;
    private Spinner Sp_Ordenes;
    private TextView lblRegSubidos,lblRegNoSubidos,txtContadorRegistrosSubidos,txtContadorRegistrosNoSubidos,tvCapturistaDefault;
    private TextView tv_otPendientes,tv_otCapturadas,tv_otCanceladas;
    private RadioGroup rgAfterUpload;
    private ArrayList<ItemComboRutas> listOrdenesCargadas = new ArrayList<ItemComboRutas>();
    private ComboRutasAdapter adaptadorOTCargadas;
    private ItemComboRutas ordenSeleccionada = null;
    private Censista capturistaActual;
    private OnRutaSelectListener listener;

    public SubirRutasCapturadasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_subir_rutas_capturadas, container, false);
        img_Subir = (ImageView) root.findViewById(R.id.img_upload);
        pb_Estado  = (ProgressBar) root.findViewById(R.id.pb_estado_subida);
        Sp_Ordenes = (Spinner) root.findViewById(R.id.sp_RutasSubir);
        lblRegSubidos = (TextView) root.findViewById(R.id.lblRegistrosSubidos);
        lblRegNoSubidos = (TextView) root.findViewById(R.id.lblRegistrosNoSubidos);
        tvCapturistaDefault = (TextView) root.findViewById(R.id.tvCapturistaDefault);
        txtContadorRegistrosSubidos = (TextView) root.findViewById(R.id.txtRegistrosSubidos);
        txtContadorRegistrosNoSubidos = (TextView) root.findViewById(R.id.txtRegistrosNoSubidos);
        tv_otPendientes = (TextView) root.findViewById(R.id.tv_otPendientes);
        tv_otCapturadas = (TextView) root.findViewById(R.id.tv_otCapturadas);
        tv_otCanceladas = (TextView) root.findViewById(R.id.tv_otCanceladas);
        rgAfterUpload = (RadioGroup) root.findViewById(R.id.rgOpciones);

        return root;
    }
    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            listener = (OnRutaSelectListener)getActivity();
        } catch (ClassCastException e) {}

        ValidaAntesDeEntrar();

        adaptadorOTCargadas = new ComboRutasAdapter(getContext(),listOrdenesCargadas);
        Sp_Ordenes.setAdapter(adaptadorOTCargadas);
        adaptadorOTCargadas.notifyDataSetChanged();

        Sp_Ordenes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ordenSeleccionada = listOrdenesCargadas.get(position);
                ObtenerCantidades();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        img_Subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_Subir.setVisibility(View.INVISIBLE);
                pb_Estado.setVisibility(View.VISIBLE);
                txtContadorRegistrosSubidos.setText("0");
                txtContadorRegistrosNoSubidos.setText("0");
                if (listOrdenesCargadas.size() >0 ){

                    new SubirOrdenesOffLine().execute();

                }else{

                    Rutinas.SnackBarCustom(getActivity(),lblRegSubidos,"","No existen registros pendientes por subir.", tipo_snackbar.INFORMATIVE_SNACKBAR);
                    img_Subir.setVisibility(View.VISIBLE);
                    pb_Estado.setVisibility(View.INVISIBLE);
                }

            }
        });

        ActualizarListaOrdenesCargadas();
        LoadCapturistaDefault();
    }

    private void ValidaAntesDeEntrar(){
        IParametro parService = new ParametroDomainObject();
        String strKey = parService.GetValorParametro(getString(R.string.parametro_key_wcf));

        if (TextUtils.isEmpty(strKey)){
            String Error = getString(R.string.esta_operacion_requiere_iniciar_sesion);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setMessage(Error);
            alertDialog.setTitle(R.string.a_ocurrido_un_problema);
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
//            listener.muestra_pagina_principal();
        }
    }

    private void LoadCapturistaDefault(){
        /*ICapturista capService = new CapturistaDomainObject();
        capturistaActual = capService.CapturistaGetDefault();
        tvCapturistaDefault.setText(capturistaActual.getNombre());*/
        tvCapturistaDefault.setText("pruebas");
    }

    private void ObtenerCantidades(){
        IOprUsuario OTService = new OprUsuarioDomainObject();
        int intPendientes = OTService.GetCantidades(ordenSeleccionada.getId_ruta(),"101");
        int intCapturadas = OTService.GetCantidades(ordenSeleccionada.getId_ruta(),"102");
//        int intCanceladas = OTService.GetCantidadOrdenesByIdTrabajo(ordenSeleccionada.getId_trabajo(),"10");

        tv_otPendientes.setText(String.format(getString(R.string.formato_pendientes), intPendientes));
        tv_otCapturadas.setText(String.format(getString(R.string.formato_capturadas), intCapturadas));
//        tv_otCanceladas.setText(String.format(getString(R.string.formato_canceladas), intCanceladas));

    }

    private void ActualizarListaOrdenesCargadas(){

        listOrdenesCargadas.clear();
        adaptadorOTCargadas.notifyDataSetChanged();

        BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        if (db != null) {//6
            Cursor c = db.rawQuery("SELECT MAX(id_carga),count(id_carga) " +
                    "FROM OPR_USUARIOS " +
                    "GROUP BY id_carga " +
                    "ORDER BY count(id_carga) DESC;", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    listOrdenesCargadas.add(new ItemComboRutas( c.getInt(0),"",c.getInt(1)));

                    adaptadorOTCargadas.notifyDataSetChanged();
                } while (c.moveToNext());
            }

            if(!c.isClosed())c.close();
            //Cerramos la base de datos
            db.close();
        }//6

    }

    public class SubirOrdenesOffLine extends AsyncTask<Void,WcfResult,WcfResult> {
        @Override
        protected WcfResult doInBackground(Void... strings) {
            WcfResult resultadoWCF = new WcfResult();
            PostCenso RegistroOrden;

            IParametro parService = new ParametroDomainObject();
            String strKey = parService.GetValorParametro(getString(R.string.parametro_key_wcf));

            int intIdTrabajo = ordenSeleccionada.getId_ruta();

            ListId_OtSubidos.clear();
            ListId_OtNoSubidos.clear();

            IOprUsuario OTService = new OprUsuarioDomainObject();

            ArrayList<OPR_USUARIOS> listOrdenTrabajo = OTService.CensoCapturadoGetList(intIdTrabajo);

            if(listOrdenTrabajo.size() > 0){
                for (OPR_USUARIOS ordenTrabajo: listOrdenTrabajo){
                    RegistroOrden = OTService.OprOprUsuarioSubirGetById(ordenTrabajo.getId());

                    ApiManager apiManager = ApiManager.getInstance();

                    PostPadron postOrdenes = new PostPadron(strKey
                            ,RegistroOrden);

                    resultadoWCF = apiManager.ActualizaPadronOffLine(postOrdenes);

                    publishProgress(resultadoWCF);

                    if (!(resultadoWCF.getError_number() > 0)){
                        ListId_OtSubidos.add(String.valueOf(RegistroOrden.getId()));
                    }else {
                        ListId_OtNoSubidos.add(String.valueOf(RegistroOrden.getId()));
                    }
                }
            }
            return resultadoWCF;
        }

        @Override
        protected void onProgressUpdate(WcfResult... values) {
            super.onProgressUpdate(values);
            if (!(values[0].getError_number() > 0) ) {
                lblRegSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosSubidos.setText(String.valueOf(Integer.parseInt(txtContadorRegistrosSubidos.getText().toString()) + 1));
                Log.e("Exito?", values[0].getError_menssage());
            } else {
                lblRegNoSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosNoSubidos.setVisibility(View.VISIBLE);
                txtContadorRegistrosNoSubidos.setText(String.valueOf(Integer.parseInt(txtContadorRegistrosNoSubidos.getText().toString()) + 1));
                Log.e("Error", values[0].getError_menssage());
            }
        }

        @Override
        protected void onPostExecute(WcfResult result) {
            super.onPostExecute(result);
            if (rgAfterUpload.getCheckedRadioButtonId() == R.id.rb2EliminarDatos) {
                if (ListId_OtSubidos.size() > 0) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setMessage("¿Deseas eliminar los registros del dispositivo?");
                    alertDialog.setTitle("Se requiere confirmación");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setCancelable(false);
                    alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //c�digo java si se ha pulsado no
                            ActualizarListaOrdenesCargadas();
                        }
                    });
                    alertDialog.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            IOprUsuario OTService = new OprUsuarioDomainObject();
                            if (ListId_OtSubidos.size() > 0)
                                for(int x=0;x<ListId_OtSubidos.size();x++) {

                                    OTService.EliminaPadronDespuesSubir(ListId_OtSubidos.get(x));

                                }
                            ActualizarListaOrdenesCargadas();
                            Rutinas.SnackBarCustom(getActivity(),lblRegSubidos,"", "Operación Realizada con Exito",tipo_snackbar.POSITIVE_SNACKBAR);
                        }
                    });
                    alertDialog.show();


                } else{
                    if (ListId_OtNoSubidos.size() > 0) {
                        ActualizarListaOrdenesCargadas();
                        Rutinas.SnackBarCustom(getActivity(),lblRegSubidos,""
                                ,String.format("Existen %d registros que no pudieron subirse", ListId_OtNoSubidos.size())
                                , tipo_snackbar.INFORMATIVE_SNACKBAR);

                    }
                }

            } else {

                if (ListId_OtNoSubidos.size() > 0) {
                    ActualizarListaOrdenesCargadas();
                    Rutinas.SnackBarCustom(getActivity(),lblRegSubidos,""
                            ,String.format("Existen %d registros que no pudieron subirse", ListId_OtNoSubidos.size())
                            ,tipo_snackbar.INFORMATIVE_SNACKBAR);
                }
            }

            img_Subir.setVisibility(View.VISIBLE);
            pb_Estado.setVisibility(View.INVISIBLE);
        }
    }
}