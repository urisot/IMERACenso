package com.imera.censo.ui.Activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.tipo_snackbar;
import com.imera.censo.Contracts.Services.OnRutaSelectListener;
import com.imera.censo.R;
import com.imera.censo.Rutinas;
import com.imera.censo.Services.ApiManager;
import com.imera.censo.ui.Fragments.ConfiguracionFragment;
import com.imera.censo.ui.Fragments.DescargarRutasFragment;
import com.imera.censo.ui.Fragments.ListadoRutasFragment;
import com.imera.censo.ui.Fragments.SubirRutasFragment;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipalActivity extends AppCompatActivity implements OnRutaSelectListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageButton btnListadoOrdenes,btnCatalogos,btnDescargarOrdenes,btnSubirOrdenes,btnConfiguracion;
//    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private DrawerLayout drawerLayoutLeft;
    private NavigationView navigationView;
    private NavController navController;
    private RelativeLayout rl_menu_small;
    private FrameLayout nav_host_fragment;
    private ImageButton btn_Back;
    private boolean boolSolicitaDescargarRutas = false;
 /*   private ImageButton btn_Actualizar;
    private ImageButton btn_Bajar;
    private ImageButton btn_Subir;

    private ImageButton btn_Settings;

    FragmentManager FM;
    Fragment nav_host_fragment = new ListadoRutasFragment();*/

    private boolean boolLogueado = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, /* Este codigo es para identificar tu request */
                1);

        bindUI();
        EstablecerPermisos();
    }

    private void bindUI(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnListadoOrdenes = (ImageButton)findViewById(R.id.btnListadoOrdenes);
        btnCatalogos = (ImageButton)findViewById(R.id.btnCatalogos);
        btnDescargarOrdenes = (ImageButton)findViewById(R.id.btnDescargarOrdenes);
        btnSubirOrdenes = (ImageButton)findViewById(R.id.btnSubirOrdenes);
        btnConfiguracion = (ImageButton)findViewById(R.id.btnConfiguracion);


        rl_menu_small = (RelativeLayout) findViewById(R.id.rl_menu_small);
        nav_host_fragment = (FrameLayout) findViewById(R.id.nav_host_fragment_content_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        drawerLayoutLeft = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_listado_rutas
                , R.id.nav_descargar_rutas
                , R.id.nav_subir_rutas
                , R.id.nav_catalogos
                , R.id.nav_configuracion)
                .setOpenableLayout(drawerLayoutLeft)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        btnListadoOrdenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    navController.navigate(R.id.nav_listado_rutas);
            }
        });

        btnDescargarOrdenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_descargar_rutas);
            }
        });

        btnSubirOrdenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_subir_rutas);
            }
        });

        btnCatalogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_catalogos);
            }
        });

        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_configuracion);
            }
        });
    }
    private void EstablecerPermisos(){
        if(boolLogueado){

            navigationView.getMenu().getItem(1).setVisible(true);
            navigationView.getMenu().getItem(2).setVisible(true);
            navigationView.getMenu().getItem(3).setVisible(true);
            navigationView.getMenu().getItem(4).setVisible(true);

            btnDescargarOrdenes.setVisibility(View.VISIBLE);
            btnSubirOrdenes.setVisibility(View.VISIBLE);
            btnCatalogos.setVisibility(View.VISIBLE);
            btnConfiguracion.setVisibility(View.VISIBLE);

            /*muestra_oculta_menu_lateral(true);
            navController.navigate(R.id.nav_listado_ot);
            drawerLayoutLeft.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);*/

        }else {

            if(Rutinas.ObtenerValorBooleanoParametro(getString(R.string.parametro_permite_descargar_censo))){
                navigationView.getMenu().getItem(1).setVisible(true);
                btnDescargarOrdenes.setVisibility(View.VISIBLE);
            }else{
                navigationView.getMenu().getItem(1).setVisible(false);
                btnDescargarOrdenes.setVisibility(View.INVISIBLE);
            }

            if(Rutinas.ObtenerValorBooleanoParametro(getString(R.string.parametro_permite_subir_censo))){
                navigationView.getMenu().getItem(2).setVisible(true);
                btnSubirOrdenes.setVisibility(View.VISIBLE);
            }else{
                navigationView.getMenu().getItem(2).setVisible(false);
                btnSubirOrdenes.setVisibility(View.INVISIBLE);
            }

            //navigationView.getMenu().getItem(3).setVisible(false);
            if(Rutinas.ObtenerValorBooleanoParametro(getString(R.string.parametro_permite_descargar_catalogos))){
                navigationView.getMenu().getItem(3).setVisible(true);
                btnCatalogos.setVisibility(View.VISIBLE);
            }else {
                navigationView.getMenu().getItem(3).setVisible(false);
                btnCatalogos.setVisibility(View.INVISIBLE);
            }

            navigationView.getMenu().getItem(4).setVisible(false);
            btnConfiguracion.setVisibility(View.INVISIBLE);

            /*muestra_oculta_menu_lateral(false);
            drawerLayoutLeft.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toolbar.setNavigationIcon(null);*/
        }

/*        navigationView.getMenu().getItem(0).setVisible(false);
        navigationView.getMenu().getItem(1).setVisible(false);

        if(boolMostrarOrdenesAgrupadas){
            navigationView.getMenu().getItem(1).setVisible(true);
            navController.navigate(R.id.nav_listado_ot_agrupado);
        }else{
            navigationView.getMenu().getItem(0).setVisible(true);
            navController.navigate(R.id.nav_listado_ot);
        }*/

        MuestraOcultaMenuLateral(Rutinas.ObtenerValorBooleanoParametro(getString(R.string.parametro_muestra_menu_lateral)));
    }

    private void MuestraOcultaMenuLateral(boolean boolMostrar){
        //int imgHeight = getSupportActionBar().getHeight();
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

        if(boolMostrar){
            rl_menu_small.setVisibility(View.VISIBLE);
            params.setMarginStart(105);
            //params.setMargins(105,0,0,0);
            nav_host_fragment.setLayoutParams(params);
        }else{
            rl_menu_small.setVisibility(View.GONE);

            params.setMargins(0, 0, 0, 0);
            nav_host_fragment.setLayoutParams(params);
        }
    }
//    static final int REQUEST_IMAGE_CAPTURE = 1;
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_login) {
            // item.setIcon(R.drawable.ic_add_24);
            if(boolLogueado){
                LogOut();
            }else{
                PruebaConexionParaIrALogIn();
            }


        }
        return super.onOptionsItemSelected(item);
    }
    private void LogOut(){
        boolLogueado = false;
        EstablecerPermisos();
        CambiaIconoMenu();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.dark_primary_color));
            window.setTitle("");
        }
        Rutinas.SnackBarCustom(this,btnListadoOrdenes,""
                ,getString(R.string.cerro_sesion_correctamente)
                , tipo_snackbar.INFORMATIVE_SNACKBAR);
    }

    private void CambiaIconoMenu(){
        MenuItem item =  toolbar.getMenu().findItem(R.id.action_login);
        if( item != null){
            if(boolLogueado){
                item.setIcon(R.drawable.ic_logout_24);
            }else {
                item.setIcon(R.drawable.ic_login_24);
            }

        }
    }

    private void PruebaConexionParaIrALogIn(){

        ApiManager apiManager = ApiManager.getInstance();

        apiManager.PruebaCoenxionWCF(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String strMensaje = response.body();
                    if (strMensaje.length()>0){
                        IrALogIn();

                    }else {
                        MuestraModificaWCF();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MuestraModificaWCF();
            }
        });
    }

    private void MuestraModificaWCF(){
        ModificaWcfDialog modificaWcfDialog = new ModificaWcfDialog(MenuPrincipalActivity.this);
        modificaWcfDialog.show();
    }


    private void IrALogIn() {
        Intent intent = new Intent(this, LogInActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, Constants.SOLICITUD_INISIAR_SESION);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onActivityResult(int requestc,int resultc, Intent data){
        super.onActivityResult(requestc,requestc,data);

        if(requestc == Constants.SOLICITUD_INISIAR_SESION){
            if(resultc == Constants.RESULTADO_OK ){
                boolLogueado = true;
                EstablecerPermisos();
                CambiaIconoMenu();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark2));
                    window.setTitle(getString(R.string.modo_admin));
                    getSupportActionBar().setTitle(getString(R.string.modo_admin));
                }
                if(boolSolicitaDescargarRutas){
                    boolSolicitaDescargarRutas = false;
                    if(VerificaCatalogos()){navController.navigate(R.id.nav_descargar_rutas);}else{navController.navigate(R.id.nav_catalogos);}
                }
            }
        }
        if(requestc==1){
            if(resultc ==1 ){
                Boolean Hay_Errores = false;
                String Error = "";

                BDManager Manejador = new BDManager(getApplicationContext(),"DBCenso",null,1);
                SQLiteDatabase db = Manejador.getReadableDatabase();

                if(db!=null){
                    //Validando se tenga rutas capturadas
                    Cursor c = db.rawQuery("SELECT id_Personal FROM Cat_Censistas", null);
                    if(!c.moveToFirst()){
                        Hay_Errores = true;
                        Error = "Necesita Actualizar el Catalogo Censistas";
                    }
                }
                if(Hay_Errores){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuPrincipalActivity.this,R.style.AppCompatAlertDialogStyle);
                    alertDialog.setMessage(Error);
                    alertDialog.setTitle("Ha ocurrido un problema");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }else{
                    try {
                        //ir a configuraciones
       /*                 FragmentTransaction FT = FM.beginTransaction();
                        Fragment Configuraciones = new ConfiguracionFragment();
                        FT.replace(R.id.fragment_contenedor, Configuraciones);
                        FT.addToBackStack(null);
                        FT.commit();*/
                        btn_Back.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_back_24));
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "error " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
            else
                btn_Back.setImageDrawable(null);
        }

        if(requestc==2){
            if(resultc ==1 ){
                Boolean Hay_Errores = false;
                String Error = "";

                BDManager Manejador = new BDManager(getApplicationContext(),"DBCenso",null,1);
                SQLiteDatabase db = Manejador.getReadableDatabase();

                if(db!=null){
                    //Validando se encuentre seleccionado un censita
                    Cursor c = db.rawQuery("SELECT id_Personal FROM Cat_Censistas WHERE estado_activo=1;", null);
                    if(!c.moveToFirst()){
                        Hay_Errores = true;
                        Error = "Necesita especificar la persona responsable del censo en configuraciones";
                    }

                    //Validando Exista el catalogo Colonias
                    c = db.rawQuery("SELECT id_colonia FROM Cat_Colonias;", null);
                    if(!c.moveToFirst()){
                        Hay_Errores = true;
                        Error = "Necesita Actualizar el Catalogo Colonias";
                    }
                    db.close();
                }
                if(Hay_Errores){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuPrincipalActivity.this,R.style.AppCompatAlertDialogStyle);
                    alertDialog.setMessage(Error);
                    alertDialog.setTitle("Ha ocurrido un problema");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }else{
                    try {
                        //ir a cargar urtas
                      /*  FragmentTransaction FT = FM.beginTransaction();
                        Fragment Cargar_Rutas = new DescargarRutasFragment();
                        FT.replace(R.id.fragment_contenedor, Cargar_Rutas);
                        FT.addToBackStack(null);
                        FT.commit();
                        btn_Back.setImageDrawable(getResources().getDrawable(R.mipmap.ic_arrow_back_white_24dp));*/
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "error " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            else
                btn_Back.setImageDrawable(null);
        }

        if(requestc==3){
            if(resultc ==1 ){
                Boolean Hay_Errores = false;
                String Error = "";

                BDManager Manejador = new BDManager(getApplicationContext(),"DBCenso",null,1);
                SQLiteDatabase db = Manejador.getReadableDatabase();

                if(db!=null){
                    //Validando se tenga rutas capturadas
                    Cursor c = db.rawQuery("SELECT id FROM OPR_USUARIOS WHERE id_estatus=102;", null);
                    if(!c.moveToFirst()){
                        Hay_Errores = true;
                        Error = "No hay ninguna ruta censada por subir";
                    }
                }
                if(Hay_Errores){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuPrincipalActivity.this,R.style.AppCompatAlertDialogStyle);
                    alertDialog.setMessage(Error);
                    alertDialog.setTitle("Ha ocurrido un problema");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }else{
                    try {
                        //ir a subir rutas
                       /* FragmentTransaction FT = FM.beginTransaction();
                        Fragment Subir_Rutas = new SubirRutasFragment();
                        FT.replace(R.id.fragment_contenedor, Subir_Rutas);
                        FT.addToBackStack(null);
                        FT.commit();
                        btn_Back.setImageDrawable(getResources().getDrawable(R.mipmap.ic_arrow_back_white_24dp));*/
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "error " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            else
                btn_Back.setImageDrawable(null);
        }

        if(requestc==111){
        /*    Fragment Cargar_Rutas = FM.findFragmentById(R.id.fragment_contenedor);
            Cargar_Rutas.onActivityResult(requestc,resultc,data);*/
        }


    }

    @Override
    public void OnItemClick(String Id) {
        if(VerificaCatalogos()){
            Intent intento = new Intent(MenuPrincipalActivity.this,DetallePadronActivity.class);
            intento.putExtra("Id_Inicial",Id);
            startActivity(intento);
        }
    }

    private boolean VerificaCatalogos(){
        boolean boolResult = false;
        boolean Hay_Errores = false;
        String Error = "Esta operacion requiere que todos los catalogos se encuentren Actualizados.";

        BDManager Manejador = new BDManager(getApplicationContext(),"DBCenso",null,1);
        SQLiteDatabase db = Manejador.getReadableDatabase();

        if(db!=null){
            Cursor c = db.rawQuery("SELECT id_anomalia FROM Cat_Anomalias", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_colonia FROM Cat_Colonias;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_diametro FROM Cat_Diametros;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_marca FROM Cat_Marcas;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_material FROM Cat_Materiales;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_materialtoma FROM Cat_MaterialToma;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_modelo FROM Cat_Modelos;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_Servicio FROM Cat_Servicios;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_tipoglobo FROM Cat_TiposGlobo;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_TipoToma FROM Cat_TiposToma;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_tipousuario FROM Cat_TiposUsuario;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_calle FROM Cat_Calles;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            c = db.rawQuery("SELECT id_giro FROM Cat_Giros;", null);
            if(!c.moveToFirst())
                Hay_Errores = true;

            db.close();
        }
        if(Hay_Errores)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuPrincipalActivity.this,R.style.AppCompatAlertDialogStyle);
            alertDialog.setMessage(Error);
            alertDialog.setTitle("Ha ocurrido un problema");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
        }else
        {
            boolResult = true;
        }
        return boolResult;
    }

    @Override
    public void img_nohayl() {
        if(boolLogueado){
            if(VerificaCatalogos()){navController.navigate(R.id.nav_descargar_rutas);}else{navController.navigate(R.id.nav_catalogos);}
        }
        else {
            boolSolicitaDescargarRutas = true;
            IrALogIn();
        }

    }

    @Override
    public void muestra_oculta_menu_lateral(boolean boolMostrar) {
        MuestraOcultaMenuLateral(boolMostrar);
    }

    @Override
    public void muestra_oculta_listado_agrupado(boolean boolMostrar) {

    }

    @Override
    public void muestra_pagina_principal() {
        navController.navigate(R.id.nav_listado_rutas);
    }

    @Override
    public void muestra_descargar_ordenes() {
        if(boolLogueado){
            if(VerificaCatalogos()){navController.navigate(R.id.nav_descargar_rutas);}else{navController.navigate(R.id.nav_catalogos);}
        }else {
            if(Rutinas.ObtenerValorBooleanoParametro(getString(R.string.parametro_permite_descargar_censo))){
                if(VerificaCatalogos()){navController.navigate(R.id.nav_descargar_rutas);}else{navController.navigate(R.id.nav_catalogos);}
            }else {
                Rutinas.SnackBarCustom(MenuPrincipalActivity.this,btnListadoOrdenes,""
                        ,getString(R.string.no_tiene_permiso_descarga_ordenes)
                        ,tipo_snackbar.NEGATIVE_SNACKBAR);
            }
        }
    }

    @Override
    public void actualiza_pocicion() {

    }

}