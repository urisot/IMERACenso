package com.imera.censo.ui.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.imera.censo.BosinessCore.DomainObject.AnomaliaDomainObject;
import com.imera.censo.BosinessCore.DomainObject.CalleDomainObject;
import com.imera.censo.BosinessCore.DomainObject.CatalogoDomainObject;
import com.imera.censo.BosinessCore.DomainObject.CensistaDomainObject;
import com.imera.censo.BosinessCore.DomainObject.ColoniaDomainObject;
import com.imera.censo.BosinessCore.DomainObject.CondicionPredioDomainObject;
import com.imera.censo.BosinessCore.DomainObject.DiametroDomainObject;
import com.imera.censo.BosinessCore.DomainObject.GiroDomainObject;
import com.imera.censo.BosinessCore.DomainObject.MarcaDomainObject;
import com.imera.censo.BosinessCore.DomainObject.MaterialDomainObject;
import com.imera.censo.BosinessCore.DomainObject.MaterialTomaDomainObject;
import com.imera.censo.BosinessCore.DomainObject.ModeloDomainObject;
import com.imera.censo.BosinessCore.DomainObject.ParametroDomainObject;
import com.imera.censo.BosinessCore.DomainObject.ServicioDomainObject;
import com.imera.censo.BosinessCore.DomainObject.TipoGloboDomainObject;
import com.imera.censo.BosinessCore.DomainObject.TipoTomaDomainObject;
import com.imera.censo.BosinessCore.DomainObject.TipoUsuarioDomainObject;
import com.imera.censo.BosinessCore.DomainObject.TiposCasaDomainObject;
import com.imera.censo.Contracts.Enums.tipo_mensajes_error;
import com.imera.censo.Contracts.Enums.tipo_snackbar;
import com.imera.censo.Contracts.Models.Anomalia;
import com.imera.censo.Contracts.Models.Calle;
import com.imera.censo.Contracts.Models.Catalogo;
import com.imera.censo.Contracts.Models.Censista;
import com.imera.censo.Contracts.Models.Colonia;
import com.imera.censo.Contracts.Models.CondicionPredio;
import com.imera.censo.Contracts.Models.Diametro;
import com.imera.censo.Contracts.Models.Giro;
import com.imera.censo.Contracts.Models.Marca;
import com.imera.censo.Contracts.Models.Material;
import com.imera.censo.Contracts.Models.MaterialToma;
import com.imera.censo.Contracts.Models.Modelo;
import com.imera.censo.Contracts.Models.Servicio;
import com.imera.censo.Contracts.Models.TipoCasa;
import com.imera.censo.Contracts.Models.TipoGlobo;
import com.imera.censo.Contracts.Models.TipoToma;
import com.imera.censo.Contracts.Models.TipoUsuario;
import com.imera.censo.Contracts.Services.IAnomalia;
import com.imera.censo.Contracts.Services.ICalle;
import com.imera.censo.Contracts.Services.ICatalogo;
import com.imera.censo.Contracts.Services.ICensista;
import com.imera.censo.Contracts.Services.IColonia;
import com.imera.censo.Contracts.Services.ICondicionPredio;
import com.imera.censo.Contracts.Services.IDiametro;
import com.imera.censo.Contracts.Services.IGiro;
import com.imera.censo.Contracts.Services.IMarca;
import com.imera.censo.Contracts.Services.IMaterial;
import com.imera.censo.Contracts.Services.IMaterialToma;
import com.imera.censo.Contracts.Services.IModelo;
import com.imera.censo.Contracts.Services.IParametro;
import com.imera.censo.Contracts.Services.IServicio;
import com.imera.censo.Contracts.Services.ITipoCasa;
import com.imera.censo.Contracts.Services.ITipoGlobo;
import com.imera.censo.Contracts.Services.ITipoToma;
import com.imera.censo.Contracts.Services.ITipoUsuario;
import com.imera.censo.Contracts.Services.OnRutaSelectListener;
import com.imera.censo.R;
import com.imera.censo.Rutinas;
import com.imera.censo.Services.ApiManager;
import com.imera.censo.ui.Adapters.AdaptadorActualizarCatalogos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CatalogosFragment extends Fragment {

    private View root;
    private ListView lv_Actualizar_Catalogos;

    private final ArrayList<Catalogo> ListCatalogos = new ArrayList<>();
    private AdaptadorActualizarCatalogos Adaptador;
//    private ArrayList<AsyncTask> Prosesos_Segundo_Plano = new ArrayList<AsyncTask>();
//    private SQLiteDatabase db;
    private boolean boolActualizaTodo = false;
    private OnRutaSelectListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_catalogos, container, false);

        lv_Actualizar_Catalogos = (ListView) root.findViewById(R.id.lv_actualizar_catalogos);

        return root;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* try {
            listener = (OnRutaSelectListener)getActivity();
        } catch (ClassCastException e) {}*/

        ValidaAntesDeEntrar();

        ListCatalogos.clear();

        ListCatalogos.add(new Catalogo("Anomalias", "getAnomalias", "id_anomalia", "Cat_Anomalias"));
        ListCatalogos.add(new Catalogo("Colonias","getColonias","id_colonia","Cat_Colonias"));
        ListCatalogos.add(new Catalogo("Diametros","getDiametros","id_diametro","Cat_Diametros"));
        ListCatalogos.add(new Catalogo("Marcas","getMarcas","id_marca","Cat_Marcas"));
        ListCatalogos.add(new Catalogo("Materiales","getMateriales","id_material","Cat_Materiales"));
        ListCatalogos.add(new Catalogo("Material Toma","getMaterialesToma","id_materialtoma","Cat_MaterialToma"));
        ListCatalogos.add(new Catalogo("Modelos","getModelos","id_modelo","Cat_Modelos"));
        ListCatalogos.add(new Catalogo("Servicios","getServicios","id_Servicio","Cat_Servicios"));
        ListCatalogos.add(new Catalogo("Tipos Globo","getTiposGlobo","id_tipoglobo","Cat_TiposGlobo"));
        ListCatalogos.add(new Catalogo("Tipos Toma","getTiposToma","id_TipoToma","Cat_TiposToma"));
        ListCatalogos.add(new Catalogo("Condiciones del predio","getCondicionesPredio","id_condicionpredio","Cat_CondicionPredio"));
        ListCatalogos.add(new Catalogo("Tipos de Casa","getTiposCasa","id_tipocasa","Cat_TipoCasa"));
        ListCatalogos.add(new Catalogo("Tipos Usuario","getTipoUsuario","id_tipousuario","Cat_TiposUsuario"));
        ListCatalogos.add(new Catalogo("Calles","getCalles","id_calle","Cat_Calles"));
        ListCatalogos.add(new Catalogo("Giros","getGiros","id_giro","Cat_Giros"));
        ListCatalogos.add(new Catalogo("Censistas","getCensistas","id_Personal","Cat_Censistas"));
        ListCatalogos.add(new Catalogo("Todos","","",""));

        Adaptador = new AdaptadorActualizarCatalogos(getActivity(), ListCatalogos);
        lv_Actualizar_Catalogos.setAdapter(Adaptador);

        lv_Actualizar_Catalogos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!ListCatalogos.get(position).getNombreReal().equals("Todos")) {
                    boolActualizaTodo = false;
                    ListCatalogos.get(position).setMostrarCargando(true);
                    ListCatalogos.get(position).setMostrarCorrecto(false);
                    ListCatalogos.get(position).setMostrarError(false);
                    ActualizaCatalogos(position);
                } else {
                    boolActualizaTodo = true;
                    for (int i = 0; i < ListCatalogos.size()-1; i++) {
                        ListCatalogos.get(i).setMostrarCargando(true);
                        ListCatalogos.get(i).setMostrarCorrecto(false);
                        ListCatalogos.get(i).setMostrarError(false);

                        ActualizaCatalogos(i);
                    }
                }
                Adaptador.notifyDataSetChanged();
            }
        });
    }

    private void ActualizaCatalogos(final Integer Id){
    String strNombreTabla = ListCatalogos.get(Id).getNombreTabla();
    String strNombreReal = ListCatalogos.get(Id).getNombreReal();
    ApiManager apiManager = ApiManager.getInstance();

    IParametro parService = new ParametroDomainObject();
    String strKey = parService.GetValorParametro(getString(R.string.parametro_key_wcf));

    if(strNombreTabla.equals(getString(R.string.cat_anomalias))){
        apiManager.AnomaliaHttpGetList (strKey, new Callback<List<Anomalia>>() {
            @Override
            public void onResponse(Call<List<Anomalia>> call, Response<List<Anomalia>> response) {
                List<Anomalia> listAnomalias = response.body();
                if (response.isSuccessful() && listAnomalias != null)
                {
                    IAnomalia anomService = new AnomaliaDomainObject();

                    if (listAnomalias.size() > 0){
                        InicializaTablas(Id);

                        for (Anomalia anomalia : listAnomalias) {
                            anomService.AnomaliaInsert(anomalia);
                        }
                        ActualizaUltimaVez(Id);
                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<Anomalia>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_calles))){
        apiManager.CalleHttpGetList(strKey, new Callback<List<Calle>>() {
            @Override
            public void onResponse(Call<List<Calle>> call, Response<List<Calle>> response) {
                List<Calle> list = response.body();
                if (response.isSuccessful() && list != null)
                {
                    ICalle calleService = new CalleDomainObject();
                    if (list.size() > 0){
                        InicializaTablas(Id);

                        for (Calle calle : list) {
                            calleService.CalleInsert(calle);
                        }
                        ActualizaUltimaVez(Id);
                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<Calle>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_censistas))){
        apiManager.CensistaHttpGetList(strKey, new Callback<List<Censista>>() {
            @Override
            public void onResponse(Call<List<Censista>> call, Response<List<Censista>> response) {
                List<Censista> list = response.body();
                if (response.isSuccessful() && list != null)
                {
                    ICensista censistaService = new CensistaDomainObject();
                    if (list.size() > 0){
                        InicializaTablas(Id);

                        for (Censista censista : list) {
                            censistaService.CensistaInsert(censista);
                        }
                        ActualizaUltimaVez(Id);
                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<Censista>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_colonias))){
        apiManager.ColoniaHttpGetList(strKey, new Callback<List<Colonia>>() {
            @Override
            public void onResponse(Call<List<Colonia>> call, Response<List<Colonia>> response) {
                List<Colonia> list = response.body();
                if (response.isSuccessful() && list != null)
                {
                    IColonia coloniaService = new ColoniaDomainObject();
                    if (list.size() > 0){
                        InicializaTablas(Id);

                        for (Colonia colonia : list) {
                            coloniaService.ColoniaInsert(colonia);
                        }
                        ActualizaUltimaVez(Id);
                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<Colonia>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_diametros))){
        apiManager.DiametroHttpGetList(strKey, new Callback<List<Diametro>>() {
            @Override
            public void onResponse(Call<List<Diametro>> call, Response<List<Diametro>> response) {
                List<Diametro> list = response.body();
                if (response.isSuccessful() && list != null)
                {
                    IDiametro diametroService = new DiametroDomainObject();
                    if (list.size() > 0){
                        InicializaTablas(Id);

                        for (Diametro diametro : list) {
                            diametroService.DiametroInsert(diametro);
                        }
                        ActualizaUltimaVez(Id);
                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<Diametro>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_giros))){
        apiManager.GiroHttpGetList(strKey, new Callback<List<Giro>>() {
            @Override
            public void onResponse(Call<List<Giro>> call, Response<List<Giro>> response) {
                List<Giro> list = response.body();
                if (response.isSuccessful() && list != null)
                {
                    if (list.size() > 0){
                        InicializaTablas(Id);

                        IGiro giroServ = new GiroDomainObject();
                        for (Giro giro : list) {
                            giroServ.GiroInsert(giro);
                        }
                        ActualizaUltimaVez(Id);
                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<Giro>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_condicionpredio))){
        apiManager.CondicionPredioHttpGetList(strKey, new Callback<List<CondicionPredio>>() {
            @Override
            public void onResponse(Call<List<CondicionPredio>> call, Response<List<CondicionPredio>> response) {
                List<CondicionPredio> list = response.body();
                if (response.isSuccessful() && list != null)
                {
                    if (list.size() > 0){
                        InicializaTablas(Id);

                        ICondicionPredio condicionServ = new CondicionPredioDomainObject();
                        for (CondicionPredio condicionPredio : list) {
                            condicionServ.CondicionPredioInsert(condicionPredio);
                        }
                        ActualizaUltimaVez(Id);
                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<CondicionPredio>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }
    else if(strNombreTabla.equals(getString(R.string.cat_tipocasa))){
        apiManager.TipoCasaHttpGetList(strKey, new Callback<List<TipoCasa>>() {
            @Override
            public void onResponse(Call<List<TipoCasa>> call, Response<List<TipoCasa>> response) {
                List<TipoCasa> list = response.body();
                if (response.isSuccessful() && list != null)
                {
                    if (list.size() > 0){
                        InicializaTablas(Id);

                        ITipoCasa tipoCasaServ = new TiposCasaDomainObject();
                        for (TipoCasa condicionPredio : list) {
                            tipoCasaServ.TipoCasaInsert(condicionPredio);
                        }
                        ActualizaUltimaVez(Id);
                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<TipoCasa>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_marcas))){
        apiManager.MarcaHttpGetList(strKey, new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                List<Marca> listMarcas = response.body();
                if (response.isSuccessful() && listMarcas != null)
                {
                    IMarca marcaService = new MarcaDomainObject();
                    if (listMarcas.size() > 0){
                        InicializaTablas(Id);

                        for (Marca marca : listMarcas) {
                            marcaService.MarcaInsert(marca);
                        }
                        ActualizaUltimaVez(Id);
                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_materiales))){
        apiManager.MaterialHttpGetList(strKey, new Callback<List<Material>>() {
            @Override
            public void onResponse(Call<List<Material>> call, Response<List<Material>> response) {
                List<Material> MaterialList = response.body();
                if (response.isSuccessful() && MaterialList != null)
                {
                    IMaterial materialService = new MaterialDomainObject();
                    if (MaterialList.size() > 0){
                        InicializaTablas(Id);

                        for (Material material : MaterialList)
                        {

                            materialService.MaterialInsert(material);
                        }
                        ActualizaUltimaVez(Id);

                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<Material>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_materialtoma))){
        apiManager.MaterialTomaHttpGetList(strKey, new Callback<List<MaterialToma>>() {
            @Override
            public void onResponse(Call<List<MaterialToma>> call, Response<List<MaterialToma>> response) {
                List<MaterialToma> MaterialtomaList = response.body();
                if (response.isSuccessful() && MaterialtomaList != null)
                {
                    IMaterialToma materialTomaService = new MaterialTomaDomainObject();
                    if (MaterialtomaList.size() > 0){
                        InicializaTablas(Id);

                        for (MaterialToma materialToma : MaterialtomaList) {
                            materialTomaService.MaterialTomaInsert(materialToma);
                        }
                        ActualizaUltimaVez(Id);

                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<MaterialToma>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_modelos))){
        apiManager.ModeloHttpGetList(strKey, new Callback<List<Modelo>>() {
            @Override
            public void onResponse(Call<List<Modelo>> call, Response<List<Modelo>> response) {
                List<Modelo> Modelolist = response.body();
                if (response.isSuccessful() && Modelolist != null)
                {
                    IModelo modeloService = new ModeloDomainObject();
                    if (Modelolist.size() > 0){
                        InicializaTablas(Id);

                        for (Modelo modelo : Modelolist) {

                            modeloService.ModeloInsert(modelo);

                        }
                        ActualizaUltimaVez(Id);

                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<Modelo>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_servicios))){
        apiManager.ServicioHttpGetList(strKey, new Callback<List<Servicio>>() {
            @Override
            public void onResponse(Call<List<Servicio>> call, Response<List<Servicio>> response) {
                List<Servicio> serviciosList = response.body();
                if (response.isSuccessful() && serviciosList != null)
                {
                    IServicio serServ = new ServicioDomainObject();
                    if (serviciosList.size() > 0){
                        InicializaTablas(Id);

                        for (Servicio servicio : serviciosList) {

                            serServ.ServicioInsert(servicio);

                        }
                        ActualizaUltimaVez(Id);

                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_tiposglobo))){
        apiManager.TipoGloboHttpGetList(strKey, new Callback<List<TipoGlobo>>() {
            @Override
            public void onResponse(Call<List<TipoGlobo>> call, Response<List<TipoGlobo>> response) {
                List<TipoGlobo> tipoGloboList = response.body();
                if (response.isSuccessful() && tipoGloboList != null)
                {
                    ITipoGlobo materialService = new TipoGloboDomainObject();
                    if (tipoGloboList.size() > 0){
                        InicializaTablas(Id);

                        for (TipoGlobo tipoGlobo : tipoGloboList) {
                            materialService.TipoGloboInsert(tipoGlobo);
                        }
                        ActualizaUltimaVez(Id);

                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<TipoGlobo>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_tiposusuario))){
        apiManager.TipoUsuarioHttpGetList(strKey, new Callback<List<TipoUsuario>>() {
            @Override
            public void onResponse(Call<List<TipoUsuario>> call, Response<List<TipoUsuario>> response) {
                List<TipoUsuario> tipoUsuarioList = response.body();
                if (response.isSuccessful() && tipoUsuarioList != null)
                {
                    ITipoUsuario tipoUsuarioService = new TipoUsuarioDomainObject();
                    if (tipoUsuarioList.size() > 0){
                        InicializaTablas(Id);

                        for (TipoUsuario tipoUsuario : tipoUsuarioList) {
                            tipoUsuarioService.TipoUsuarioInsert(tipoUsuario);
                        }
                        ActualizaUltimaVez(Id);

                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<TipoUsuario>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }else if(strNombreTabla.equals(getString(R.string.cat_tipostoma))){
        apiManager.TipoTomaHttpGetList(strKey, new Callback<List<TipoToma>>() {
            @Override
            public void onResponse(Call<List<TipoToma>> call, Response<List<TipoToma>> response) {
                List<TipoToma> tipoTomaList = response.body();
                if (response.isSuccessful() && tipoTomaList != null)
                {
                    ITipoToma materialService = new TipoTomaDomainObject();
                    if (tipoTomaList.size() > 0){
                        InicializaTablas(Id);

                        for (TipoToma tipoToma : tipoTomaList) {
                            materialService.TipoTomaInsert(tipoToma);
                        }
                        ActualizaUltimaVez(Id);

                        NotificaActualizacion(strNombreReal);
                    }
                    NotificaCambio(Id);
                }else {
                    NotificaCambio(Id,true);
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<TipoToma>> call, Throwable t) {
                NotificaCambio(Id,true);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,getActivity()
                        , tipo_mensajes_error.SNACKBAR
                        ,lv_Actualizar_Catalogos);
            }
        });
    }

}

    private void NotificaActualizacion(String strNombreReal){
        if(!TextUtils.isEmpty(strNombreReal)){

            String strMensaje = String.format(getString(R.string.notifica_actualiza_catalogo), strNombreReal);
            if(boolActualizaTodo){
                Toast.makeText(getActivity(),strMensaje,Toast.LENGTH_LONG).show();
            }else {
                Rutinas.SnackBarCustom(getActivity(),lv_Actualizar_Catalogos, "",strMensaje, tipo_snackbar.INFORMATIVE_SNACKBAR);
            }
        }

    }

    private void NotificaCambio(int Id, boolean ConError){
        if (ConError){
            ListCatalogos.get(Id).setMostrarCargando(false);
            Adaptador.notifyDataSetChanged();
            ListCatalogos.get(Id).setMostrarError(true);
            Adaptador.notifyDataSetChanged();
        }else{
            ListCatalogos.get(Id).setMostrarCargando(false);
            Adaptador.notifyDataSetChanged();
            ListCatalogos.get(Id).setMostrarCorrecto(true);
            Adaptador.notifyDataSetChanged();
        }


    }

    private void NotificaCambio(int Id){
        NotificaCambio(Id,false);
    }

    private void ActualizaUltimaVez(int Id){
        String strNombreTabla = ListCatalogos.get(Id).getNombreTabla();

        ICatalogo catService = new CatalogoDomainObject();
        catService.ActualizaUltimaVez(strNombreTabla);
    }

    private void InicializaTablas(Integer Id){
        String strNombreTabla = ListCatalogos.get(Id).getNombreTabla();
        String strCampoId = ListCatalogos.get(Id).getNombreId();

        ICatalogo catService = new CatalogoDomainObject();
        catService.InicializaTablas(strNombreTabla,strCampoId);
    }

/*    public void DetenerTodo(){
        for(int i = 0;i<Prosesos_Segundo_Plano.size();i++)
            Prosesos_Segundo_Plano.get(i).cancel(true);
        Prosesos_Segundo_Plano.clear();

    }*/

    @Override
    public void onPause() {
//        DetenerTodo();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        root = null;
    }
}