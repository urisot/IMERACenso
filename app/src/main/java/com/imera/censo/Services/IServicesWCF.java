package com.imera.censo.Services;



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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IServicesWCF {
    @GET("getAnomalias")
    Call<List<Anomalia>> getAnomalias(@Query("p1") String pass);

    @GET("getDiametros")
    Call<List<Diametro>> getDiametros(@Query("p1") String pass);

    @GET("getMarcas")
    Call<List<Marca>> getMarcas(@Query("p1") String pass);

    @GET("getMaterialCalle")
    Call<List<Material>> getMateriales(@Query("p1") String pass);

    @GET("getMaterialesToma")
    Call<List<MaterialToma>> getMaterialesToma(@Query("p1") String pass);

    @GET("getTiposCasa")
    Call<List<TipoCasa>> getTiposCasa(@Query("p1") String pass);

    @GET("getCondicionesPredio")
    Call<List<CondicionPredio>> getCondicionesPredio(@Query("p1") String pass);

    @GET("getModelos")
    Call<List<Modelo>> getModelos(@Query("p1") String pass);

    @GET("getServicios")
    Call<List<Servicio>> getServicios(@Query("p1") String pass);

    @GET("getTiposGlobo")
    Call<List<TipoGlobo>> getTiposGlobo(@Query("p1") String pass);

    @GET("getTiposToma")
    Call<List<TipoToma>> getTiposToma(@Query("p1") String pass);

    @GET("getTipoUsuario")
    Call<List<TipoUsuario>> getTipoUsuario(@Query("p1") String pass);

    @GET("getColonias")
    Call<List<Colonia>> getColonias(@Query("p1") String pass);

    @GET("getCalles")
    Call<List<Calle>> getCalles(@Query("p1") String pass);

    @GET("getGiros")
    Call<List<Giro>> getGiros(@Query("p1") String pass);

    @GET("getCensistas")
    Call<List<Censista>> getCensistas(@Query("p1") String pass);

    @GET("getUsuariosCenso")
    Call<ArrayList<UsuarioPadron>> getUsuariosCenso(@Query("p1") String nGrupo,
                                                    @Query("p2") String nSec,
                                                    @Query("p3") String nManIni,
                                                    @Query("p5") String nManFin,
                                                    @Query("p6") String nIdColonia,
                                                    @Query("p7") String key);

    @GET("addCuenta")
    Call<Integer> addCuenta(@Query("p1") String id_padron,
                            @Query("p2") String id_cuenta,
                            @Query("p3") String id_carga,
                            @Query("p4") String id_censista,
                            @Query("p5") String id_servicio,
                            @Query("p6") String id_tipousuario,
                            @Query("p7") String medidor_ins,
                            @Query("p8") String lectura_med_ins,
                            @Query("p9") String id_marca_ins,
                            @Query("p10") String id_modelo_ins,
                            @Query("p11") String id_diametro,
                            @Query("p12") String id_materialtoma,
                            @Query("p13") String id_tipotoma,
                            @Query("p14") String id_tipoglobo,
                            @Query("p15") String id_mat_banqueta,
                            @Query("p16") String id_mat_calle,
                            @Query("p17") String registro_banqueta,
                            @Query("p18") String id_anomalia,
                            @Query("p19") String fac_tecnica,
                            @Query("p20") String tinaco,
                            @Query("p21") String cisterna,
                            @Query("p22") String alberca,
                            @Query("p23") String pozo,
                            @Query("p24") String foto_predio,
                            @Query("p25") String foto_toma,
                            @Query("p26") String nombre_comercial,
                            @Query("p27") String observa_a,
                            @Query("p28") String id_giro,
                            @Query("p29") String id_calle_ppal,
                            @Query("p30") String id_calle_lat1,
                            @Query("p31") String latitud,
                            @Query("p32") String longitud,
                            @Query("p33") String id_estatus,
                            @Query("p34") String id_identificacion,
                            @Query("p35") String otros,
                            @Query("p36") String num_ext,
                            @Query("p37") String num_int,
                            @Query("p38") String clave,
                            @Query("p40") String key);

    @POST("ActPadron")
    Call<WcfResult> CapturarPadron(@Body PostPadron post);

    @POST("ValidarUsrPswCenso")
    Call<clsUsuario> ValidarUsrPsw(@Body PostUsuario post);

    @GET("TstWCF")
    Call<String> TstWCF();

/*
    @GET("getOrdenesBrigadista")
    Call<ArrayList<clsOrdenTrabajoDescargar>> getOrdenesBrigadista(@Query("p1") String pass,
                                                                   @Query("p2") String idBrigadista,
                                                                   @Query("p3") String idTrabajo);

    @POST("ValidarUsrPsw")
    Call<clsUsuario> ValidarUsrPsw(@Body PostUsuario post);

    @GET("TstWCF")
    Call<String> TstWCF();

    //ofline
    @POST("ActOrdenes")
    Call<clsResultadoWCF> CapturarOrdenes(@Body PostOrdenes post);

    @GET("actUbicacionBrigadista")
    Call<clsResultadoWCF> actUbicacionBrigadista(@Query("p1") String pass,
                                                 @Query("p2") String idBrigadista,
                                                 @Query("p3") String longitud,
                                                 @Query("p4") String latitud);*/

 /*   //online
    @POST("ActOrdenes")
    Call<clsResultadoWCF> CapturarOrdenes(@Body PostOrdenes post);*/
}
