package com.imera.censo.Services;




import com.imera.censo.App;
import com.imera.censo.BosinessCore.DomainObject.ParametroDomainObject;
import com.imera.censo.Contracts.Models.Anomalia;
import com.imera.censo.Contracts.Models.Calle;
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
import com.imera.censo.Contracts.Models.UsuarioPadron;
import com.imera.censo.Contracts.Models.WcfResult;
import com.imera.censo.Contracts.Models.clsUsuario;
import com.imera.censo.Contracts.Services.IParametro;
import com.imera.censo.R;
import com.imera.censo.Rutinas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class ApiManager {
    private static ApiManager apiManager;

    IParametro parService = new ParametroDomainObject();
    private final String BASE_URL = parService.GetValorParametro(App.getContext().getString(R.string.parametro_direccion_wcf));
//    private final String BASE_URL = "http://imera-os.azurewebsites.net/IMERA_OS.svc/";
//    private final String BASE_URL = "http://movilesarm.azurewebsites.net/ServiciosMoviles.svc/";
    public static IServicesWCF iServicesWCF;
    private static HttpLoggingInterceptor loggingInterceptor;
    private static OkHttpClient.Builder httpClientBuilder;


    private ApiManager() {
        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        //httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
        httpClientBuilder = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor);

        //strURLWCF = getResources().getString(R.string.URL_WCF);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        iServicesWCF = retrofit.create(IServicesWCF.class);
    }

    public static synchronized ApiManager getInstance() {

        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }


    public void AnomaliaHttpGetList(String pass, Callback<List<Anomalia>> callback) {
        Call<List<Anomalia>> call = iServicesWCF.getAnomalias(pass);
        call.enqueue(callback);
    }

    public void CalleHttpGetList(String pass, Callback<List<Calle>> callback) {
        Call<List<Calle>> call = iServicesWCF.getCalles(pass);
        call.enqueue(callback);
    }

    public void CensistaHttpGetList(String pass, Callback<List<Censista>> callback) {
        Call<List<Censista>> call = iServicesWCF.getCensistas(pass);
        call.enqueue(callback);
    }

    public void TipoCasaHttpGetList(String pass, Callback<List<TipoCasa>> callback) {
        Call<List<TipoCasa>> call = iServicesWCF.getTiposCasa(pass);
        call.enqueue(callback);
    }

    public void CondicionPredioHttpGetList(String pass, Callback<List<CondicionPredio>> callback) {
        Call<List<CondicionPredio>> call = iServicesWCF.getCondicionesPredio(pass);
        call.enqueue(callback);
    }

    public void ColoniaHttpGetList(String pass, Callback<List<Colonia>> callback) {
        Call<List<Colonia>> call = iServicesWCF.getColonias(pass);
        call.enqueue(callback);
    }

    public void DiametroHttpGetList(String pass, Callback<List<Diametro>> callback) {
        Call<List<Diametro>> call = iServicesWCF.getDiametros(pass);
        call.enqueue(callback);
    }

    public void GiroHttpGetList(String pass, Callback<List<Giro>> callback) {
        Call<List<Giro>> call = iServicesWCF.getGiros(pass);
        call.enqueue(callback);
    }

    public void MarcaHttpGetList(String pass, Callback<List<Marca>> callback) {
        Call<List<Marca>> call = iServicesWCF.getMarcas(pass);
        call.enqueue(callback);
    }

    public void MaterialHttpGetList(String pass, Callback<List<Material>> callback) {
        Call<List<Material>> call = iServicesWCF.getMateriales(pass);
        call.enqueue(callback);
    }

    public void MaterialTomaHttpGetList(String pass, Callback<List<MaterialToma>> callback) {
        Call<List<MaterialToma>> call = iServicesWCF.getMaterialesToma(pass);
        call.enqueue(callback);
    }

    public void ModeloHttpGetList(String pass, Callback<List<Modelo>> callback) {
        Call<List<Modelo>> call = iServicesWCF.getModelos(pass);
        call.enqueue(callback);
    }

    public void ServicioHttpGetList(String pass, Callback<List<Servicio>> callback) {
        Call<List<Servicio>> call = iServicesWCF.getServicios(pass);
        call.enqueue(callback);
    }

    public void TipoGloboHttpGetList(String pass, Callback<List<TipoGlobo>> callback) {
        Call<List<TipoGlobo>> call = iServicesWCF.getTiposGlobo(pass);
        call.enqueue(callback);
    }

    public void TipoTomaHttpGetList(String pass, Callback<List<TipoToma>> callback) {
        Call<List<TipoToma>> call = iServicesWCF.getTiposToma(pass);
        call.enqueue(callback);
    }

    public void TipoUsuarioHttpGetList(String pass, Callback<List<TipoUsuario>> callback) {
        Call<List<TipoUsuario>> call = iServicesWCF.getTipoUsuario(pass);
        call.enqueue(callback);
    }

    public void PadronHttpGetList(String nGrupo, String nSec, String nManIni, String nManFin, String nIdColonia, String key, Callback<ArrayList<UsuarioPadron>> callback) {
        Call<ArrayList<UsuarioPadron>> call = iServicesWCF.getUsuariosCenso(nGrupo,nSec, nManIni, nManFin, nIdColonia, key);
        call.enqueue(callback);
    }

    public Integer ActualizarPadron(String id_padron,
                                    String id_cuenta,
                                    String id_carga,
                                    String id_censista,
                                    String id_servicio,
                                    String id_tipousuario,
                                    String medidor_ins,
                                    String lectura_med_ins,
                                    String id_marca_ins,
                                    String id_modelo_ins,
                                    String id_diametro,
                                    String id_materialtoma,
                                    String id_tipotoma,
                                    String id_tipoglobo,
                                    String id_mat_banqueta,
                                    String id_mat_calle,
                                    String registro_banqueta,
                                    String id_anomalia,
                                    String fac_tecnica,
                                    String tinaco,
                                    String cisterna,
                                    String alberca,
                                    String pozo,
                                    String foto_predio,
                                    String foto_toma,
                                    String nombre_comercial,
                                    String observa_a,
                                    String id_giro,
                                    String id_calle_ppal,
                                    String id_calle_lat1,
                                    String latitud,
                                    String longitud,
                                    String id_estatus,
                                    String id_identificacion,
                                    String otros,
                                    String num_ext,
                                    String num_int,
                                    String clave,
                                    String key) {
//        clsResultadoWCF resultadoWCF = new clsResultadoWCF();
        Integer resultadoInt = 0;
        Call<Integer> call = iServicesWCF.addCuenta(id_padron,
                                                    id_cuenta,
                                                    id_carga,
                                                    id_censista,
                                                    id_servicio,
                                                    id_tipousuario,
                                                    medidor_ins,
                                                    lectura_med_ins,
                                                    id_marca_ins,
                                                    id_modelo_ins,
                                                    id_diametro,
                                                    id_materialtoma,
                                                    id_tipotoma,
                                                    id_tipoglobo,
                                                    id_mat_banqueta,
                                                    id_mat_calle,
                                                    registro_banqueta,
                                                    id_anomalia,
                                                    fac_tecnica,
                                                    tinaco,
                                                    cisterna,
                                                    alberca,
                                                    pozo,
                                                    foto_predio,
                                                    foto_toma,
                                                    nombre_comercial,
                                                    observa_a,
                                                    id_giro,
                                                    id_calle_ppal,
                                                    id_calle_lat1,
                                                    latitud,
                                                    longitud,
                                                    id_estatus,
                                                    id_identificacion,
                                                    otros,
                                                    num_ext,
                                                    num_int,
                                                    clave,
                                                    key);
        try {
            Response<Integer> Result = call.execute();
            if(Result.isSuccessful()){
                resultadoInt = Result.body();
            }else {
                resultadoInt = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
      /*      resultadoWCF = RetornaError(50
                    ,e.getMessage());*/
        }
        return resultadoInt;
    }

    public WcfResult ActualizaPadronOffLine(PostPadron padron) {
        WcfResult resultadoWCF = new WcfResult();
        Call<WcfResult> call = iServicesWCF.CapturarPadron(padron);
        try {
            Response<WcfResult> Result = call.execute();
            if(Result.isSuccessful()){
                resultadoWCF = Result.body();
            }else {
                resultadoWCF = RetornaError(Result.code()
                        , Rutinas.ObtenerMensajeDeCodigo(Result.code()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            resultadoWCF = RetornaError(50
                    ,e.getMessage());
        }
        return resultadoWCF;
    }

    private WcfResult RetornaError(int intError, String strMensajeError){
        WcfResult resultadoWCF = new WcfResult();

        resultadoWCF.setError_menssage(strMensajeError);
        resultadoWCF.setError_number(intError);
        resultadoWCF.setDescripcion("SUBIR_PADRON");
        resultadoWCF.setEstado("ERROR");
        return resultadoWCF;
    }

    public void ValidaUsuarioContrasena(PostUsuario usuario, Callback<clsUsuario> callback) {
        Call<clsUsuario> call = iServicesWCF.ValidarUsrPsw(usuario);
        call.enqueue(callback);
    }

    public void PruebaCoenxionWCF( Callback<String> callback) {
        Call<String> call = iServicesWCF.TstWCF();
        call.enqueue(callback);
    }


}
