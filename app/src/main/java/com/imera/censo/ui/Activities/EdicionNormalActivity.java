package com.imera.censo.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Services.InterfaceTiposWizard;
import com.imera.censo.R;
import com.imera.censo.ui.Adapters.MiFragmentStatePagerAdapter;
import com.imera.censo.ui.Fragments.CapturaNormal1Fragment;
import com.imera.censo.ui.Fragments.CapturaNormal2Fragment;
import com.imera.censo.ui.Fragments.CapturaNormal3Fragment;

public class EdicionNormalActivity extends AppCompatActivity implements InterfaceTiposWizard {
    private Toolbar toolbar;
    private ViewPager pager;
    private MiFragmentStatePagerAdapter adapter;
    private TabLayout tabLayout;
    private OPR_USUARIOS Registro = new OPR_USUARIOS();
    private String Latitud= "0";
    private String Longitud = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_normal);

        //Para GPS
     /*   LocationManager milocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener milocListener = new MiLocationListener();
        milocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, milocListener);*/

        InicializaGPS();
        //Obteniendo Data
        Registro = getIntent().getParcelableExtra("data");

        //Anado la toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(""+Registro.getNombre());
        toolbar.setSubtitle(""+Registro.getDireccion());
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tabLayout = (TabLayout)findViewById(R.id.tlPaginas);

        pager = (ViewPager)findViewById(R.id.vpPantallasEdicion);

        adapter = new MiFragmentStatePagerAdapter(getSupportFragmentManager(), 3, new MiFragmentStatePagerAdapter.enCambioFragmento() {

            @Override
            public Fragment cambioFragment(int position) {
                Fragment fragmento = new Fragment();
                Bundle argumentos = new Bundle();
                argumentos.putParcelable("Data",Registro);

                switch (position){
                    case 0:
                        fragmento = new CapturaNormal1Fragment();
                        fragmento.setArguments(argumentos);
                        break;
                    case 1:
                        fragmento = new CapturaNormal2Fragment();
                        fragmento.setArguments(argumentos);
                        break;
                    case 2:
                        fragmento = new CapturaNormal3Fragment();
                        fragmento.setArguments(argumentos);
                        break;
                }
                return fragmento;
            }

            @Override
            public void autoGuardado(OPR_USUARIOS data) {
                Registro = data;
                Log.e("Guardado","Automatico");
            }
        });

        pager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("1"));
        tabLayout.addTab(tabLayout.newTab().setText("2"));
        tabLayout.addTab(tabLayout.newTab().setText("3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void Siguiente(int pantalla) {

    }

    @Override
    public void Anterior(int pantalla) {

    }

    @Override
    public void Guardar(Object Datos) {

    }

    @Override
    public void onClick(String accion) {
        if(accion.equals("Guardar")){
            Registro.setLatitud(Latitud);
            Registro.setLongitud(Longitud);

            OPR_USUARIOS.actualizarRegistro(Registro);

            setResult(1);
            finish();
        }
    }

    @Override
    public void callSnackBar(String mensaje) {
        Snackbar snackbar = Snackbar
                .make(pager,mensaje, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /**********************************************************************************************/
    public class MiLocationListener implements LocationListener {
        public void onLocationChanged(Location loc) {
            Latitud = "" + loc.getLatitude();
            Longitud = "" + loc.getLongitude();
            Log.e("Cordenadas", loc.getLatitude() + "," + loc.getLongitude());
        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Desactivado", Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Activo", Toast.LENGTH_SHORT).show();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }


    private void InicializaGPS(){
        //Para GPS
        LocationManager milocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener milocListener = new MiLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        assert milocManager != null;
        milocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, milocListener);
    }
}